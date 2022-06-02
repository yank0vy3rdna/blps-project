package ru.itmo.blps.controllers.inputModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
// Make sure the user can only back project as himself.
public class BackRequestModel {
    private final String projectName;
    private final Integer amount;
}
