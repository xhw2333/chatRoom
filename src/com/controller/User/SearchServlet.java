package com.controller.User;

import com.controller.BaseServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.model.User;
import com.service.UserService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 搜索用户接口
 */
@WebServlet(name = "SearchServlet",urlPatterns = "/search")
public class SearchServlet extends BaseServlet {
    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户关键词
        String key = jsonNode.path("key").asText();

        JSONObject json;
        List<User> list = userService.searchFriend(key);
        json = stringify(true,1,list);

        System.out.println(list);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
