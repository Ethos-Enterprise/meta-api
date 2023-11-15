package ethos.metaapi.controller;

import ethos.metaapi.controller.request.MetaCreateRequestDto;
import ethos.metaapi.controller.response.MetaResponseDto;
import ethos.metaapi.mapper.MetaMapper;
import ethos.metaapi.repository.entity.MetaEntity;
import ethos.metaapi.service.MetaService;
import jakarta.validation.Valid;
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

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MetaResponseDto postMeta(@RequestBody @Valid MetaCreateRequestDto metaCreateRequestDto){
        MetaEntity meta = MetaMapper.of(metaCreateRequestDto);
        return service.saveMeta(meta);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MetaResponseDto> getMetas(){
        return service.getMetas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MetaResponseDto getMetaById(@PathVariable UUID id){
        return service.getMetaById(id);
    }

    @GetMapping("/busca-por-descricao")
    @ResponseStatus(HttpStatus.OK)
    public List<MetaResponseDto> getMetaByDescricao(@RequestParam String descricao){
        return service.getMetaByDescricao(descricao);
    }

    @GetMapping("/busca-data-inicio")
    @ResponseStatus(HttpStatus.OK)
    public List<MetaResponseDto> getMetaByDataInicio(@RequestParam LocalDate dataInicio){
        return service.getMetaByDataInicio(dataInicio);
    }

    @GetMapping("/busca-data-fim")
    @ResponseStatus(HttpStatus.OK)
    public List<MetaResponseDto> getMetaByDataFim(@RequestParam LocalDate dataFim){
        return service.getMetaByDataFim(dataFim);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MetaResponseDto putMeta(@PathVariable UUID id, @RequestBody @Valid MetaCreateRequestDto metaCreateRequestDto){
        MetaEntity updatedMeta = MetaMapper.of(metaCreateRequestDto);
        return service.updateMeta(updatedMeta, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeta(@PathVariable UUID id){
        service.deleteMeta(id);
    }
}
