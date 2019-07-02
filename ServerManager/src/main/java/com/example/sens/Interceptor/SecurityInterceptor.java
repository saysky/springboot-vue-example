package com.example.sens.Interceptor;

import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 言曌
 * @date 2019-05-03 11:40
 */

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //这里可以根据session的用户是否登录
        if (request.getSession().getAttribute("user") == null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                JSONObject res = new JSONObject();
                res.put("success", false);
                res.put("message", "用户未登录！");
                out = response.getWriter();
                out.append(res.toString());
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(500);
                return false;
            }
        }
        return true;
    }
}
