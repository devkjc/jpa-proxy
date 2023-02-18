package com.study.jpaproxy.repository;

import com.study.jpaproxy.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
