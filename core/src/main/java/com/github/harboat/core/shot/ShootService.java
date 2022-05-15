package com.github.harboat.core.shot;

import com.github.harboat.core.GameQueueProducer;
import org.springframework.stereotype.Service;

@Service
public class ShootService {

    private GameQueueProducer producer;

    public void takeAShoot(String gameId, String username, Integer cellId) {
    }
}
