package io.mgk.threadpool.AsyncFutureStudy.service;

import io.mgk.threadpool.AsyncFutureStudy.entity.User;
import io.mgk.threadpool.AsyncFutureStudy.entity.UserDtls;
import io.mgk.threadpool.AsyncFutureStudy.repository.UserDtlsRepository;
import io.mgk.threadpool.AsyncFutureStudy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDtlsRepository userDtlsRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Async
    public void addAllUsers(MultipartFile file) {
        long start = System.currentTimeMillis();
        List<User> users = parseCsvFile(file);
        logger.info("Saving List of Users of Size- {} and {}", users.size(), Thread.currentThread().getName());
        userRepository.saveAll(users);
        long end = System.currentTimeMillis();
        logger.info("Total time taken - {}",end-start);
        CompletableFuture.completedFuture(users);
    }

    @Async
    public CompletableFuture<List<User>> findAllUsers() {
        logger.info("get list of user by {}",Thread.currentThread().getName());
        List<User> users = userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }

    @Async
    public CompletableFuture<List<UserDtls>> verifyUsrDtls() {
        List<User> users = userRepository.findAll();
        List<UserDtls> userDtls = setUsrDtlsFrmUsr(users);
        userDtlsRepository.saveAll(userDtls);
        return CompletableFuture.completedFuture(userDtls);
    }

    private List<UserDtls> setUsrDtlsFrmUsr(List<User> users) {
        List<UserDtls> userDtls = new ArrayList<>();
        users.stream().filter(user -> user.getGender().equals("Male")).forEach(user->{
            final UserDtls userDtl = new UserDtls();
            userDtl.setUserId(user.getId());
            userDtl.setShift("Night");
            userDtl.setReserved(false);
            userDtls.add(userDtl);
        });
        users.stream().filter(user -> !user.getGender().equals("Male")).forEach(user->{
            final UserDtls userDtl = new UserDtls();
            userDtl.setUserId(user.getId());
            userDtl.setShift("Day");
            userDtl.setReserved(true);
            userDtls.add(userDtl);
        });
        logger.info("get list of user by {}",Thread.currentThread().getName());
        return userDtls;
    }

    private List<User> parseCsvFile(MultipartFile file) {
        final List<User> users = new ArrayList<>();
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            while ((line=br.readLine())!=null) {
                final String[] data = line.split(",");
                final User user = new User();
                user.setName(data[0]);
                user.setEmail(data[1]);
                user.setGender(data[2]);
                users.add(user);
            }
            return users;
        } catch (final IOException e) {
            logger.error("Failed to Parse CSV File - ", e);
            throw new RuntimeException("Failed to Parse CSV File - ", e);
        }
    }
}
