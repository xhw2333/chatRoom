package com.controller.Msg;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.model.Msg;
import com.model.User;
import com.service.ChatService;
import com.service.MsgService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@ServerEndpoint("/chat")
public class ChatServlet {
    private String id;
    private static ConcurrentMap<String,ChatServlet>users = new ConcurrentHashMap<>();
    private Session session;
    private ChatService chatService = new ChatService();
    private MsgService msgService = new MsgService();

    /**
     * 发送信息
     * @param type
     * @param data
     */
    private void sendMessage(int type, Object data){
        Map map = new HashMap();
        map.put("type", type); // 0 - 登录， -1 - 离开， 1 - 消息
        map.put("data",data);
        JSONObject json = JSONObject.fromObject(map);
        this.session.getAsyncRemote().sendText(String.valueOf(json));
    }

    /**
     * 用户登录
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        Map<String,List<String>>map = session.getRequestParameterMap();
//        取得客户端传来的参数
        id = map.get("id").get(0);
        System.out.println("用户id："+id);
        users.put(id, this);
//        获取用户列表对应的键值
        List<String>list =new ArrayList<>( users.keySet());
        Set<String>key = users.keySet();
//        发送登录信息给所有用户
        for(String k: key){
            users.get(k).sendMessage(0,JSONArray.fromObject(list));
        }
    }

    /**
     * 用户退出
     */
    @OnClose
    public void onClose(){
//        移除用户信息
        users.remove(id);
        Set<String>key = users.keySet();
//        发送离线信息给所有用户
        List<String>list =new ArrayList<>( users.keySet());
        try{
            for(String k: key){
                if(k != id){
                    users.get(k).sendMessage(-1,JSONArray.fromObject(list));
                }
            }
        }catch(Exception e){
            System.out.println("发送失败");
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try{
            System.out.println("来自客户端的消息:" + message);

            JSONObject json = JSONObject.fromObject(message);
            Msg msg = (Msg)JSONObject.toBean(json,Msg.class);
            msg.setTime(); // 设置时间
//           转换为要发送的正确格式
            msg = chatService.getMsg(msg);
            System.out.println(msg);
//            添加进数据库
            msgService.addMsg(msg.getSend_id(),msg.getAccept_id(),msg.getContent(),msg.getTarget());

//        私聊
            if(msg.getTarget() == 0){
                System.out.println("私聊");
//                发送给自己
                users.get(Integer.toString(msg.getSend_id())).sendMessage(1,msg);
                ChatServlet toServlet = users.get(Integer.toString(msg.getAccept_id()));
                if(null != toServlet){
//                    发送给私聊对象
                    toServlet.sendMessage(1,msg);
                }
            }
//        群聊
            else {
//                获取每个用户id
                List<User> members = chatService.getGroupMember(msg.getAccept_id());
                for(int i = 0; i < members.size(); i++){
//                    给所有成员发信息
                    ChatServlet toServlet = users.get(Integer.toString(members.get(i).getId()));
                    if(null != toServlet){
                        toServlet.sendMessage(1,msg);
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            this.sendMessage(1,"服务器错误");
        }


//        Set<String>key = users.keySet();
//        for(String k: key){
//            if(k.equals(msg.getSend_id()))continue;
//            users.get(k).sendMessage(message);
//        }
    }

    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }
}