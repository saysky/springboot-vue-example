package com.example.sens.config;

import com.example.sens.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author 言曌
 * @date 2018/12/24 下午4:55
 */
@Component
@Slf4j
public class ScheduledTasks {

    @Autowired
    private ServerService serverService;

    /**
     * 轮训修改状态
     */
    @Scheduled(cron = "*/60 * * * * ?")
    public void autoUpdateServerStatus() {
        //1.查询服务器释放时间<当前时间 && 当前状态是used的，全部改成free
        serverService.correctServerStatus();
    }

}
