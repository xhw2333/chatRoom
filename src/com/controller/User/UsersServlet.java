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
import java.util.List;

/**
 * 获取用户列表接口
 */
@WebServlet(name = "UsersServlet",urlPatterns = "/list")
public class UsersServlet extends BaseServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        res.setContentType("application/json;charset=utf-8");

        JSONObject json;

        try{
            List<User> list = userService.getUserList();

            json = stringify(true,1,list);
            res.getWriter().println(json);

        }catch(Exception e){
            json = stringify(false,-1,"服务器错误");
            res.getWriter().println(json);
        }
    }
}
