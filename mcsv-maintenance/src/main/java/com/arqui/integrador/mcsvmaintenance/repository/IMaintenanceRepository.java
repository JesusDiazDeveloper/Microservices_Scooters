package com.arqui.integrador.mcsvmaintenance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.mcsvmaintenance.model.Maintenance;

@Repository
public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
    
}
