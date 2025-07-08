package com.example.ticket.service;

import com.example.ticket.model.User;
import java.util.List;

public interface IUserService {
    User findById(Long id);
    List<User> findAll();
    User save(User user);
    void deleteById(Long id);
} 