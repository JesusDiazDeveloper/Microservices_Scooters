package com.arqui.integrador.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.dto.BillDto;
import com.arqui.integrador.dto.PausedTimeResponseDto;
import com.arqui.integrador.dto.PriceDto;
import com.arqui.integrador.dto.TravelDto;
import com.arqui.integrador.dto.TravelsScooterResponseDto;
import com.arqui.integrador.service.TravelService;

@RestController
@RequestMapping("/travels")
public class TravelController {

	private TravelService service;
	private static final Logger LOG = LoggerFactory.getLogger(TravelController.class);

	public TravelController(TravelService service) {
		this.service = service;
	}

	@GetMapping()
	public ResponseEntity<List<TravelDto>> getTravels() {
		List<TravelDto> travel = service.getAll();
		if (travel != null) {
			return ResponseEntity.ok(travel);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TravelDto> getTravelById(@PathVariable String id) {
		TravelDto travel = service.getById(id);
		if (travel != null) {
			return ResponseEntity.ok(travel);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTravel(@RequestBody TravelDto t) {
		service.createTravel(t);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTravel(@RequestBody TravelDto t, @PathVariable String id) {
		service.update(t, id);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteTravel(@PathVariable String id) {
		service.delete(id);
	}

	@GetMapping(value = "/paused")
	public ResponseEntity<List<PausedTimeResponseDto>> getTravelsByPausedTime() {
		List<PausedTimeResponseDto> travel = service.getAllByPause();
		if (travel != null) {
			return ResponseEntity.ok(travel);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/billing")
	public ResponseEntity<BillDto> getBillingByDate(@RequestParam int year, @RequestParam int month1,
			@RequestParam int month2) {
		BillDto travel = service.getBills(year, month1, month2);
		if (travel != null) {
			return ResponseEntity.ok(travel);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/filter")
	public ResponseEntity<List<TravelsScooterResponseDto>> getQuantityTravelsByYear(@RequestParam int year,
			@RequestParam Long quantity) {
		List<TravelsScooterResponseDto> travel = service.getAllByYearQuantity(year, quantity);
		if (travel != null) {
			return ResponseEntity.ok(travel);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "/price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void createNewPrice(@RequestBody PriceDto p) {

		service.newPrice(p);

	}
}