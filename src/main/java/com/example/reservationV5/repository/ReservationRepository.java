package com.example.reservationV5.repository;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByMember(Member member);
    Optional<Reservation> findByMemberPhoneNumber(String phoneNumber);

}
