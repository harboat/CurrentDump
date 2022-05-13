package com.github.harboat.logger;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MainLogRepository extends MongoRepository<MainServiceLogDocument, String> {
}
