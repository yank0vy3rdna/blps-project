package ru.itmo.blps.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.BackRecord;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.BRMapper;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackService;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;
import ru.itmo.blps.services.Exceptions.ServiceException;

@Service
@Data
@AllArgsConstructor
public class ConsumerImpl implements BackService {

    private final BRMapper brMapper;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(ConsumerImpl.class);

    @Override
    @KafkaListener(topics = "back")
    public int back(ConsumerRecord<String, Object> backConsumerRecord) {

        /*
        Problem is here!!!
         */
        logger.info("Consumer received: " + backConsumerRecord.toString());
//            BackModel bm = objectMapper.readValue(backConsumerRecord.value(), BackModel.class);
        BackModel bm = (BackModel) backConsumerRecord.value();
        logger.info("Received message: " + backConsumerRecord.value());
        Integer projectId = bm.getProjectId();
        Integer amount = bm.getAmount();
        String username = bm.getBackerUsername();
        if (amount < 0) {
            throw new ServiceException("Please give me money! Not steal mine!!!");
        }
        Project project = projectMapper.findProjectById(projectId);
        if (project == null) {
            throw new NoSuchProjectException("Can't find this project.");
        }

        User user = userMapper.findUserByLogin(username);
        if (user == null) {
            throw new NoSuchUserException("No such user.");
        }
        Integer userId = user.getId();

        // Update backer list.
        projectMapper.addBacker(userId, projectId);

        // Insert back record.
        BackRecord br = new BackRecord();
        br.setUserId(userId);
        br.setProjectId(projectId);
        br.setAmount(amount);
        int effectRows = brMapper.insertBR(br);
        Integer new_amount = amount;
        if (project.getCurrentAmount() != null) {
            new_amount += project.getCurrentAmount();
        }

        // Update current amount.
        projectMapper.updateCurrentMoney(projectId, new_amount);

        return effectRows;
    }
}
