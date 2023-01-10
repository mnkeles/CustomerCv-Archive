package com.customercvarchive.model.dto;


import com.customercvarchive.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer userId;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private Role role;

}
