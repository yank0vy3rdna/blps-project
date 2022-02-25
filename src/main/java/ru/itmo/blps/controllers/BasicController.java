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
//        else if (e instanceof NoPermissionException){
//            result.setState(5003);
//            result.setMessage("You have no permission to do this.");
//        }else if (e instanceof UpdateException){
//            result.setState(5004);
//            result.setMessage("Can't update.");
//        }
        return result;
    }




//    /** Get the uid from session
//     * @param session session object
//     * @return recent user's id
//     */
//    protected final Integer getuidFromSession(HttpSession session){
//        return Integer.valueOf(session.getAttribute("uid").toString());
//    }
//    /** Get the username from session
//     * @param session session object
//     * @return recent user's username
//     */
//    protected final String getUserNameFromSession(HttpSession session){
//        return session.getAttribute("username").toString();
//    }
}
