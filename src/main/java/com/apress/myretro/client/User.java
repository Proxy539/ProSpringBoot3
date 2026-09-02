package com.apress.myretro.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String email;
    private String name;
    private List<UserRole> userRole;
    private boolean active;
}
