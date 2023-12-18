package com.arqui.integrador.mcsvadministrator.utils;

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;
import com.arqui.integrador.mcsvadministrator.model.Administrator;

public class AdministratorMapper {
    public static AdministratorDTO entityToDto(Administrator administrator){
        return AdministratorDTO.builder()
        .id(administrator.getId())
        .name(administrator.getName())
        .password(administrator.getPassword())
        .rol(administrator.getRol())
        .build();
    }

    public static Administrator dtoToEntity(AdministratorDTO administratorDTO){
        return Administrator.builder()
        .id(administratorDTO.getId())
        .name(administratorDTO.getName())
        .password(administratorDTO.getPassword())
        .rol(administratorDTO.getRol())
        .build();
    }
}
