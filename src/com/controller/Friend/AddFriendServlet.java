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
 * 添加好友接口
 */
@WebServlet(name = "AddFriendServlet",urlPatterns = "/friends/add")
public class AddFriendServlet extends BaseServlet {
    private FriendService friendService = new FriendService();

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户id
        int uid = Integer.parseInt(jsonNode.path("uid").asText());
        // 好友id
        int friend_id = Integer.parseInt(jsonNode.path("friend_id").asText());

        JSONObject json;

        if(friendService.addFriend(uid,friend_id)){
            json = stringify(true,1,"success");
        } else {
            json = stringify(false,0,"error");
        }

        System.out.println("用户id"+uid);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
