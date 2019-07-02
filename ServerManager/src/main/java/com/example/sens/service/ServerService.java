package com.example.sens.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.sens.entity.Server;

import java.util.List;


/**
 * <pre>
 *     服务器业务逻辑接口
 * </pre>
 *
 */
public interface ServerService {

    /**
     * 新增/修改服务器
     *
     * @param server server
     * @return Role
     */
    void saveByServer(Server server);

    /**
     * 根据服务器Id获得服务器
     *
     * @param serverId 服务器名
     * @return 服务器
     */
    Server findByServerId(Long serverId);

    /**
     * 删除服务器
     *
     * @param serverId 服务器Id
     */
    void removeServer(Long serverId);


    /**
     * 分页获取服务器
     *
     * @param page 分页
     * @return 分页数据
     */
    Page<Server> findAll(String queryType, String queryKeywords,Page<Server> page);


    /**
     * 批量删除
     * @param ids
     */
    void batchRemove(List<Long> ids);

    /**
     * 矫正服务器状态
     */
    void correctServerStatus();


    Server findByProjectName(String projectName);
}
