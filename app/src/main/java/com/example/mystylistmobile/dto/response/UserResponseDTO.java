package com.example.mystylistmobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String seasonalColor;

    private String bodyShape;

    private String styleType;
}
