package com.example.sens.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <pre>
 *     用户信息
 * </pre>
 *
 */
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = -5144055068797033748L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 密码
     */
    private String userPass;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 未读消息数
     */
    @TableField(exist = false)
    private Integer unReadSize;


}
