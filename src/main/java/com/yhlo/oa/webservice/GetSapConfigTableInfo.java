package com.yhlo.oa.webservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sun.misc.BASE64Encoder;

import javax.xml.rpc.ServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cy
 * @ClassName: 获取SAP系统配置信息
 * @Description:
 * @date 2022/5/1819:47
 */
@Slf4j
public class GetSapConfigTableInfo {


   /* public static void main(String[] args) throws ServiceException, IOException {
        //请求地址
        String url = "http://sapdev.szyhlo.com:8000/sap/bc/srt/rfc/sap/zws_oms2erp/500/zws_oms2erp/zws_oms2erp";

        String username = "DDS"; //账号
        String password = "Yhlo123456"; //密码
        String Authorization = username + ":" + password;

        List<String> paramList = new ArrayList<>();
        paramList.add("T001");
        paramList.add("TVKO");
        paramList.add("TVKOT");
        paramList.add("TVKWZ");
        paramList.add("TVKBT");
        paramList.add("TVGRT");
        paramList.add("TVKBZ");
        paramList.add("TVBVK");
        paramList.add("TVSTT");
        paramList.add("TVTWT");
        paramList.add("TSPAT");
        paramList.add("TVZBT");
        paramList.add("T042ZT");
        paramList.add("T001W");
        paramList.add("T001L");
        paramList.add("T024E");
        paramList.add("V_024");
        paramList.add("T134T");
        paramList.add("TWEWT");
        paramList.add("T023T");
        paramList.add("T179");
        paramList.add("T005U");
        paramList.add("T077X");
        paramList.add("T171T");
        paramList.add("T189T");
        paramList.add("CSKT");
        paramList.add("T005T");
        paramList.add("TVSBT");
        paramList.add("TVAUT");
        paramList.add("TVAGT");

        System.err.println(paramList);

        String param = "TVKWZ";

        String requestBody = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"  xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\">\n" +
                "   <soap:Header/>\n" +
                "   <soap:Body>\n" +
                "   <urn:ZWS_DD2SAP>\n" +
                "       <IC_INTNR>SD135</IC_INTNR>\n" +
                "           <JSONSTR1><![CDATA[[{\"ZSAP_TABLE\":\""+param+"\"}]]]></JSONSTR1>\n"+
                "   </urn:ZWS_DD2SAP>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";
        System.out.println("requestBody\n" + requestBody);
        String responseBody = doPost(url, requestBody, Authorization);
        //System.err.println("responseBody=="+responseBody);

        Document doc = null;
        try {
            doc = DocumentHelper.parseText(responseBody.trim());
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element root = doc.getRootElement();// 指向根节点
        String message = root.element("Body").element("ZWS_DD2SAPResponse").elementTextTrim("OUT1").trim();
        JSONArray objects = JSON.parseArray(message);
        System.out.println("\n反馈报文:" + objects);
        if(objects.size()>0){
            for(int i=0;i<objects.size();i++){
                JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                System.out.println(job.get("ZSD_02")) ;   // 得到 每个对象中的属性值
            }
        }
        System.err.println(sendRequest("TVKWZ"));
    }*/

    public static String sendRequest(String IC_INTNR,String JSONSTR1){


        String url = "http://sapdev.szyhlo.com:8000/sap/bc/srt/rfc/sap/zws_oms2erp/500/zws_oms2erp/zws_oms2erp";

        String username = "DDS"; //账号
        String password = "Yhlo123456"; //密码
        String Authorization = username + ":" + password;
        String requestBody = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"  xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\">\n" +
                "   <soap:Header/>\n" +
                "   <soap:Body>\n" +
                "   <urn:ZWS_DD2SAP>\n" +
                "       <IC_INTNR>"+IC_INTNR+"</IC_INTNR>\n" +
                "           <JSONSTR1>"+JSONSTR1+"</JSONSTR1>\n"+
                "   </urn:ZWS_DD2SAP>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";
        log.info("requestBody\n" + requestBody);
        String responseBody = doPost(url, requestBody, Authorization);
        //System.err.println("responseBody=="+responseBody);
        String message = "";
        if(responseBody.contains("error")){
            return message;
        }

        Document doc = null;
        try {
            doc = DocumentHelper.parseText(responseBody.trim());
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element root = doc.getRootElement();// 指向根节点
        message = root.element("Body").element("ZWS_DD2SAPResponse").elementTextTrim("OUT1").trim();

        return message;
    }

    /**
     * Post请求一个地址
     *
     * @param URL         请求地址
     * @param requestBody 请求的body
     * @param Authorization 账号密码
     * @return 返回调用结果
     */
    public static String doPost(String URL, String requestBody,String Authorization) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;

        try {
            java.net.URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            BASE64Encoder base = new BASE64Encoder();
            String encodedPassword = base.encode(Authorization.getBytes("UTF-8"));
           // System.out.println("加密后的密码：" + encodedPassword);
            //将加密的账号密码放到请求头里，这里注意Basic后面要加空格
            conn.setRequestProperty("Authorization", "Basic " + encodedPassword);
            conn.setRequestMethod("POST");
            //发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
            conn.setRequestProperty("Accept", "application/soap+xml");
            //获取输出流，写入请求的json或者xml报文
            out = new OutputStreamWriter(conn.getOutputStream(),"utf-8");

            out.write(requestBody); //获取请求的body,
            out.flush();
            out.close();
            //取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                    //System.out.println(line);
                }
            } else {
                result.append("error");
                log.info("ResponseCode is an error code:" + conn.getResponseCode());
                log.info("ResponseMessage is :" + conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return result.toString();
    }


    public static String call(String url, String requestBody,String Authorization)
            throws Exception {
        //记录接口調用結果字符串
        String rev = "";
        // 获得Http客户端
        HttpClient httpclient = HttpClients.createDefault();
        // 响应模型
        HttpResponse response = null;


        try {
            // 创建Post请求
            HttpPost httppost = new HttpPost(url);
            // 设置Content-Type
            httppost.addHeader("Content-Type","application/soap+xml; charset=utf-8");
            //httppost.addHeader("SOAPAction", targetNamespace);
            BASE64Encoder base = new BASE64Encoder();
            String encodedPassword = base.encode(Authorization.getBytes("UTF-8"));
            System.out.println("加密后的密码：" + encodedPassword);
            //将加密的账号密码放到请求头里，这里注意Basic后面要加空格
            httppost.addHeader("Authorization","Basic " + encodedPassword);

            StringEntity data = new StringEntity(requestBody, Charset.forName("UTF-8"));
            httppost.setEntity(data);
            // 由客户端执行请求
            response = httpclient.execute(httppost);
            // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {	//调用接口成功
                // 获取到返回结果，注意返回结构也是包装过后的，需要根据实际情况进行拆解
                rev = EntityUtils.toString(response.getEntity());
            } else {	//调用接口失败
                rev = EntityUtils.toString(response.getEntity());
                log.error(rev);
                rev = "接口调用出错！";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
        return rev;
    }

}
