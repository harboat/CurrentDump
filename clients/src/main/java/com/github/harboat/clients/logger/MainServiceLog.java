package com.github.harboat.clients.logger;

import java.sql.Timestamp;

public record MainServiceLog(String serviceId, String body, Timestamp createdAt) {
}
