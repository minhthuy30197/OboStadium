package com.company.demo.service;

import com.company.demo.entity.User;
import com.company.demo.model.request.CreateUserReq;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User createUser(CreateUserReq req);
}
