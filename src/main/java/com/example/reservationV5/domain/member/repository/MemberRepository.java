package com.example.reservationV5.domain.member.repository;

import com.example.reservationV5.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {



    Optional<Member> findByUsername(String username);
    boolean existsByMemberId(Long memberId);
    boolean existsByUsername(String username); // join duplicateCheck
    Optional<Member> findByPhoneNumber(String phoneNumber);


//    Optional<Member> findByUsername(String username);





}
