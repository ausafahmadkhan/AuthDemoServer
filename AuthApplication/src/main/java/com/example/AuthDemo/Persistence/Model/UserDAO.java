package com.example.AuthDemo.Persistence.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("AuthUsers")
public class UserDAO
{
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
}
