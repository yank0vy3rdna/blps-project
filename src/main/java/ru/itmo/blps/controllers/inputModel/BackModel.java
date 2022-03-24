package ru.itmo.blps.controllers.inputModel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BackModel {
    private final Integer projectId;
    private final Integer amount;
}
