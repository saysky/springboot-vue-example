package com.example.sens.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.sens.entity.Server;
import com.example.sens.entity.User;
import com.example.sens.enums.ServerStatus;
import com.example.sens.service.ServerService;
import com.example.sens.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 言曌
 * @date 2019-05-08 13:10
 */

@RestController
@RequestMapping("/api/server")
public class ServerController {


    @Autowired
    private ServerService serverService;

    /**
     * 分页展示服务器
     *
     * @param pageNo
     * @param pageSize
     * @param queryType
     * @param queryKeyword
     * @return
     */
    @GetMapping("/paging")
    public Response<Page<Server>> paging(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "queryType", required = false) String queryType,
                                         @RequestParam(value = "queryKeyword", required = false) String queryKeyword) {
        Page pageable = new Page(pageNo, pageSize);
        Page<Server> page = null;
        try {
            page = serverService.findAll(queryType, queryKeyword, pageable);
        } catch (Exception e) {
            return Response.no();
        }
        return Response.yes(page);
    }


    /**
     * 申请服务器
     *
     * @param server
     * @param session
     * @return
     */
    @PostMapping("/apply")
    public Response applyServer(@RequestBody Server server, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Long now = System.currentTimeMillis();

        Long applyTime = server.getApplyTime();
        Long releaseTime = server.getReleaseTime();
        if(applyTime < now) {
            return Response.no("申请时间不能小于当前时间");
        }
        if(releaseTime < now) {
            return Response.no("释放时间不能小于当前时间");
        }
        if(releaseTime < applyTime) {
            return Response.no("释放时间不能小于申请时间");
        }
        try {
            //1.判断项目名称是否存在
            Server checkProjectName = serverService.findByProjectName(server.getProjectName());
            if (checkProjectName != null && !Objects.equals(checkProjectName.getId(), server.getId())) {
                return Response.no("项目名已存在！");
            }

            //2.添加
            if ((Objects.equals(server.getServerStatus(), ServerStatus.FREE.getDesc())) || (now > (server.getReleaseTime() != null ? server.getReleaseTime() : 0))) {
                server.setApplyUserName(user.getUserName());
                server.setServerStatus(ServerStatus.FREE.getDesc());
                serverService.saveByServer(server);
            } else {
                return Response.no("服务器在使用中，不能申请！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.no("申请失败！");
        }
        return Response.yes();
    }

    /**
     * 释放服务器
     *
     * @param id
     * @return
     */
    @PostMapping("/release")
    public Response releaseServer(@RequestParam("id") Long id) {
        try {
            Server server = serverService.findByServerId(id);
            if (server != null) {
                Long now = System.currentTimeMillis();
                if (!(Objects.equals(server.getServerStatus(), ServerStatus.FREE.getDesc()))) {
//                    server.setReleaseTime(now);
                    server.setServerStatus(ServerStatus.FREE.getDesc());
                    serverService.saveByServer(server);
                } else {
                    return Response.no("服务器已经释放！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no("申请失败！");
        }
        return Response.yes();
    }

    /**
     * 创建服务器
     *
     * @param server
     * @return
     */
    @PostMapping("/create")
    public Response createServer(@RequestBody Server server,HttpSession session) {
        //只有管理员可以删除
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getType())) {
            return Response.no("没有权限添加！");
        }
        try {
            serverService.saveByServer(server);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no("保存失败！");
        }
        return Response.yes();
    }


    /**
     * 删除服务器
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Response<Boolean> delete(@RequestParam("id") Long id, HttpSession session) {
        try {
            //只有管理员可以删除
            User user = (User) session.getAttribute("user");
            if (!"admin".equals(user.getType())) {
                return Response.no("没有权限删除！");
            }
            serverService.removeServer(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no();
        }
        return Response.yes();
    }

    /**
     * 批量删除服务器
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/batchRemove")
    @ResponseBody
    public Response<Boolean> batchRemove(@RequestParam("ids") String ids,HttpSession session) {
        //只有管理员可以删除
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getType())) {
            return Response.no("没有权限删除！");
        }

        if ("".equals(ids)) {
            return Response.no("请至少选择一项！");
        }
        String[] arr = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(Long.valueOf(arr[i]));
        }
        try {

            serverService.batchRemove(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.no();
        }
        return Response.yes();
    }


}
