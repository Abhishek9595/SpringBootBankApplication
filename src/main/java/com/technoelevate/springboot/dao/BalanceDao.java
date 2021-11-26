package com.technoelevate.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.springboot.dto.BalanceDetails;

@Repository
public interface BalanceDao extends JpaRepository<BalanceDetails, Integer>{

}
