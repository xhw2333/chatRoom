package com.controller.User;

import com.controller.BaseServlet;
import com.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.service.UserService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录接口
 */
@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends BaseServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户名
        String username = jsonNode.path("username").asText();
        //密码
        String password = jsonNode.path("password").asText();

        User user = userService.login(username,password);
        JSONObject json;

        if(user != null){
            json = stringify(true,1,user);
        } else {
            json = stringify(false,0,null);
        }

        System.out.println(username + ',' + password);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.getWriter().println(1);
    }

}
