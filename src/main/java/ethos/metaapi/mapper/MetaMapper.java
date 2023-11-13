package ethos.metaapi.mapper;

import ethos.metaapi.controller.request.MetaRequest;
import ethos.metaapi.repository.entity.MetaEntity;

public class MetaMapper {
    public static MetaEntity of(MetaRequest metaRequest){
        MetaEntity metaEntity = new MetaEntity();

        metaEntity.setDescricao(metaRequest.descricao());
        metaEntity.setDataInicio(metaRequest.dataInicio());
        metaEntity.setDataFim(metaRequest.dataFim());

        return metaEntity;
    }
}
