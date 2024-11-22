package dev.kauanmocelin.notification;

public record NotificationRequest(
    Integer toCustomerId,
    String toCustomerName,
    String message) {
}
