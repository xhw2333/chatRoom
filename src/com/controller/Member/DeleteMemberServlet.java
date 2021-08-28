package com.controller.Member;

import com.controller.BaseServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.service.MemberService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除群成员接口
 */
@WebServlet(name = "DeleteMemberServlet",urlPatterns = "/member/delete")
public class DeleteMemberServlet extends BaseServlet {
    private MemberService memberService = new MemberService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //群id
        int group_id = Integer.parseInt(jsonNode.path("group_id").asText());
        // 成员id
        int member_id = Integer.parseInt(jsonNode.path("members_id").asText());

        JSONObject json;

        if(memberService.deleteMember(group_id,member_id)){
            json = stringify(true,1,"success");
        } else {
            json = stringify(false,0,"error");
        }

        System.out.println(member_id);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
