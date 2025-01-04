package com.example.reservationV5.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;


public enum ReservationStatus {
    CONFIRMED,
    CANCEL
}
