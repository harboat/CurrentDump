package com.github.harboat.logger;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlacementLogRepository extends MongoRepository<PlacementServiceLogDocument, String> {
}
