package com.bear.house.mybatis.entity;

import com.bear.house.mybatis.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangTao
 *         Created at 18/7/10 上午11:14.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String userName;
    private String name;
    private String password;
    private String salt;
    private StatusEnum status;
    private String note;

}
