package com.arqui.integrador.mcsvmaintenance.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListOfIdsToUpdateDTO {
    private List<Long> list;
     
}
