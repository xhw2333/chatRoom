package com.controller.Group;

import com.controller.BaseServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.model.Group;
import com.service.GroupService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 获取成员列表接口
 */
@WebServlet(name = "GroupListServlet",urlPatterns = "/group")
public class GroupListServlet extends BaseServlet {
    private GroupService groupService = new GroupService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户id
        int uid = Integer.parseInt(jsonNode.path("uid").asText());

        JSONObject json;

        List<Group> list = groupService.getMyGroup(uid);

        if(!list.isEmpty()){
            json = stringify(true,1,list);
        } else {
            json = stringify(false,1,null);
        }

        System.out.println("自己id"+uid);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
