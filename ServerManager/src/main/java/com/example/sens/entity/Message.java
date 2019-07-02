package com.example.sens.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     消息信息
 * </pre>
 *
 */
@Data
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = -5144055068797033748L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 状态
     */
    private String status;

    /**
     * 内容
     */
    private String content;

    /**
     * 接受人
     */
    private Long userId;


    private Long createTime;


}
