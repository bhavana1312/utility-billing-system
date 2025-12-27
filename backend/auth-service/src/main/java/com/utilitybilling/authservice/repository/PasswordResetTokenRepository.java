package com.utilitybilling.authservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.utilitybilling.authservice.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken,String>{

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUserId(String userId);
}
