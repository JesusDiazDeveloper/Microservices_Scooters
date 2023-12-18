package com.arqui.integrador.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "Travel")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
	public class Travel implements Serializable {

		private static final long serialVersionUID = -1680823192265785318L;
		@Id
		private String id; 
		private int id_account;
		private int id_user;
		private Integer id_scooter;
		@CreatedDate
		private LocalDateTime start_date;
		@LastModifiedDate
		private LocalDateTime ending_date;
		private int pause_time;
		private BigDecimal km;
		private Double cost;
		private boolean paused;
	}
