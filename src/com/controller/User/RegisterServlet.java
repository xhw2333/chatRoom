package com.controller.User;

import com.controller.BaseServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.model.User;
import com.service.UserService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注册接口
 */
@WebServlet(name = "RegisterServlet",urlPatterns = "/register")
public class RegisterServlet extends BaseServlet {
    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户名
        String username = jsonNode.path("username").asText();
        //密码
        String password = jsonNode.path("password").asText();

        JSONObject json;

        if(userService.register(username,password)){
            json = stringify(true,1,"success");
        } else {
            json = stringify(false,0,"error");
        }

        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
