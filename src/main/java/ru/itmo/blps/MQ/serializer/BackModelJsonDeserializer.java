package ru.itmo.blps.MQ.serializer;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.blps.controllers.inputModel.BackModel;

import java.util.Map;

// // Deserialize JSON to BackModel by fastjson.
public class BackModelJsonDeserializer implements Deserializer<BackModel> {

    private static final Logger logger = LoggerFactory.getLogger(BackModelJsonDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }

    @Override
    public BackModel deserialize(String s, byte[] bytes) {
        logger.info("Deserialize : {}", new String(bytes));
        return JSON.parseObject(bytes, BackModel.class);
    }
}
