package com.arqui.integrador.mcsvadministrator.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TravelsByTotalBillingAmount {

    private BigDecimal total_value ;


}
