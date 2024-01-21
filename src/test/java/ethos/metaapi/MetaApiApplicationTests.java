package ethos.metaapi;

import ethos.metaapi.controller.MetaController;
import ethos.metaapi.controller.request.MetaCreateRequestDto;
import ethos.metaapi.controller.request.MetaUpdateRequestDto;
import ethos.metaapi.controller.response.MetaResponseDto;
import ethos.metaapi.mapper.MetaMapper;
import ethos.metaapi.repository.MetaRepository;
import ethos.metaapi.repository.entity.MetaEntity;
import ethos.metaapi.service.MetaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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