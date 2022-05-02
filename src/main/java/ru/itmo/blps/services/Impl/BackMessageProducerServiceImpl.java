package ru.itmo.blps.services.Impl;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackMessageProducerService;

@Service
@AllArgsConstructor
public class BackMessageProducerServiceImpl implements BackMessageProducerService {

    private static final Logger logger = LoggerFactory.getLogger(BackMessageProducerServiceImpl.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendBackMessage(String topic, BackModel obj) {

        // Time, key and value.
//        ProducerRecord<String, BackModel> producerRecord = new ProducerRecord<>(topic, null, // Let Kafka allocate by itself
//                System.currentTimeMillis(), String.valueOf(obj.hashCode()), obj);
//        Message<BackModel> message = MessageBuilder
//                .withPayload(obj)
//                .setHeader(KafkaHeaders.TOPIC, topic)
//                .build();
        logger.info("Sending message\"{}\" to topic \"{}\"", obj, topic);
        ListenableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(topic, null,
                        System.currentTimeMillis(),
                        String.valueOf(obj.hashCode()),
                        obj);
        logger.info("Result: {}", future);
        // send backing message asynchronously
        future.addCallback(result -> {
            assert result != null;
            logger.info("Producer successfully send to" + topic + "-> " + result.getRecordMetadata().topic(), result.getRecordMetadata().partition());
        }, ex -> logger.error("Producer send: {} Fail, Reason{}", obj, ex.getMessage()));
    }
}
