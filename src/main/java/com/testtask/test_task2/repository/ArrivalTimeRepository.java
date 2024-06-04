package com.testtask.test_task2.repository;

import com.testtask.test_task2.entity.ArrivalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArrivalTimeRepository extends JpaRepository<ArrivalTime, Long> {
}
