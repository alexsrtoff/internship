package ru.sber.internship.entity.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientDTO)) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(id, clientDTO.id) &&
                Objects.equals(firstName, clientDTO.firstName) &&
                Objects.equals(lastName, clientDTO.lastName) &&
                Objects.equals(email, clientDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }


}
