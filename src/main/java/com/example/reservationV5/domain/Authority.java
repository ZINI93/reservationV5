package com.example.reservationV5.domain;

import jakarta.persistence.*;
import jakarta.validation.valueextraction.UnwrapByDefault;
import lombok.*;

@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id @Column(name = "authority_Name", length = 50)
    private String authorityName;

}
