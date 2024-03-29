package com.example.AuthDemo.Persistence.Repository;

import com.example.AuthDemo.Persistence.Model.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDAO, String>
{
    @Query("{'username' : ?0}")
    Optional<UserDAO> findUserByUserName(String username);
}
