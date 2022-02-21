package ru.itmo.blps.DAO.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Back_record implements Serializable {
    private Integer id;
    private Integer user_id;
    private Integer project_id;
    private Integer amount;
}