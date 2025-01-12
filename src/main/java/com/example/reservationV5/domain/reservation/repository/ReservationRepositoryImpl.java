package com.example.reservationV5.domain.reservation.repository;

import com.example.reservationV5.domain.member.entity.QMember;
import com.example.reservationV5.domain.reservation.dto.ReservationDto;
import com.example.reservationV5.domain.reservation.entitiy.QReservation;
import com.example.reservationV5.domain.reservation.entitiy.Reservation;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.PrimitiveIterator;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Reservation> findReservationsForToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        QReservation reservation = QReservation.reservation;

        return queryFactory.selectFrom(reservation)
                .where(reservation.dateTime.between(startOfDay,endOfDay))
                .fetch();
    }

    @Override
    public List<Reservation> findReservations(String name, String phoneNumber) {
        QReservation reservation = QReservation.reservation;
        QMember member = QMember.member;

        //예외 처리
        BooleanExpression namePredicate = (name != null && !name.isEmpty()) ? member.name.containsIgnoreCase(name) : null;
        BooleanExpression phonePredicate = (phoneNumber != null && !phoneNumber.isEmpty()) ? member.phoneNumber.containsIgnoreCase(phoneNumber) : null;

        return queryFactory.selectFrom(reservation)
                .leftJoin(reservation.member, member)
                .where(namePredicate, phonePredicate)
                .fetch();
    }
}



