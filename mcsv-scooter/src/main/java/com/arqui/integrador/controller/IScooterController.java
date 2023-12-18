package com.arqui.integrador.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.arqui.integrador.dto.ScooterDTO;
import com.arqui.integrador.dto.ScooterListDTO;
import com.arqui.integrador.dto.ScooterNearestDTO;
import com.arqui.integrador.dto.ScooterOperationDTO;
import com.arqui.integrador.dto.ScooterReportDTO;

import jakarta.validation.Valid;

@Validated
@RequestMapping("/scooters")
public interface IScooterController {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<List<ScooterDTO>> getAll(
			@RequestParam(value = "orderBy", defaultValue = "id") String order,
			@RequestParam(value = "available", required = false) Boolean available);
	
	@GetMapping(value = "/report" ,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<List<ScooterReportDTO>> getScooterReport(@RequestParam(value = "pause-time", defaultValue = "false") Boolean pause_time);
	
	@GetMapping(value = "/in-operation", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<List<ScooterOperationDTO>> getScooterInOperation();
	
	@GetMapping(value = "/nearest/lat/{lat}/long/{long}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<List<ScooterNearestDTO>> getNearestScooters(@PathVariable("lat") double latitude, @PathVariable("long") double longitude);
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<ScooterDTO> getById(@PathVariable("id") Long id);
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	ResponseEntity<ScooterDTO> add(@RequestBody @Valid ScooterDTO scooter);
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<ScooterDTO> update(@PathVariable("id") Long id,@RequestBody @Valid ScooterDTO scooter);
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	void updateScootersMaintenance(@RequestBody @Valid ScooterListDTO ids);
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable("id") Long id);
}
