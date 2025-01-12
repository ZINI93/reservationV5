package com.example.reservationV5.domain.reservation.repository;

import com.example.reservationV5.domain.reservation.entitiy.Reservation;

import java.util.List;

public interface ReservationRepositoryCustom {
    List<Reservation> findReservationsForToday();

    List<Reservation> findReservations(String name, String phoneNumber);

}
