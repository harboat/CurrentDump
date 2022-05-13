package com.github.harboat.placement;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlacementRepository extends MongoRepository<Placement, String> {
}
