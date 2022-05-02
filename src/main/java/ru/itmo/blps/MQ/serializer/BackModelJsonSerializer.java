package ru.itmo.blps.MQ.serializer;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.Impl.BackMessageProducerServiceImpl;


import java.util.Map;

public class BackModelJsonSerializer implements Serializer<BackModel> {

    private static final Logger logger = LoggerFactory.getLogger(BackModelJsonSerializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }

    @Override
    public byte[] serialize(String top, BackModel backModel) {
        logger.info("Serialize BackModel: {}", backModel);
        return JSON.toJSONBytes(backModel);
    }
}
