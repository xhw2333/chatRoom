package com.controller.Friend;

import com.controller.BaseServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.model.User;
import com.service.FriendService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 获取好友列表接口
 */
@WebServlet(name = "GetFriendListServlet",urlPatterns = "/friends")
public class GetFriendListServlet extends BaseServlet {
    private FriendService friendService = new FriendService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户id
        int uid = Integer.parseInt(jsonNode.path("uid").asText());

        List<User> friends = friendService.getMyFriend(uid);
        JSONObject json;

        if(!friends.isEmpty()){
            json = stringify(true,1,friends);
        } else {
            json = stringify(true,1,null);
        }

        System.out.println("自己id"+uid);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
