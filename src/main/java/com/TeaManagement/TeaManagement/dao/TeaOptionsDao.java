package com.TeaManagement.TeaManagement.dao;

import com.TeaManagement.TeaManagement.entity.TeaOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TeaOptionsDao extends JpaRepository<TeaOptions, Integer> {
    TeaOptions findByBeverageName(String beverageName);
}
