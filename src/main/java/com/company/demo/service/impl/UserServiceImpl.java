package com.company.demo.service.impl;

import com.company.demo.entity.User;
import com.company.demo.exception.BadRequestException;
import com.company.demo.exception.DuplicateRecordException;
import com.company.demo.model.mapper.UserMapper;
import com.company.demo.model.request.ChangePasswordReq;
import com.company.demo.model.request.CreateUserReq;
import com.company.demo.model.request.UpdateProfileReq;
import com.company.demo.repository.UserRepository;
import com.company.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

    @Override
    public void changePassword(User user, ChangePasswordReq req) {
        // Validate password
        if (!BCrypt.checkpw(req.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }

        String hash = BCrypt.hashpw(req.getNewPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        userRepository.save(user);
    }

    @Override
    public User updateProfile(User user, UpdateProfileReq req) {
        user.setAddress(req.getAddress());
        user.setPhone(req.getPhone());
        user.setFullName(req.getFullName());

        return userRepository.save(user);
    }
}
