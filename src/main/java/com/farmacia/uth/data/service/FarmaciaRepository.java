package com.farmacia.uth.data.service;

import com.farmacia.uth.data.entity.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long>, JpaSpecificationExecutor<Farmacia> {

}
