package com.company.demo.model.mapper;

import com.company.demo.entity.User;
import com.company.demo.model.dto.UserDto;
import com.company.demo.model.request.CreateUserReq;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class UserMapper {
    public static User toUser(CreateUserReq req) {
        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        // Hash password using BCrypt
        String hash = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setStatus(true);
        user.setRoles(new ArrayList<String>(Arrays.asList("USER")));

        return user;
    }

    public static UserDto toUserDto(User user) {
        UserDto tmp = new UserDto();
        tmp.setId(user.getId());
        tmp.setFullName(user.getFullName());
        tmp.setPhone(user.getPhone());
        tmp.setEmail(user.getEmail());
        tmp.setAddress(user.getAddress());
        tmp.setRoles(user.getRoles());

        return tmp;
    }
}
