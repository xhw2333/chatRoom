package com.controller.Member;

import com.controller.BaseServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.model.Member;
import com.service.MemberService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 获取群成员接口
 */
@WebServlet(name = "GroupMembersServlet",urlPatterns = "/member/list")
public class GroupMembersServlet extends BaseServlet {
    private MemberService memberService = new MemberService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //群id
        int group_id = Integer.parseInt(jsonNode.path("group_id").asText());

        JSONObject json;

        List<Member> memberList = memberService.getGroupMember(group_id);
        json = stringify(true,1,memberList);

        System.out.println(group_id);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
