package ru.itmo.blps.services;


import ru.itmo.blps.controllers.inputModel.BackModel;

public interface BackMessageProducerService {
    // Send message to Kafka
    void sendBackMessage(String top, BackModel o);
}
