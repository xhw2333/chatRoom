package com.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseServlet extends HttpServlet {

    /**
     * 将json字符串转换成需要的数据
     * @param request
     * @return
     * @throws IOException
     */
    protected JsonNode thisInit(HttpServletRequest request) throws IOException {
        //创建缓冲字符输入流和json
        BufferedReader reader;
        String json;
        //实例化objectMapper对象
        ObjectMapper mapper = new ObjectMapper();
        //如果json中有新增的字段并且是实体类类中不存在的，不报错（json反序列化）
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        reader = new BufferedReader(request.getReader());
        json = reader.readLine();
        reader.close();
        //返回jsonNode对象
        return mapper.readTree(json);
    }

    /**
     * 转换为json字符串
     * @param status
     * @param code
     * @param data
     * @return
     * @throws IOException
     */
    protected JSONObject stringify(boolean status,int code,Object data) throws IOException {
        Map map = new HashMap();
        map.put("status", status);
        map.put("code", code);
        map.put("data",data);
        JSONObject json = JSONObject.fromObject(map);
        return json;
    }
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //设置编码格式
//        req.setCharacterEncoding("utf-8");
//        //获取请求路径URI
//        String uri = req.getRequestURI();
//        //获取方法名
//        String requestName = uri.substring(uri.lastIndexOf('/') + 1);
//        try {
//            //反射获取方法对象并调用
//            //获取当前的类，返回方法
//            Method method = this.getClass().getMethod(requestName, HttpServletRequest.class, HttpServletResponse.class);
//            //执行方法，调用包装在当前方法中的方法对象
//            Object invokeResponse = method.invoke(this, req, resp);
//            //设置响应编码
//            resp.setContentType("application/json;charset=utf-8");
//            //SKIP为跳过参数
//            if(!"SKIP".equals(invokeResponse)){
//                //返回数据，并转为json字符串
//                new ObjectMapper().writeValue(resp.getOutputStream(), invokeResponse);
//                resp.getOutputStream().flush();
//            }
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
}
