package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.mappers.Mapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseEntity;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;
import com.beaconstrategists.clientcaseapi.services.TacCaseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class TacCaseController {

    private final TacCaseService tacCaseService;

    private final Mapper<TacCaseEntity, TacCaseDto> tacCaseMapper;

    public TacCaseController(TacCaseService tacCaseService, Mapper<TacCaseEntity, TacCaseDto> tacCaseMapper) {
        this.tacCaseMapper = tacCaseMapper;
        this.tacCaseService = tacCaseService;
    }

    @GetMapping(path = "/tacCases")
    public ResponseEntity<List<TacCaseDto>> listTacCases(@RequestParam(value = "caseNumber", required = false) String caseNumber) {
        if (caseNumber != null) {
            Optional<TacCaseEntity> foundTacCase = tacCaseService.findByCaseNumber(caseNumber);
            if (foundTacCase.isPresent()) {
                TacCaseDto tacCaseDto = tacCaseMapper.mapTo(foundTacCase.get());
                return ResponseEntity.ok(Collections.singletonList(tacCaseDto));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
        } else {
            List<TacCaseEntity> tacCases = tacCaseService.findAll();
            List<TacCaseDto> tacCaseDtos = tacCases.stream()
                    .map(tacCaseMapper::mapTo)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(tacCaseDtos);
        }
    }

    @GetMapping(path = "/tacCases/{id}")
    public ResponseEntity<TacCaseDto> getTacCase(@PathVariable Long id) {
        Optional<TacCaseEntity> foundTacCase = tacCaseService.findById(id);
        return foundTacCase.map(tacCaseEntity -> {
            TacCaseDto tacCaseDto = tacCaseMapper.mapTo(tacCaseEntity);
            return new ResponseEntity<>(tacCaseDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/tacCases")
    public ResponseEntity<TacCaseDto> createTacCase(@RequestBody TacCaseDto tacCaseDto) {
        TacCaseEntity tacCaseEntity = tacCaseMapper.mapFrom(tacCaseDto);
        TacCaseEntity tacCaseEntitySaved = tacCaseService.save(tacCaseEntity);
        return new ResponseEntity<>(tacCaseMapper.mapTo(tacCaseEntitySaved), HttpStatus.CREATED);
    }

    @PutMapping(path = "/tacCases/{id}")
    public ResponseEntity<TacCaseDto> fullUpdateTacCase(@PathVariable Long id, @RequestBody TacCaseDto tacCaseDto) {

        if(!tacCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //tacCaseDto.setId(id);
        TacCaseEntity tacCaseEntity = tacCaseMapper.mapFrom(tacCaseDto);
        TacCaseEntity tacCaseEntitySaved = tacCaseService.save(tacCaseEntity);
        return new ResponseEntity<>(tacCaseMapper.mapTo(tacCaseEntitySaved), HttpStatus.OK);
    }

    @PatchMapping(path = "/tacCases/{id}")
    public ResponseEntity<TacCaseDto> partialUpdate(@PathVariable Long id, @RequestBody TacCaseDto tacCaseDto) {

        if(!tacCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TacCaseEntity tacCaseEntity = tacCaseMapper.mapFrom(tacCaseDto);
        TacCaseEntity tacCaseEntitySaved = tacCaseService.partialUpdate(id, tacCaseEntity); //fixme
        return new ResponseEntity<>(tacCaseMapper.mapTo(tacCaseEntitySaved), HttpStatus.OK);
    }

    @DeleteMapping(path = "/tacCases/{id}")
    public ResponseEntity<Void> deleteTacCase(@PathVariable Long id) {
        if (!tacCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tacCaseService.delete(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Message", "Case ID: " + id + " deleted.");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

}
