package com.example.mobileappserver.service;


import com.example.mobileappserver.model.User;

import com.example.mobileappserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteWorker(int workerid) {
        userRepository.deleteById(workerid);
    }

    public void addWorker(User user) {
        userRepository.save(user);
    }

    public void updateWorker(User user) throws Exception {
        User changeUser = userRepository.findById(user.getId()).orElseThrow(() -> new Exception("User doesnit exsist"));
        changeUser.setName(user.getName());
        changeUser.setAbility(user.getAbility());
        changeUser.setGender(user.getGender());
        changeUser.setActive(user.getActive());
        changeUser.setIsAdmin(user.getIsAdmin());
        changeUser.setWorkingYears(user.getWorkingYears());
        userRepository.save(changeUser);
    }

    public User login(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("Wrong password!"));
        return user;
    }

    public void changeDeviceToken(String id, String deviceToken) throws Exception {
        User user = userRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new Exception("Could not find user by id"));
        user.setDeviceToken(deviceToken);
        user.setRegistered(true);
        userRepository.save(user);
    }

    public User find(String id) throws Exception {
        return userRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new Exception("Could not find user"));
    }

    public List<User> findAllWorkers() {
        return userRepository.findByIsAdmin(false);
    }

}
