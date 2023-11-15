package ethos.metaapi.controller.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MetaUpdateRequestDto (
        @NotNull
        String pilarEsg,

        @NotBlank
        @Size(min = 5)
        String descricao,

        @PastOrPresent
        LocalDate dataInicio,

        @Future
        LocalDate dataFim){
}
