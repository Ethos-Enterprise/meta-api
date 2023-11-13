package ethos.metaapi.service;

import ethos.metaapi.controller.response.MetaResponse;
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

    // @PostMapping

    public List<MetaResponse> getMetas() {
        final List<MetaEntity> listaMetas = repository.findAll();
        return listaMetas.stream().map(MetaResponse::new).toList();
    }

    public MetaResponse getMetaById(UUID id){
        Optional<MetaEntity> meta = repository.findById(id);

        if (meta.isEmpty()){
            throw new MetaNotFoundException(String.format("Meta não encontrada."));
        }
        return meta.map(MetaResponse::new).get();
    }

    public List<MetaResponse> getMetaByDescricao(String descricao){
        List<MetaEntity> meta = repository.findByDescricaoContainsIgnoreCase(descricao);

        if(meta.isEmpty()){
            throw new MetaException(String.format("A meta com a descrição %s não existe.", descricao));
        }
        return meta.stream().map(MetaResponse::new).toList();
    }

    public List<MetaResponse> getMetaByDataInicio(LocalDate dataInicio){
        List<MetaEntity> meta = repository.findByDataInicio(dataInicio);

        if(meta.isEmpty()){
            throw new MetaException(String.format("A meta com a data inicio %s não existe.", dataInicio));
        }
        return meta.stream().map(MetaResponse::new).toList();
    }

    public List<MetaResponse> getMetaByDataFim(LocalDate dataFim){
        List<MetaEntity> meta = repository.findByDataFim(dataFim);

        if(meta.isEmpty()){
            throw new MetaException(String.format("A meta com a data fim %s não existe.", dataFim));
        }
        return meta.stream().map(MetaResponse::new).toList();
    }
}
