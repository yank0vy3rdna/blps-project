package ru.itmo.blps.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.itmo.blps.controllers.inputModel.BackModel;

public interface BackService {
    int back(ConsumerRecord<String, Object> bookConsumerRecord);
}
