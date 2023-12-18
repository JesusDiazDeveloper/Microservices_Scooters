package com.arqui.integrador.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.dto.BillDto;
import com.arqui.integrador.dto.PausedTimeResponseDto;
import com.arqui.integrador.dto.TravelsScooterResponseDto;
import com.arqui.integrador.model.Price;
import com.arqui.integrador.model.Travel;

@Repository
public interface TravelRepository extends MongoRepository<Travel, String> {

    /*
     * @Aggregation({
     * "{$match: { $expr: { $and: [ { $gte: [ '$start_date', ?0 ] }, { $lte: [ '$ending_date', ?1 ] } ] } } }"
     * ,
     * "{$group: { _id: null, total: { $sum: '$cost' } } }",
     * "{$project: { _id: 0, total: 1 } }"
     * })
     * BillDto getBillsByDate(Date startDate, Date endDate);
     */

    @Aggregation({
            "{ $group: { _id: '$id_scooter', totalPauseTime: { $sum: '$pause_time' } } }",
            "{ $project: { _id: '$_id', id_scooter: '$_id', pause_time: '$totalPauseTime' } }"
    })
    List<PausedTimeResponseDto> getAllByPause();

    void save(Price p1);

    @Aggregation({
            "{ $match: { $expr: { $and: [ { $eq: [ { $year: '$start_date' }, ?0 ] }, { $eq: [ { $year: '$ending_date' }, ?0 ] } ] } } }",
            "{ $group: { _id: '$id_scooter', count: { $sum: 1 } } }",
            "{ $match: { count: { $gte: ?1 } } }",
            "{ $project: { _id: '$_id', id_scooter: '$_id', travel_quantity: '$count' } }"
    })
    List<TravelsScooterResponseDto> getQuantityByYear(int year, Long quantity);
    // "{ $match: { count: { $gt: ?1 } } }", // Si queremos que sea Solo mayor y no
    // mayor o igual, es $gt en vez de $gte

}
