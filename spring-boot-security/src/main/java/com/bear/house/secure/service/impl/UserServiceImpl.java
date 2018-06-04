package com.bear.house.secure.service.impl;

import com.bear.house.secure.entity.User;
import com.bear.house.secure.repository.UserRepository;
import com.bear.house.secure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author WangTao
 *         Created at 18/6/4 下午4:15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }
}
