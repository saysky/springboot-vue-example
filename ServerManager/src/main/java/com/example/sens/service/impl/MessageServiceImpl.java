package com.example.sens.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.sens.entity.Message;
import com.example.sens.mapper.MessageMapper;
import com.example.sens.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 *     服务器业务逻辑实现类
 * </pre>
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired(required = false)
    private MessageMapper messageMapper;

    @Override
    public void saveByMessage(Message message) {
        if (message != null && message.getId() != null) {
            messageMapper.updateById(message);
        } else {
            messageMapper.insert(message);
        }
    }

    @Override
    public Message findByMessageId(Long messageId) {
        return messageMapper.selectById(messageId);
    }


    @Override
    public void removeMessage(Long messageId) {
        messageMapper.deleteById(messageId);
    }

    @Override
    public Page<Message> findAll(Page<Message> page) {
        return page.setRecords(messageMapper.findAll(page));
    }

    @Override
    public Page<Message> findByUserIdAndStatus(Long userId, String status, Page<Message> page) {
        return page.setRecords(messageMapper.findByUserIdAndStatus(userId, status, page));
    }


    @Override
    public Integer batchMarkRead(Long userId) {
        return messageMapper.batchMarkRead(userId);
    }

    @Override
    public Integer batchDeleted(Long userId) {
        return messageMapper.batchDeleted(userId);
    }

    @Override
    public void updateStatus(Long id, String status) {
        Message message = messageMapper.selectById(id);
        if (message != null) {
            message.setStatus(status);
            messageMapper.updateById(message);
        }
    }

    @Override
    public Integer getUnReadSize(Long userId) {
        return messageMapper.getUnReadSize(userId);
    }

}
