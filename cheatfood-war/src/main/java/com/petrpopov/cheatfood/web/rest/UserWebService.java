package com.petrpopov.cheatfood.web.rest;

import com.petrpopov.cheatfood.config.CheatException;
import com.petrpopov.cheatfood.model.UserCreate;
import com.petrpopov.cheatfood.model.UserEntity;
import com.petrpopov.cheatfood.security.CheatRememberMeServices;
import com.petrpopov.cheatfood.security.LoginManager;
import com.petrpopov.cheatfood.service.IUserService;
import com.petrpopov.cheatfood.service.UserContextHandler;
import com.petrpopov.cheatfood.web.other.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * User: petrpopov
 * Date: 16.07.13
 * Time: 15:52
 */

@Controller
@RequestMapping("/api")
public class UserWebService {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserContextHandler userContextHandler;

    @Autowired
    private LoginManager loginManager;

    @Autowired
    private CheatRememberMeServices rememberMeServices;

    @RequestMapping(value = "users/create", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public MessageResult processSubmit(@Valid @RequestBody UserCreate user, HttpServletRequest request, HttpServletResponse response) {

        MessageResult res = new MessageResult();

        UserEntity entity;
        try {
            entity = userService.createUser(user);
        } catch (CheatException e) {
            res.setError(true);
            res.setMessage("User is already exists !");
            return res;
        }

        Authentication authenticate = loginManager.authenticate(entity.getId(), user.getPassword());
        rememberMeServices.onLoginSuccess(request, response, authenticate);

        res.setResult(entity);
        return res;
    }

    @RequestMapping(value = "users/current", method = RequestMethod.GET)
    @ResponseBody
    public UserEntity getCurrentUser() {

        UserEntity entity = userContextHandler.currentContextUser();
        return entity;
    }

}