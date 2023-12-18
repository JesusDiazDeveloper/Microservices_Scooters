package com.arqui.integrador.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class TravelDto implements Serializable {
	
	private static final long serialVersionUID = 5534018998179223152L;

	@JsonProperty("id")
	private String id;
	@JsonProperty("id_account")
	private int id_account;
	@JsonProperty("id_user")
	private int id_usuario;
	@JsonProperty("id_scooter")
	private int id_scooter;
	@JsonProperty("start_date")
	private LocalDateTime start_date;
	@JsonProperty("ending_date")
	private LocalDateTime ending_date;
	@JsonProperty("km")
	private BigDecimal km;
	@JsonProperty("cost")
	private Double cost;
	@JsonProperty("paused")
	private boolean paused;
	@JsonProperty("pause_time")
	private int pause_time;
}
