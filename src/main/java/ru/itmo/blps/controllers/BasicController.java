package ru.itmo.blps.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itmo.blps.DAO.utils.JsonResult;
import ru.itmo.blps.services.Exceptions.NoEnoughMoneyException;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;
import ru.itmo.blps.services.Exceptions.WrongStatusException;

import java.util.List;

// Basic of controllers
public class BasicController {
    // Successful operation.
    public static final int OK = 200;

    // Will handle exceptions and send info to frontend.
    @ExceptionHandler
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof NoEnoughMoneyException) {
            result.setState(4000);
            result.setMessage("There is no enough money!");
        } else if (e instanceof NoSuchProjectException) {
            result.setState(5000);
            result.setMessage("Can't find out the project");
        } else if (e instanceof NoSuchUserException) {
            result.setState(5001);
            result.setMessage("You should register at the first.");
        } else if (e instanceof WrongStatusException) {
            result.setState(5002);
            result.setMessage("You can't do that. Because the state of project is wrong.");
        }
        return result;
    }




}
