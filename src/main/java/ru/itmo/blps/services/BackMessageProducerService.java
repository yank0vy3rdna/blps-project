package ru.itmo.blps.services;


public interface BackMessageProducerService {
    // Send message to Kafka
    void sendBackMessage(String top, Object o);
}
