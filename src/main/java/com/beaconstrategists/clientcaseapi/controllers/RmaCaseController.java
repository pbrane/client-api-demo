package com.beaconstrategists.clientcaseapi.controllers;

import com.beaconstrategists.clientcaseapi.controllers.dto.RmaCaseDto;
import com.beaconstrategists.clientcaseapi.mappers.Mapper;
import com.beaconstrategists.clientcaseapi.model.entities.RmaCaseEntity;
import com.beaconstrategists.clientcaseapi.services.RmaCaseService;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<List<RmaCaseDto>> listRmaCases(@RequestParam(value = "caseNumber", required = false) String caseNumber) {
        if (caseNumber != null) {
            Optional<RmaCaseEntity> foundRmaCase = rmaCaseService.findByCaseNumber(caseNumber);
            if (foundRmaCase.isPresent()) {
                RmaCaseDto rmaCaseDto = rmaCaseMapper.mapTo(foundRmaCase.get());
                return ResponseEntity.ok(Collections.singletonList(rmaCaseDto));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
        } else {
            List<RmaCaseEntity> rmaCases = rmaCaseService.findAll();
            List<RmaCaseDto> rmaCaseDtos = rmaCases.stream()
                    .map(rmaCaseMapper::mapTo)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(rmaCaseDtos);
        }
    }

    @GetMapping(path = "/rmaCases/{id}")
    public ResponseEntity<RmaCaseDto> getRmaCase(@PathVariable Long id) {
        Optional<RmaCaseEntity> foundRmaCase = rmaCaseService.findById(id);
        return foundRmaCase.map(rmaCaseEntity -> {
            RmaCaseDto rmaCaseDto = rmaCaseMapper.mapTo(rmaCaseEntity);
            return new ResponseEntity<>(rmaCaseDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/rmaCases/{id}")
    public ResponseEntity<RmaCaseDto> fullUpdateRmaCase(@PathVariable Long id, @RequestBody RmaCaseDto rmaCaseDto) {

        if(!rmaCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //rmaCaseDto.setId(id);
        RmaCaseEntity rmaCaseEntity = rmaCaseMapper.mapFrom(rmaCaseDto);
        RmaCaseEntity rmaCaseEntitySaved = rmaCaseService.save(rmaCaseEntity);
        return new ResponseEntity<>(rmaCaseMapper.mapTo(rmaCaseEntitySaved), HttpStatus.OK);
    }

    @PatchMapping(path = "/rmaCases/{id}")
    public ResponseEntity<RmaCaseDto> partialUpdate(@PathVariable Long id, @RequestBody RmaCaseDto rmaCaseDto) {

        if(!rmaCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RmaCaseEntity rmaCaseEntity = rmaCaseMapper.mapFrom(rmaCaseDto);
        RmaCaseEntity rmaCaseEntitySaved = rmaCaseService.partialUpdate(id, rmaCaseEntity); //fixme
        return new ResponseEntity<>(rmaCaseMapper.mapTo(rmaCaseEntitySaved), HttpStatus.OK);
    }

    @DeleteMapping(path = "/rmaCases/{id}")
    public ResponseEntity<Void> deleteRmaCase(@PathVariable Long id) {
        if (!rmaCaseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rmaCaseService.delete(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Message", "Case ID: " + id + " deleted.");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
