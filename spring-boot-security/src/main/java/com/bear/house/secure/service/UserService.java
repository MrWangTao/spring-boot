package com.bear.house.secure.service;

import com.bear.house.secure.entity.User;

import java.util.Optional;

/**
 * @author WangTao
 *         Created at 18/6/4 下午4:14.
 */
public interface UserService {

    Optional<User> findByUserName(String userName);

}
