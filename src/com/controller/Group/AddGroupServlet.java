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

import static com.utils.TypeChangeUtils.strToInt;

/**
 * 创群接口
 */
@WebServlet(name = "AddGroupServlet",urlPatterns = "/group/add")
public class AddGroupServlet extends BaseServlet {
    private GroupService groupService = new GroupService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        res.setContentType("application/json;charset=utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户id
        int oid = Integer.parseInt(jsonNode.path("oid").asText());
        // 群id
        String group_name = jsonNode.path("group_name").asText();
        // 成员id
        String str = String.valueOf(jsonNode.path("members_id"));
        System.out.println(str);
        JSONObject json;
        System.out.println(oid + " " + group_name);

//        转换为数组形式的member_id数组
        int [] members_id = strToInt(str.substring(1,str.length()-1));

        if(groupService.setGroup(group_name,oid,members_id)){
            json = stringify(true,1,"success");
        } else {
            json = stringify(false,0,"error");
        }
        res.getWriter().println(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
