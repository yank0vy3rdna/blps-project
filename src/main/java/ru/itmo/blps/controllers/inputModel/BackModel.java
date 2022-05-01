package ru.itmo.blps.controllers.inputModel;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
public class BackModel implements Serializable {
    private final Integer projectId;
    private final Integer amount;
    private final String backerUsername;

    public BackModel(Integer projectId, Integer amount, String backerUsername) {
        this.projectId = projectId;
        this.amount = amount;
        this.backerUsername = backerUsername;
    }
}
