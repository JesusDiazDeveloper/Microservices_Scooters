package com.arqui.integrador.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price implements Serializable {
	private static final long serialVersionUID = -1680823192265785318L;
	@Id
	private String id;
	private BigDecimal price_by_hour;
	private BigDecimal rate_of_increase;
	private Timestamp actual_date; // Tenia LocalDate
}
