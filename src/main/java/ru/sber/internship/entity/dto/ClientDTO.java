package ru.sber.internship.entity.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClientDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

}
