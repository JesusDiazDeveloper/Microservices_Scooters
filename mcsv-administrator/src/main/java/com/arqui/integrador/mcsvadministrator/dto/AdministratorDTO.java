package com.arqui.integrador.mcsvadministrator.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdministratorDTO {
    
    private Long id;

    private String name;

    private String password;

    private String rol; 

}
