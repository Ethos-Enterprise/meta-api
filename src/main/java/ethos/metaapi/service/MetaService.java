package ethos.metaapi.service;

import ethos.metaapi.controller.response.MetaResponseDto;
import ethos.metaapi.exception.MetaException;
import ethos.metaapi.exception.MetaNotFoundException;
import ethos.metaapi.repository.MetaRepository;
import ethos.metaapi.repository.entity.MetaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MetaService {
    private final MetaRepository repository;

    public MetaResponseDto saveMeta(MetaEntity metaEntity) {
        MetaEntity metaCadastrada = repository.save(metaEntity);
        MetaResponseDto metaResponseDto = new MetaResponseDto(metaCadastrada);
        return metaResponseDto;
    }

    public List<MetaResponseDto> getMetas() {
        final List<MetaEntity> listaMetas = repository.findAll();
        return listaMetas.stream().map(MetaResponseDto::new).toList();
    }

    public MetaResponseDto getMetaById(UUID id){
        Optional<MetaEntity> meta = repository.findById(id);

        if (meta.isEmpty()){
            throw new MetaNotFoundException(String.format("Meta não encontrada."));
        }
        return meta.map(MetaResponseDto::new).get();
    }

    public List<MetaResponseDto> getMetaByDescricao(String descricao){
        List<MetaEntity> meta = repository.findByDescricaoContainsIgnoreCase(descricao);

        if(meta.isEmpty()){
            throw new MetaException(String.format("A meta com a descrição %s não existe.", descricao));
        }
        return meta.stream().map(MetaResponseDto::new).toList();
    }

    public List<MetaResponseDto> getMetaByDataInicio(LocalDate dataInicio){
        List<MetaEntity> meta = repository.findByDataInicio(dataInicio);

        if(meta.isEmpty()){
            throw new MetaException(String.format("A meta com a data inicio %s não existe.", dataInicio));
        }
        return meta.stream().map(MetaResponseDto::new).toList();
    }

    public List<MetaResponseDto> getMetaByDataFim(LocalDate dataFim){
        List<MetaEntity> meta = repository.findByDataFim(dataFim);

        if(meta.isEmpty()){
            throw new MetaException(String.format("A meta com a data fim %s não existe.", dataFim));
        }
        return meta.stream().map(MetaResponseDto::new).toList();
    }

    public MetaResponseDto updateMeta(MetaEntity updatedMeta, UUID id) {
        Optional<MetaEntity> metaOpt = repository.findById(updatedMeta.getId());

        if (metaOpt.isPresent()) {
            MetaEntity existingMeta = metaOpt.get();

            existingMeta.setDescricao(updatedMeta.getDescricao());
            existingMeta.setDataInicio(updatedMeta.getDataInicio());
            existingMeta.setDataFim(updatedMeta.getDataFim());

            MetaEntity savedMeta = repository.save(existingMeta);

            return new MetaResponseDto(savedMeta);
        } else {
            throw new MetaNotFoundException("Meta não encontrada.");
        }
    }

    public void deleteMeta(UUID id) {
        Optional<MetaEntity> existingMetaOptional = repository.findById(id);

        if (existingMetaOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new MetaNotFoundException(String.format("Meta com id %s não encontrada", id));
        }
    }
}

