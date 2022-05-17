package com.github.harboat.core.stats;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface statsRepository extends MongoRepository<PlayerStats, String> {
    Optional<PlayerStats> findByPlayerEmail(String mail);
}
