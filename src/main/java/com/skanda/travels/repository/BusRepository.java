package com.skanda.travels.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skanda.travels.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {}
