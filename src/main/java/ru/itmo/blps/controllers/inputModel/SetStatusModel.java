package ru.itmo.blps.controllers.inputModel;

import lombok.Data;

@Data
public class SetStatusModel {
    private Integer projectId;
    private Integer status;
}
