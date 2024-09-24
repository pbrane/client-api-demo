package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseDto;
import com.beaconstrategists.clientcaseapi.mappers.Mapper;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import com.beaconstrategists.clientcaseapi.services.RmaCaseService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RmaCaseController {

    private final RmaCaseService rmaCaseService;

    private final Mapper<RmaCaseEntity, RmaCaseDto> rmaCaseMapper;

    public RmaCaseController(RmaCaseService rmaCaseService, Mapper<RmaCaseEntity, RmaCaseDto> rmaCaseMapper) {
        this.rmaCaseMapper = rmaCaseMapper;
        this.rmaCaseService = rmaCaseService;
    }

    @PostMapping(path = "/rmaCases")
    public ResponseEntity<RmaCaseDto> createRmaCase(@RequestBody RmaCaseDto rmaCaseDto) {
        RmaCaseEntity rmaCaseEntity = rmaCaseMapper.mapFrom(rmaCaseDto);
        RmaCaseEntity rmaCaseEntitySaved = rmaCaseService.save(rmaCaseEntity);
        return new ResponseEntity<>(rmaCaseMapper.mapTo(rmaCaseEntitySaved), HttpStatus.CREATED);
    }

    @GetMapping(path = "/rmaCases")
    public List<RmaCaseDto> listRmaCases() {
        List<RmaCaseEntity> rmaCases = rmaCaseService.findAll();
        return rmaCases.stream()
                .map(rmaCaseMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/rmaCases/{caseNumber}")
    public ResponseEntity<RmaCaseDto> getRmaCase(@PathVariable String caseNumber) {
        Optional<RmaCaseEntity> foundRmaCase = rmaCaseService.findByCaseNumber(caseNumber);
        return foundRmaCase.map(rmaCaseEntity -> {
            RmaCaseDto rmaCaseDto = rmaCaseMapper.mapTo(rmaCaseEntity);
            return new ResponseEntity<>(rmaCaseDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/rmaCases/{caseNumber}")
    public ResponseEntity<RmaCaseDto> fullUpdateRmaCase(@PathVariable String caseNumber, @RequestBody RmaCaseDto rmaCaseDto) {

        if(!rmaCaseService.isExists(caseNumber)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        rmaCaseDto.setCaseNumber(caseNumber);
        RmaCaseEntity rmaCaseEntity = rmaCaseMapper.mapFrom(rmaCaseDto);
        RmaCaseEntity rmaCaseEntitySaved = rmaCaseService.save(rmaCaseEntity);
        return new ResponseEntity<>(rmaCaseMapper.mapTo(rmaCaseEntitySaved), HttpStatus.OK);
    }

    @PatchMapping(path = "/rmaCases/{caseNumber}")
    public ResponseEntity<RmaCaseDto> partialUpdate(@PathVariable String caseNumber, @RequestBody RmaCaseDto rmaCaseDto) {

        if(!rmaCaseService.isExists(caseNumber)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RmaCaseEntity rmaCaseEntity = rmaCaseMapper.mapFrom(rmaCaseDto);
        RmaCaseEntity rmaCaseEntitySaved = rmaCaseService.partialUpdate(caseNumber, rmaCaseEntity); //fixme
        return new ResponseEntity<>(rmaCaseMapper.mapTo(rmaCaseEntitySaved), HttpStatus.OK);
    }

    @DeleteMapping(path = "/rmaCases/{caseNumber}")
    public ResponseEntity<RmaCaseDto> deleteRmaCase(@PathVariable String caseNumber) {

        if(!rmaCaseService.isExists(caseNumber)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        rmaCaseService.delete(caseNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
