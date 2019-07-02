package com.example.sens.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.sens.entity.Message;
import com.example.sens.entity.User;
import com.example.sens.service.MessageService;
import com.example.sens.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 言曌
 * @date 2019-05-08 13:10
 */

@RestController
@RequestMapping("/api/message")
public class MessageController {


    @Autowired
    private MessageService messageService;

    /**
     * 根据状态查询，不分页
     *
     * @param status
     * @param session
     * @return
     */
    @GetMapping("/paging")
    public Response<Page<Message>> paging(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          @RequestParam(value = "status", required = false, defaultValue = "unread") String status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Page pageable = new Page(pageNo, pageSize);
        Page<Message> messages = null;
        try {
            messages = messageService.findByUserIdAndStatus(user.getUserId(), status, pageable);
        } catch (Exception e) {
            return Response.no();
        }
        return Response.yes(messages);
    }

    //删除消息
    //清空回收站


    /**
     * 标记已读
     *
     * @param id
     * @param status
     * @param session
     * @return
     */
    @PostMapping("/updateStatus")
    public Response<Boolean> delete(@RequestParam("id") Long id,
                                    @RequestParam("status") String status, HttpSession session) {
        try {
            messageService.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no();
        }
        return Response.yes();
    }

    @PostMapping("/delete")
    public Response<Boolean> delete(@RequestParam("id") Long id,
                                    HttpSession session) {
        try {
            messageService.removeMessage(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no();
        }
        return Response.yes();
    }


    /**
     * 全部标记已读
     *
     * @return
     */
    @PostMapping(value = "/batchRead")
    @ResponseBody
    public Response<Boolean> batchRead(HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            messageService.batchMarkRead(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no();
        }
        return Response.yes();
    }

    /**
     * 清空回收站
     *
     * @param session
     * @return
     */
    @PostMapping("/clear")
    public Response<Boolean> clear(HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            messageService.batchDeleted(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no();
        }
        return Response.yes();
    }

    @GetMapping("/unread")
    public Response<Integer> getUnReadSize(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer size = 0;
        try {
            size = messageService.getUnReadSize(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no();
        }
        return Response.yes(size);
    }

}
