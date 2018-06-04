package com.bear.house.secure.repository;

import com.bear.house.secure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author WangTao
 *         Created at 18/6/4 下午4:11.
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 根据用户名称查询用户详情
     * @param userName
     * @return
     */
    User findByUserName(String userName);

}
