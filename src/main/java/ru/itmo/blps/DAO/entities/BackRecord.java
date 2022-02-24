package ru.itmo.blps.DAO.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackRecord implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer projectId;
    private Integer amount;
}
