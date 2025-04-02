package com.TeaManagement.TeaManagement.dao;

import com.TeaManagement.TeaManagement.entity.TeaSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TeaSelectionDao extends JpaRepository<TeaSelection, Integer> {
}
