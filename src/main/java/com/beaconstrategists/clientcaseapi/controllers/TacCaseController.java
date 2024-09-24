package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.mappers.Mapper;
import com.beaconstrategists.clientcaseapi.model.entities.TacCaseEntity;
import com.beaconstrategists.clientcaseapi.controllers.dto.TacCaseDto;
import com.beaconstrategists.clientcaseapi.services.TacCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<TacCaseDto> listTacCases() {
        List<TacCaseEntity> tacCases = tacCaseService.findAll();
        return tacCases.stream()
                .map(tacCaseMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/tacCases/{caseNumber}")
    public ResponseEntity<TacCaseDto> getTacCase(@PathVariable String caseNumber) {
        Optional<TacCaseEntity> foundTacCase = tacCaseService.findByCaseNumber(caseNumber);
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

    @PutMapping(path = "/tacCases/{caseNumber}")
    public ResponseEntity<TacCaseDto> fullUpdateTacCase(@PathVariable String caseNumber, @RequestBody TacCaseDto tacCaseDto) {

        if(!tacCaseService.isExists(caseNumber)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tacCaseDto.setCaseNumber(caseNumber);
        TacCaseEntity tacCaseEntity = tacCaseMapper.mapFrom(tacCaseDto);
        TacCaseEntity tacCaseEntitySaved = tacCaseService.save(tacCaseEntity);
        return new ResponseEntity<>(tacCaseMapper.mapTo(tacCaseEntitySaved), HttpStatus.OK);
    }

    @PatchMapping(path = "/tacCases/{caseNumber}")
    public ResponseEntity<TacCaseDto> partialUpdate(@PathVariable String caseNumber, @RequestBody TacCaseDto tacCaseDto) {

        if(!tacCaseService.isExists(caseNumber)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TacCaseEntity tacCaseEntity = tacCaseMapper.mapFrom(tacCaseDto);
        TacCaseEntity tacCaseEntitySaved = tacCaseService.partialUpdate(caseNumber, tacCaseEntity); //fixme
        return new ResponseEntity<>(tacCaseMapper.mapTo(tacCaseEntitySaved), HttpStatus.OK);
    }

    @DeleteMapping(path = "/tacCases/{caseNumber}")
    public ResponseEntity<TacCaseDto> deleteTacCase(@PathVariable String caseNumber) {
        if(!tacCaseService.isExists(caseNumber)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tacCaseService.delete(caseNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
