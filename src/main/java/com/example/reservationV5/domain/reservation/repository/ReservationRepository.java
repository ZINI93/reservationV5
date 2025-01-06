package com.example.reservationV5.domain.reservation.repository;

import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.reservation.entitiy.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByMember(Member member);
    Optional<Reservation> findByMemberPhoneNumber(String phoneNumber);

}
