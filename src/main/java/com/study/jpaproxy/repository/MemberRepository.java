package com.study.jpaproxy.repository;

import com.study.jpaproxy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
