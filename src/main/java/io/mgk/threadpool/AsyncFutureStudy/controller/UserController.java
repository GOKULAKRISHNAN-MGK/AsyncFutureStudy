package io.mgk.threadpool.AsyncFutureStudy.controller;

import io.mgk.threadpool.AsyncFutureStudy.entity.User;
import io.mgk.threadpool.AsyncFutureStudy.entity.UserDtls;
import io.mgk.threadpool.AsyncFutureStudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void AddAllUsers(@RequestParam(value = "files") MultipartFile[] files) {
        for(MultipartFile file:files) {
            userService.addAllUsers(file);
        }
    }

    @GetMapping(value = "/users",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<List<User>> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(value = "/usersMultiple",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void findAllUsersMultiple() {
        CompletableFuture<List<User>> user_one = userService.findAllUsers();
        CompletableFuture<List<User>> user_two = userService.findAllUsers();
        CompletableFuture<List<User>> user_three = userService.findAllUsers();
        CompletableFuture.allOf(user_one, user_two, user_three).join();
    }

    @GetMapping(value = "/userDtls",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<List<UserDtls>> verifyUserDetails() {
        return userService.verifyUsrDtls();
    }
}
