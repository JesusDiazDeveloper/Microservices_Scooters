package com.arqui.integrador.mcsvadministrator.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FareNewPriceDTO {
    private LocalDate date;
    private double price;
}
