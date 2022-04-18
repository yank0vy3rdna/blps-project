package ru.itmo.blps.controllers.inputModel;

import lombok.Data;

@Data
public class SetRoleModel {
    private final Integer userId;
    private final Integer role;
}
