package ru.itmo.blps.services.Impl;

import lombok.SneakyThrows;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.services.CamundaRequestSender;

import java.util.HashMap;
import java.util.Map;

@Service
public class CamundaRequestSenderImpl implements CamundaRequestSender {
    private static final String camundaBaseUrl = "http://localhost:8080/engine-rest";

    @SneakyThrows
    private void createTaskRequest(String taskKey, Map<String, Object> variables) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestJsonObject = new JSONObject();
        JSONObject variablesJsonObject = new JSONObject();
        for (String varName : variables.keySet()) {
            JSONObject variableJsonObject = new JSONObject();
            Object object = variables.get(varName);
            if (object instanceof Integer) {
                variableJsonObject.put("type", "Integer");
            }
            if (object instanceof String) {
                variableJsonObject.put("type", "String");
            }
            variableJsonObject.put("value", object);
            variablesJsonObject.put(varName, variableJsonObject);
        }
        requestJsonObject.put("variables", variablesJsonObject);
        HttpEntity<String> request =
                new HttpEntity<>(requestJsonObject.toString(), headers);
        restTemplate.postForObject(camundaBaseUrl + "/process-definition/key/" + taskKey + "/start", request, String.class);
    }

    @Override
    public void createProject(User user, Project project) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", user.getId());
        variables.put("project_name", project.getName());
        variables.put("project_description", project.getDescription());
        variables.put("project_target_amount", project.getTargetAmount());
        this.createTaskRequest("createProject", variables);
    }

    @Override
    public void backProject(User user, int amount, String projectName) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", user.getId());
        variables.put("project_name", projectName);
        variables.put("back_amount", amount);
        this.createTaskRequest("backProject", variables);
    }
}
