package com.github.harboat.battleships.shot;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShotRepository extends MongoRepository<Shot, String> {
}
