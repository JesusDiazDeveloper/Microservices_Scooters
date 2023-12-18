package com.arqui.integrador.dto;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class TravelsScooterResponseDto implements Serializable {

	private static final long serialVersionUID = -7822551077921357978L;

	@JsonProperty("id_scooter")
	@Field("_id")
	private int id_scooter;
	@JsonProperty("travel_quantity")
	private Long travel_quantity;

}
