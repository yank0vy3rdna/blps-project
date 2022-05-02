package ru.itmo.blps.controllers.inputModel;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class BackModel {
    private final Integer projectId;
    private final Integer amount;
    private final String backerUsername;
}
