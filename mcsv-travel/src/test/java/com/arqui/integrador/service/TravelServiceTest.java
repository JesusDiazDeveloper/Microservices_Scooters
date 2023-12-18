package com.arqui.integrador.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.arqui.integrador.dto.TravelDto;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.model.Travel;
import com.arqui.integrador.repository.TravelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class TravelServiceTest {
	
	@InjectMocks
	private TravelService travelService;
	
	@Mock
	private TravelRepository travelRepository;
	
	@Mock
    private RestTemplate restTemplate;
	
	@Mock
	private ObjectMapper objectMapper;
	
	@Test
	void getAllTest(){
		List<Travel> travels = new ArrayList<>();
		Travel travelOne = Travel.builder()
							.id("a")
							.id_account(1)
							.id_user(123)
							.id_scooter(1000)
							.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
							.ending_date(null)
							.pause_time(0)
							.km(BigDecimal.valueOf(45))
							.cost(Double.valueOf(5000))
							.paused(false)
							.build();
		
		
		Travel travelTwo = Travel.builder()
							.id("b")
							.id_account(2)
							.id_user(1234)
							.id_scooter(1001)
							.start_date(LocalDateTime.of(2023, 06, 16, 0, 0))
							.ending_date(null)
							.pause_time(0)
							.km(BigDecimal.valueOf(12))
							.cost(Double.valueOf(1500))
							.paused(false)
							.build();
		
		Travel travelThree = Travel.builder()
							.id("c")
							.id_account(3)
							.id_user(12345)
							.id_scooter(1002)
							.start_date(LocalDateTime.of(2023, 06, 17, 0, 0))
							.ending_date(null)
							.pause_time(20)
							.km(BigDecimal.valueOf(55))
							.cost(Double.valueOf(6000))
							.paused(false)
							.build();
		
		travels.add(travelOne);
		travels.add(travelTwo);
		travels.add(travelThree);
		
		TravelDto travelDtoOne = TravelDto.builder()
				.id("a")
				.id_account(1)
				.id_usuario(123)
				.id_scooter(1000)
				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(45))
				.cost(Double.valueOf(5000))
				.paused(false)
				.build();
		
		TravelDto travelDtoTwo = TravelDto.builder()
				.id("b")
				.id_account(2)
				.id_usuario(1234)
				.id_scooter(1001)
				.start_date(LocalDateTime.of(2023, 06, 16, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(12))
				.cost(Double.valueOf(1500))
				.paused(false)
				.build();
		
		TravelDto travelDtoThree = TravelDto.builder()
				.id("c")
				.id_account(3)
				.id_usuario(12345)
				.id_scooter(1002)
				.start_date(LocalDateTime.of(2023, 06, 17, 0, 0))
				.ending_date(null)
				.pause_time(20)
				.km(BigDecimal.valueOf(55))
				.cost(Double.valueOf(6000))
				.paused(false)
				.build();
		
		Mockito.when(this.travelRepository.findAll()).thenReturn(travels);
		Mockito.when(this.objectMapper.convertValue(travelOne, TravelDto.class)).thenReturn(travelDtoOne);
		Mockito.when(this.objectMapper.convertValue(travelTwo, TravelDto.class)).thenReturn(travelDtoTwo);
		Mockito.when(this.objectMapper.convertValue(travelThree, TravelDto.class)).thenReturn(travelDtoThree);
		
		List<TravelDto> travelsDto = this.travelService.getAll();
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findAll();
		Assertions.assertEquals(3L, travelsDto.size());
		Assertions.assertEquals(1, travelsDto.get(0).getId_account());
		Assertions.assertEquals(2, travels.get(1).getId_account());
		Assertions.assertEquals(3, travels.get(2).getId_account());
		Assertions.assertEquals(123, travels.get(0).getId_user());
		Assertions.assertEquals(1234, travels.get(1).getId_user());
		Assertions.assertEquals(12345, travels.get(2).getId_user());
		Assertions.assertEquals(1000, travels.get(0).getId_scooter());
		Assertions.assertEquals(1001, travels.get(1).getId_scooter());
		Assertions.assertEquals(1002, travels.get(2).getId_scooter());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 15, 0, 0), travels.get(0).getStart_date());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 16, 0, 0), travels.get(1).getStart_date());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 17, 0, 0), travels.get(2).getStart_date());
		Assertions.assertEquals(null, travels.get(0).getEnding_date());
		Assertions.assertEquals(null, travels.get(1).getEnding_date());
		Assertions.assertEquals(null, travels.get(2).getEnding_date());
		Assertions.assertEquals(0, travels.get(0).getPause_time());
		Assertions.assertEquals(0, travels.get(1).getPause_time());
		Assertions.assertEquals(20, travels.get(2).getPause_time());
		Assertions.assertEquals(BigDecimal.valueOf(45), travels.get(0).getKm());
		Assertions.assertEquals(BigDecimal.valueOf(12), travels.get(1).getKm());
		Assertions.assertEquals(BigDecimal.valueOf(55), travels.get(2).getKm());
		Assertions.assertEquals(BigDecimal.valueOf(5000), travels.get(0).getCost());
		Assertions.assertEquals(BigDecimal.valueOf(1500), travels.get(1).getCost());
		Assertions.assertEquals(BigDecimal.valueOf(6000), travels.get(2).getCost());
		Assertions.assertEquals(false, travels.get(0).isPaused());
		Assertions.assertEquals(false, travels.get(1).isPaused());
		Assertions.assertEquals(false, travels.get(2).isPaused());
	}
	
	@Test
	void getByIdTest() {
		Travel travelOne = Travel.builder()
				.id("a")
				.id_account(1)
				.id_user(123)
				.id_scooter(1000)
				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
				.ending_date(null).pause_time(0)
				.km(BigDecimal.valueOf(45))
				.cost(Double.valueOf(5000))
				.paused(false)
				.build();
		
		TravelDto travelDtoOne = TravelDto.builder()
				.id("a")
				.id_account(1)
				.id_usuario(123)
				.id_scooter(1000)
				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(45))
				.cost(Double.valueOf(5000))
				.paused(false)
				.build();
		
		Mockito.when(this.travelRepository.findById("a")).thenReturn(Optional.of(travelOne));
		Mockito.when(this.objectMapper.convertValue(travelOne, TravelDto.class)).thenReturn(travelDtoOne);
		
		TravelDto travelDto = this.travelService.getById("a");
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findById("a");
		
		Assertions.assertEquals(1, travelDto.getId_account());
		Assertions.assertEquals(123, travelDto.getId_usuario());
		Assertions.assertEquals(1000, travelDto.getId_scooter());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 15, 0, 0), travelDto.getStart_date());
		Assertions.assertEquals(0, travelDto.getPause_time());
		Assertions.assertEquals(BigDecimal.valueOf(45), travelDto.getKm());
		Assertions.assertEquals(BigDecimal.valueOf(5000), travelDto.getCost());
		Assertions.assertEquals(false, travelDto.isPaused());
	}
	
	@Test
	void getByIdNotFoundTest() {		
		Mockito.when(this.travelRepository.findById("a")).thenReturn(Optional.empty());
		
		ItemNotFoundException expectedException = Assertions.assertThrows(
				ItemNotFoundException.class, () -> this.travelService.getById("a"));
		
		Assertions.assertEquals("Item not found.", expectedException.getMessage());
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findById("a");
	}
	
	@Test
	void getByNullIdTest() {		
		Mockito.when(this.travelRepository.findById(null)).thenThrow(IllegalArgumentException.class);
		
		IllegalArgumentException expectedException = Assertions.assertThrows(
				IllegalArgumentException.class, () -> this.travelService.getById(null));
		
		Assertions.assertEquals("java.lang.IllegalArgumentException", expectedException.getClass().getName());
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findById(null);
	}

	@Test
	void createTest() {
		Travel travel= Travel.builder()
				.id("a")
				.id_account(1)
				.id_user(123)
				.id_scooter(1000)
				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(45))
				.cost(Double.valueOf(5000))
				.paused(false)
				.build();
				
		
		TravelDto travelDtoRequest = TravelDto.builder()
				.id("a")
				.id_account(1)
				.id_usuario(123)
				.id_scooter(1000)
				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(45))
				.cost(Double.valueOf(5000))
				.paused(false)
				.build();
		
		Mockito.when(this.objectMapper.convertValue(travelDtoRequest, Travel.class)).thenReturn(travel);
		
		this.travelService.createTravel(travelDtoRequest);
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).save(travel);

	}
	
	@Test
	void updateTest() {
		Travel travel = Travel.builder()
				.id("b")
				.id_account(1)
				.id_user(123)
				.id_scooter(1000)
				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(45))
				.cost(Double.valueOf(5000))
				.paused(false)
				.build();
		
		Travel travelEdited = Travel.builder()
				.id("b")
				.id_account(2)
				.id_user(1234)
				.id_scooter(1001)
				.start_date(LocalDateTime.of(2023, 06, 16, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(12))
				.cost(Double.valueOf(1500))
				.paused(false)
				.build();
		
		
		TravelDto travelEditedDto = TravelDto.builder()
				.id("b")
				.id_account(2)
				.id_usuario(1234)
				.id_scooter(1001)
				.start_date(LocalDateTime.of(2023, 06, 16, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(12))
				.cost(Double.valueOf(1500))
				.paused(false)
				.build();
		
		Mockito.when(this.travelRepository.findById("b")).thenReturn(Optional.of(travel));
		Mockito.when(this.objectMapper.convertValue(travelEditedDto, Travel.class)).thenReturn(travelEdited);
		
		this.travelService.update(travelEditedDto, "b");
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).save(travelEdited);
	}
	
	@Test
	void deleteTest() {
		this.travelService.delete("b");
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).deleteById("b");
	}

}
