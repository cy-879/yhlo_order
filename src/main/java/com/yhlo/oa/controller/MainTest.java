package com.yhlo.oa.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cy
 * @ClassName: MainTest  
 * @Description: 测试CRM接口调用
 * @date 2022/6/299:00
 */
@Slf4j
public class MainTest {

    private static String url1 = "https://open.fxiaoke.com/cgi/corpAccessToken/get/V2";
    private static String url2 = "https://open.fxiaoke.com/cgi/user/getByMobile";


   /* public static void main(String[] args) {
       Map map= getPersonByPhoneNumber();
       String a = (String) map.get("body");
       JSONObject jsonObject = JSONObject.parseObject(a);
       System.err.println("jsonObject=="+jsonObject);
    }*/


    /**
     * @Author cy
     * @Description  获取企业应用授权
     * * @param null
     * @Return
     * @Date 2022/6/29 9:16
     */
    public static Map getQiyyysq (){
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("appId", "FSAID_131ae19");
      jsonObject.put("appSecret", "817396c8c21c47be876da78dd5eb6652");
      jsonObject.put("permanentCode", "43B0BAD52FC2F2349F34ADFEB85ED936");
      Map<String, Object> map = new HashMap();
      try
      {
          Map<String, Object> mapbodyone = send(url1, jsonObject, "utf-8");
          String body = (String)mapbodyone.get("body");
          Integer status = (Integer)mapbodyone.get("status");
          if (status.intValue() != 200)
          {
              map.put("status", status);
              map.put("body", body);
              return map;
          }
          System.err.println("取企业应用授权：-->" + body.toString());
          map.put("status", "200");
          map.put("body", body);
          return map;
      }
      catch (Exception e)
      {
          log.info("取企业应用授权：-->" + e.toString());
          map.put("status", Integer.valueOf(400));
          map.put("body", "调用对方接口异常，请重试几次!! 或者检查对方接口数据是否有变更导致接口异常！！" + e.toString());
      }
      return map;
    }




    /**
     * @Author cy
     * @Description  根据手机号查询员工
     * * @param null
     * @Return
     * @Date 2022/6/29 9:20
     */
    public static Map getPersonByPhoneNumber(){

        Map sq = getQiyyysq();
        String bd = (String) sq.get("body");
        String st = (String) sq.get("status");
        if(!"200".equals(st)){
            log.error("获取授权失败！！！");
            return null;
        }

        JSONObject json = JSONObject.parseObject(bd);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("corpAccessToken", json.get("corpAccessToken"));
        jsonObject.put("corpId", json.get("corpId"));
        jsonObject.put("mobile", "13900000000");
        Map<String, Object> map = new HashMap();
        try
        {
            Map<String, Object> mapbodyone = send(url2, jsonObject, "utf-8");
            String body = (String)mapbodyone.get("body");
            Integer status = (Integer)mapbodyone.get("status");
            if (status.intValue() != 200)
            {
                map.put("status", status);
                map.put("body", body);
                return map;
            }
            System.err.println("据手机号查询员工：-->" + body.toString());
            map.put("status", "200");
            map.put("body", body);
            return map;
        }
        catch (Exception e)
        {
            log.info("据手机号查询员工：-->" + e.toString());
            map.put("status", Integer.valueOf(400));
            map.put("body", "调用对方接口异常，请重试几次!! 或者检查对方接口数据是否有变更导致接口异常！！" + e.toString());
        }
        return map;
    }

    public static Map send(String url, JSONObject jsonObject, String encoding)
    {
        try
        {
            Map<String, Object> map = new HashMap();
            String body = "";

            CloseableHttpClient client = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(url);

            StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader("Content-Type", "application/json"));

            httpPost.setEntity(s);



            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");



            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
            httpPost.setConfig(requestConfig);

            CloseableHttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int responsecode = statusLine.getStatusCode();


            HttpEntity entity = response.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);

            response.close();
            if (responsecode != 200)
            {
                map.put("status", Integer.valueOf(responsecode));
                map.put("body", body);
                return map;
            }
            map.put("status", Integer.valueOf(responsecode));
            map.put("body", body);
            return map;
        }
        catch (Exception e)
        {
            log.info("POST调用异常：：send--->" + e.toString());
            Map<String, Object> maps = new HashMap();
            maps.put("status", Integer.valueOf(400));
            maps.put("body", "未知错误或者方法异常，请查看日志： " + e.toString());
            return maps;
        }
    }

}
