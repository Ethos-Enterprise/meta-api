package ethos.metaapi.controller.response;

import ethos.metaapi.repository.entity.MetaEntity;

import java.time.LocalDate;
import java.util.UUID;

public record MetaResponse(UUID id, String pilarEsg, String descricao, LocalDate dataInicio, LocalDate dataFim) {

    public MetaResponse(MetaEntity metaEntity){
        this(
                metaEntity.getId(),
                metaEntity.getPilarEsg(),
                metaEntity.getDescricao(),
                metaEntity.getDataInicio(),
                metaEntity.getDataFim()
        );
    }
}
