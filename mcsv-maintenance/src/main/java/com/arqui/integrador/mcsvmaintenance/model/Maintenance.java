package com.arqui.integrador.mcsvmaintenance.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "maintenances")
public class Maintenance implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "maintenance_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_maintenance;

	private LocalDate start_date;
 
	private LocalDate end_date;

    private Long id_scooter;

    private float scooter_km;

}
