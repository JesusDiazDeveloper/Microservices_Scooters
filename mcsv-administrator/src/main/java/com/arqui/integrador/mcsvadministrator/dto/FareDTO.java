package com.arqui.integrador.mcsvadministrator.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class FareDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigDecimal price_by_hour;
    private BigDecimal rate_of_increase;
    private Timestamp actual_date;
}
