package com.study.jpaproxy;

import com.study.jpaproxy.domain.Member;
import com.study.jpaproxy.domain.Team;
import com.study.jpaproxy.repository.MemberRepository;
import com.study.jpaproxy.repository.TeamRepository;
import org.hibernate.proxy.HibernateProxy;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManagerFactory;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EntityManagerFactory emf;

    @Test
    void 프록시_초기화() {
        //given
        Member member = memberRepository.findById(1L).orElseThrow();
        Team team = member.getTeam();
        //when
        //then
        System.out.println("team.getId() :: " + team.getId());
        boolean isLoadedGetId = emf.getPersistenceUnitUtil().isLoaded(team);
        assertThat(isLoadedGetId).isFalse();

        System.out.println("team.getName() :: " + team.getName());
        boolean isLoadedGetName = emf.getPersistenceUnitUtil().isLoaded(team);
        assertThat(isLoadedGetName).isTrue();
        assertThat(team).isInstanceOf(HibernateProxy.class);
    }

    @Test
    void 프록시_동일성_보장() {
        //given
        Member member = memberRepository.findById(1L).orElseThrow();
        Team proxyTeam = member.getTeam();
        String name = proxyTeam.getName();
        System.out.println("teamName :: " + name);
        assertThat(proxyTeam).isInstanceOf(HibernateProxy.class);
        //when
        Team repositoryTeam = teamRepository.findById(1L).orElseThrow();
        //then
        assertThat(repositoryTeam).isInstanceOf(HibernateProxy.class);
    }

    @Test
    void 엔티티_동일성_보장() {
        //given
        Team repositoryTeam = teamRepository.findById(1L).orElseThrow();
        Member member = memberRepository.findById(1L).orElseThrow();
        Team memberTeam = member.getTeam();
        String name = memberTeam.getName();
        System.out.println("name :: " + name);
        //when
        //then
        assertThat(repositoryTeam).isNotInstanceOf(HibernateProxy.class);
        assertThat(memberTeam).isNotInstanceOf(HibernateProxy.class);
    }
}
