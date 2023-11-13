package ethos.metaapi.repository;

import ethos.metaapi.repository.entity.MetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MetaRepository extends JpaRepository<MetaEntity, UUID> {

    List<MetaEntity> findByDescricaoContainsIgnoreCase(String descricao);

    List<MetaEntity> findByDataInicio(LocalDate dataInicio);

    List<MetaEntity> findByDataFim(LocalDate dataFim);
}
