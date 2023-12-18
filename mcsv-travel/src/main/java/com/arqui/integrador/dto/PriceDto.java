package com.arqui.integrador.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
public class PriceDto implements Serializable {

	private static final long serialVersionUID = 6393167105538475504L;

	@JsonProperty("price_by_hour")
	private BigDecimal price_by_hour;
	@JsonProperty("rate_of_increase")
	private BigDecimal rate_of_increase;
	@JsonProperty("actual_date")
	private Timestamp actual_date; 
}
