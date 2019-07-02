package com.example.sens.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     服务器信息
 * </pre>
 *
 */
@Data
@TableName("server")
public class Server implements Serializable {

    private static final long serialVersionUID = -5144055068797033748L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 使用人
     */
    private String applyUserName;


    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 服务器状态
     */
    private String serverStatus;

    /**
     * 申请时间
     */
    private Long applyTime;

    /**
     * 释放时间
     */
    private Long releaseTime;

    /**
     * 项目名称
     */
    private String projectName;

}
