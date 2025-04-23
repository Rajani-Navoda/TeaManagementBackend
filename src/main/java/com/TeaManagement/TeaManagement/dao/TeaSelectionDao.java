package com.TeaManagement.TeaManagement.dao;

import com.TeaManagement.TeaManagement.entity.TeaSelection;
import com.TeaManagement.TeaManagement.entity.enums.Teatime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface TeaSelectionDao extends JpaRepository<TeaSelection, Integer> {

    List<TeaSelection> findAllByTeatimeAndLocalDateTimeBetween(Teatime teatime, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<TeaSelection> findAllByLocalDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
