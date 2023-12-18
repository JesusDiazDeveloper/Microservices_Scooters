package com.arqui.integrador.model;

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
@Table(name = "scooter")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scooter {
	
	@Id
	@Column(name = "scooter_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private boolean enabled;
	
	@Column(name = "kms_traveled")
	private float kmsTraveled;
	
	@Column(name = "used_time")
	private float usedTime;
	
	@Column
	private double latitude;
	
	@Column
	private double longitude;
	
	@Column(name = "station_id", nullable=false)
	private Long stationId;
}
