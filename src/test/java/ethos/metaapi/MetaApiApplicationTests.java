package ethos.metaapi;

import ethos.metaapi.controller.MetaController;
import ethos.metaapi.controller.request.MetaCreateRequestDto;
import ethos.metaapi.controller.request.MetaUpdateRequestDto;
import ethos.metaapi.controller.response.MetaResponseDto;
import ethos.metaapi.exception.MetaNotFoundException;
import ethos.metaapi.repository.MetaRepository;
import ethos.metaapi.repository.entity.MetaEntity;
import ethos.metaapi.service.MetaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetaApiApplicationTests {
    @InjectMocks
    private MetaService service;

    @Mock
    private MetaRepository repository;

    @Mock
    private MetaController controller;

    public static MetaEntity createMetaEntity() {
        return new MetaEntity(
                UUID.fromString("62b3105b-4d0e-4b98-bbca-19991ebcb0b8"),
                "ESG",
                "descricaoEmpresa",
                LocalDate.parse("2024-01-10"),
                LocalDate.parse("2024-10-30")
        );
    }

    public static MetaCreateRequestDto createMetaRequest() {
        return new MetaCreateRequestDto(
                "ESG",
                "descricaoEmpresa",
                LocalDate.parse("2024-01-10"),
                LocalDate.parse("2024-10-30")
        );
    }

    public static MetaUpdateRequestDto updateMetaRequest() {
        return new MetaUpdateRequestDto(
                "E",
                "descricaoAtualizadaEmpresa",
                LocalDate.parse("2024-01-10"),
                LocalDate.parse("2026-03-20")
        );
    }

    @Test
    @DisplayName("Deve salvar uma nova meta")
    void should_save_new_meta() {
        MetaEntity entity = createMetaEntity();

        when(repository.save(any(MetaEntity.class))).thenReturn(entity);

        MetaResponseDto result = service.saveMeta(entity);

        verify(repository, times(1)).save(any(MetaEntity.class));

        assertNotNull(result);

        assertEquals(entity.getId(), result.id());
    }

    @Test
    @DisplayName("Deve atualizar uma meta existente")
    public void should_update_existing_meta() {
        MetaEntity updatedMeta = createMetaEntity();

        updatedMeta.setDescricao("Nova descrição");

        when(repository.findById(updatedMeta.getId())).thenReturn(Optional.of(updatedMeta));

        when(repository.save(any(MetaEntity.class))).thenReturn(updatedMeta);

        MetaResponseDto result = service.updateMeta(updatedMeta, updatedMeta.getId());

        verify(repository, times(1)).findById(updatedMeta.getId());

        verify(repository, times(1)).save(updatedMeta);

        assertNotNull(result);

        assertEquals("Nova descrição", result.descricao());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a meta não existir")
    public void testUpdateMeta_WhenMetaDoesNotExist() {
        MetaEntity updatedMeta = new MetaEntity();

        updatedMeta.setId(UUID.randomUUID());

        when(repository.findById(updatedMeta.getId())).thenReturn(Optional.empty());

        MetaNotFoundException ex = assertThrows(MetaNotFoundException.class, () -> service.updateMeta(updatedMeta, updatedMeta.getId()));

        assertEquals("Meta não encontrada.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve deletar meta existente")
    public void testDeleteMeta_WhenMetaExists() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.of(new MetaEntity()));

        service.deleteMeta(id);

        verify(repository, times(1)).findById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a meta não existe")
    public void testDeleteMeta_WhenMetaDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        MetaNotFoundException ex = assertThrows(MetaNotFoundException.class, () -> service.deleteMeta(id));

        verify(repository, never()).deleteById(id);

        assertEquals(String.format("Meta com id %s não encontrada", id), ex.getMessage());
    }

    @Test
    @DisplayName("Deve retornar todas as metas criadas")
    void should_return_all_metas() {
       MetaEntity entity = createMetaEntity();

       when(repository.findAll()).thenReturn(java.util.List.of(entity));

       var metas = service.getMetas();

       assertEquals(metas.size(), 1);
    }

    @Test
    @DisplayName("Deve listar todas as metas conforme o ID")
    void should_return_all_metas_by_id() {
        UUID metaId = UUID.fromString("62b3105b-4d0e-4b98-bbca-19991ebcb0b8");
        MetaEntity entity = createMetaEntity();

        when(repository.findById(metaId)).thenReturn(java.util.Optional.of(entity));

        MetaResponseDto meta = service.getMetaById(metaId);

        assertNotNull(meta);

        assertEquals(createMetaEntity().getId(), entity.getId());
    }
}