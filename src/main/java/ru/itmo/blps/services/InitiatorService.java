package ru.itmo.blps.services;

public interface InitiatorService {
    void takeMoney(Integer uid, Integer pid, Integer amount);
}
