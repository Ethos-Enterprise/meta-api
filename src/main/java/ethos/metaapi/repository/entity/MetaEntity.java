package ethos.metaapi.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "META")
@Entity
public class MetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String pilarEsg;

    private String descricao;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private UUID fkEmpresa;
}
