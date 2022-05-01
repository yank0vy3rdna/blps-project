package ru.itmo.blps.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface BackService {
    int back(ConsumerRecord<String, Object> bookConsumerRecord);
}
