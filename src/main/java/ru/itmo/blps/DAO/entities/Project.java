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
    private Integer initiator_id;
    private Integer target_amount;
    private Integer current_amount;
}