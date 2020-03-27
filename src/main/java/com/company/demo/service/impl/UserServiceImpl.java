package com.company.demo.service.impl;

import com.company.demo.entity.User;
import com.company.demo.exception.DuplicateRecordException;
import com.company.demo.model.mapper.UserMapper;
import com.company.demo.model.request.CreateUserReq;
import com.company.demo.repository.UserRepository;
import com.company.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(CreateUserReq req) {
        // Check email exist
        User user = userRepository.findByEmail(req.getEmail());
        if (user != null) {
            throw new DuplicateRecordException("Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác.");
        }

        user = UserMapper.toUser(req);
        userRepository.save(user);

        return user;
    }
}
