package com.wecode.service;

import com.wecode.entity.User;
import com.wecode.entity.dto.UserDto;
import com.wecode.entity.util.ChangePasswordVM;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);
    User update(User user);
    User findById(Long id);
    public boolean changePassword(ChangePasswordVM vm , String username);
}
