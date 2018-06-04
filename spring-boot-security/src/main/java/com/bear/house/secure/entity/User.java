package com.bear.house.secure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author WangTao
 *         Created at 18/6/4 下午4:07.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    private String id;
    private String name;
    private String userName;
    private String password;
    private String salt = "littlebear";
    private LocalDateTime createTime;
    private LocalDateTime updateTine;
    private String note;
}
