package com.github.harboat.clients.notification;

public record NotificationRequest<T>(String userId, EventType type, T body) {
}
