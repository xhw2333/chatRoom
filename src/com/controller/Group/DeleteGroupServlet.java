package com.controller.Group;

import com.controller.BaseServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.service.GroupService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除群接口
 */
@WebServlet(name = "DeleteGroupServlet",urlPatterns = "/group/delete")
public class DeleteGroupServlet extends BaseServlet {
    private GroupService groupService = new GroupService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户id
        int oid = Integer.parseInt(jsonNode.path("oid").asText());
        // 群id
        int group_id = Integer.parseInt(jsonNode.path("group_id").asText());

        JSONObject json;

        if(groupService.deleteGroup(group_id,oid)){
            json = stringify(true,1,"success");
        } else {
            json = stringify(false,0,"error");
        }

        System.out.println(oid + ' ' + group_id);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
