package ru.itmo.blps.MQ;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackMessageProducerService;

@Service
@AllArgsConstructor
public class BackMessageProducerServiceImpl implements BackMessageProducerService {

    private static final Logger logger = LoggerFactory.getLogger(BackMessageProducerServiceImpl.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendBackMessage(String topic, BackModel obj) {

        logger.info("Sending message\"{}\" to topic \"{}\"", obj, topic);

        ListenableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(
                        topic,
                        null, // Let Kafka allocate by itself
                        System.currentTimeMillis(),
                        String.valueOf(obj.hashCode()),
                        obj);

        // send backing message asynchronously
        future.addCallback(result -> {
            assert result != null;
            logger.info("Producer successfully send to " + topic + "-> " + result.getRecordMetadata().topic(), result.getRecordMetadata().partition());
        }, ex -> logger.error("Producer send: {} Fail, Reason{}", obj, ex.getMessage()));
    }
}
