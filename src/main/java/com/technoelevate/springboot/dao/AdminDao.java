package com.technoelevate.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.springboot.dto.Admin;
@Repository
public interface AdminDao extends JpaRepository<Admin, String> {
	Admin findByUsername(String userName);
}
