package com.example.sens.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.sens.entity.Message;

import java.util.List;


/**
 * <pre>
 *     消息业务逻辑接口
 * </pre>
 */
public interface MessageService {

    /**
     * 新增/修改消息
     *
     * @param message message
     * @return Role
     */
    void saveByMessage(Message message);

    /**
     * 根据消息Id获得消息
     *
     * @param messageId 消息名
     * @return 消息
     */
    Message findByMessageId(Long messageId);

    /**
     * 删除消息
     *
     * @param messageId 消息Id
     */
    void removeMessage(Long messageId);


    /**
     * 分页获取消息
     *
     * @param page 分页
     * @return 分页数据
     */
    Page<Message> findAll(Page<Message> page);


    Page<Message> findByUserIdAndStatus(Long userId, String status, Page<Message> page);

    /**
     * 全部标记已读
     *
     * @param userId
     * @return
     */
    Integer batchMarkRead(Long userId);

    /**
     * 批量删除
     *
     * @param userId
     * @return
     */
    Integer batchDeleted(Long userId);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    void updateStatus(Long id, String status);


    Integer getUnReadSize(Long userId);


}
