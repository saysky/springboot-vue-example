package com.example.sens.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.sens.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {


    List<Message> findAll(Pagination pagination);

    List<Message> findByUserIdAndStatus(@Param("userId") Long userId,
                                        @Param("status") String status,
                                        Pagination pagination);

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

    Integer getUnReadSize(Long userId);


}

