package com.controller.History;

import com.controller.BaseServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.model.Msg;
import com.service.MsgService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 获取消息记录接口
 */
@WebServlet(name = "MsgHistoryServlet",urlPatterns = "/msg/history")
public class MsgHistoryServlet extends BaseServlet {
    private MsgService msgService = new MsgService();
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        JsonNode jsonNode = thisInit(req);
        //用户id
        int uid = Integer.parseInt(jsonNode.path("uid").asText());
        //别人id
        int accept_id = Integer.parseInt(jsonNode.path("accept_id").asText());
        //标识
        int target = Integer.parseInt(jsonNode.path("target").asText());

        JSONObject json;
        List<Msg> list = msgService.getMyMsg(uid,accept_id,target);
        if(!list.isEmpty()){
            json = stringify(true,1,list);
        } else {
            json = stringify(false,1,null);
        }

        System.out.println(uid);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
