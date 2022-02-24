package ru.itmo.blps.DAO.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {
    private Integer id;
    private String name;
    private Integer targetAmount;
    private Integer currentAmount;
    private String description;
    private Integer status;
}
