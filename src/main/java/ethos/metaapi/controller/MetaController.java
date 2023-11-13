package ethos.metaapi.controller;

import ethos.metaapi.controller.response.MetaResponse;
import ethos.metaapi.service.MetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1.0/metas")
@RequiredArgsConstructor
public class MetaController {

    private final MetaService service;

    // @PostMapping

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MetaResponse> getMetas(){
        return service.getMetas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MetaResponse getMetaById(@PathVariable UUID id){
        return service.getMetaById(id);
    }

    @GetMapping("/busca-por-descricao")
    @ResponseStatus(HttpStatus.OK)
    public List<MetaResponse> getMetaByDescricao(@RequestParam String descricao){
        return service.getMetaByDescricao(descricao);
    }

    @GetMapping("/busca-data-inicio")
    @ResponseStatus(HttpStatus.OK)
    public List<MetaResponse> getMetaByDataInicio(@RequestParam LocalDate dataInicio){
        return service.getMetaByDataInicio(dataInicio);
    }

    @GetMapping("/busca-data-fim")
    @ResponseStatus(HttpStatus.OK)
    public List<MetaResponse> getMetaByDataFim(@RequestParam LocalDate dataFim){
        return service.getMetaByDataFim(dataFim);
    }
}
