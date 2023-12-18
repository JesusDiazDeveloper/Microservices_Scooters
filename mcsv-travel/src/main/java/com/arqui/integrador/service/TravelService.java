package com.arqui.integrador.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arqui.integrador.dto.BillDto;
import com.arqui.integrador.dto.PausedTimeResponseDto;
import com.arqui.integrador.dto.PriceDto;
import com.arqui.integrador.dto.TravelDto;
import com.arqui.integrador.dto.TravelsScooterResponseDto;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.model.Price;
import com.arqui.integrador.model.Travel;
import com.arqui.integrador.repository.PriceRepository;
import com.arqui.integrador.repository.TravelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TravelService {

  private TravelRepository repository;
  private PriceRepository priceRepository;
  private final MongoTemplate mongoTemplate;

  private static final Logger LOG = LoggerFactory.getLogger(TravelService.class);

  private ObjectMapper mapper;

  public TravelService(
      TravelRepository repository,
      @Qualifier("mapper") ObjectMapper mapper,
      PriceRepository priceRepository,
      MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;

    this.repository = repository;
    this.priceRepository = priceRepository;
    this.mapper = mapper;
  }

  public TravelDto getById(String id) {
    Travel t = repository.findById(id).orElseThrow(
        () -> new ItemNotFoundException("Item not found.", "Item with id: " + id + " not found."));
    return mapper.convertValue(t, TravelDto.class);
  }

  public void createTravel(TravelDto t) {
    Travel t1 = mapper.convertValue(t, Travel.class);
    repository.save(t1);
  }

  public List<TravelDto> getAll() {
    List<TravelDto> list = new ArrayList<>();
    List<Travel> resp = repository.findAll();
    for (Travel travel : resp) {
      TravelDto t = mapper.convertValue(travel, TravelDto.class);
      list.add(t);
    }
    return list;
  }

  @Transactional
  public void update(TravelDto t, String id) {
    Travel t1 = repository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    t1 = mapper.convertValue(t, Travel.class);
    t1.setCost(t.getCost());
    t1.setEnding_date(t.getEnding_date());
    t1.setKm(t.getKm());
    t1.setId(id);
    repository.save(t1);
  }

  public void delete(String id) {
    repository.deleteById(id);
  }

  public List<PausedTimeResponseDto> getAllByPause() {
    List<PausedTimeResponseDto> result = repository.getAllByPause();
    LOG.info(result.toString());
    return result;
  }

  public BillDto getBills(int year, int month1, int month2) {

    List<TravelDto> t = this.getTravels(year, month1, month2);
    Double result = 0.0;
    for (TravelDto travelDto : t) {
      result += travelDto.getCost();
    }

    BillDto billDto = BillDto.builder().total(result).build();
    return billDto;
  }

  private List<TravelDto> getTravels(int year, int month1, int month2) {
    Date startDateMonth1 = convertToStartDate(year, month1);
    Date endDateMonth2 = convertToEndDate(year, month2);

    Aggregation aggregation = Aggregation.newAggregation(
        // Match documents based on the conditions
        Aggregation.match(
            Criteria.where("start_date").gte(startDateMonth1).lte(endDateMonth2).and("ending_date").lt(endDateMonth2)),
        // Project to shape the output
        Aggregation.project()
            .and("id").as("id")
            .and("id_account").as("id_cuenta")
            .and("id_user").as("id_usuario")
            .and("id_scooter").as("id_scooter")
            .and("start_date").as("start_date")
            .and("ending_date").as("ending_date")
            .and("km").as("km")
            .and("cost").as("cost")
            .and("paused").as("paused")
            .and("pause_time").as("pause_time"));

    return mongoTemplate.aggregate(aggregation, "Travel", TravelDto.class)
        .getMappedResults();
  }

  private Date convertToStartDate(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1); // Month in Calendar is 0-based
    calendar.set(Calendar.DAY_OF_MONTH, 1); // Set day to the first day of the month

    return calendar.getTime();
  }

  private Date convertToEndDate(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1); // Month in Calendar is 0-based
    calendar.set(
        Calendar.DAY_OF_MONTH,
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set day to the last
    // day of the month

    return calendar.getTime();
  }

  public List<TravelsScooterResponseDto> getAllByYearQuantity(
      int year,
      Long quantity) {
    return repository.getQuantityByYear(year, quantity);
  }

  public void newPrice(PriceDto p) {
    Price p1 = mapper.convertValue(p, Price.class);
    LOG.info("new price: {}", p1);
    priceRepository.save(p1);
  }
}
