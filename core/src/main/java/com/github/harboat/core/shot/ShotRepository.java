package com.github.harboat.core.shot;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ShotRepository extends MongoRepository<Shot, String> {
}
