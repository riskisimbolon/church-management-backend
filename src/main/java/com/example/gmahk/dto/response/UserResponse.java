package com.example.gmahk.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String nama;
    private String email;
    private String role;
    private Integer jemaatId;
    private String status;
}
