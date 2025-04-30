package com.erp.common;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;

import static com.erp.common.OperateYml.*;

@Data
public class HttpClientCommon {

    private static JSONObject responseBody;
    private static String requestUrl;
    private static String authorization;


    // get 请求方法
    public static JSONObject getHttpClient(String url){
        return doHttpClient(url, "", "GET");
    }

    // put 请求方法
    public static void putHttpClient(String url, String paramsJson){
        doHttpClient(url, paramsJson, "PUT");
    }

    // put 请求方法
    public static void putHttpClient(String url){
        doHttpClient(url, "", "PUT");
    }

    // post 请求方法
    public static JSONObject postHttpClient(String url, String params_json){
        return doHttpClient(url, params_json, "POST");
    }

    // 获得请求url，host + api
    public static String getRequestUrl(String moduleName, String apiName){
        requestUrl = readHost() + readModuleJson(moduleName).getString(apiName);
        return requestUrl;
    }

    // 获得请求authorization
    public static void  getAuthorization(){
        authorization = readYaml("src/main/resources/login_token.yml").getString("Authorization");
    }

    // httpclient5 请求基础方法
    public static JSONObject doHttpClient(String url, String params_json, String requestMethod){
        String response = "";
        StringEntity stringEntity;
        try(CloseableHttpClient closeableHttpClient = HttpClients.createDefault()){
            switch (requestMethod) {
                case "PUT":
                    HttpPut httpPut = new HttpPut(url);
                    if(params_json != null){
                        stringEntity = new StringEntity(params_json, ContentType.APPLICATION_JSON);
                        httpPut.setEntity(stringEntity);
                    }
                    httpPut.setHeader("Accept", "application/json");
                    httpPut.setHeader("Content-Type", "application/json");
                    httpPut.setHeader("Authorization", authorization);
                    response = closeableHttpClient.execute(httpPut,new BasicHttpClientResponseHandler());
                    break;
                case "POST":
                    HttpPost httpPost = new HttpPost(url);
                    stringEntity = new StringEntity(params_json, ContentType.APPLICATION_JSON);
                    httpPost.setEntity(stringEntity);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-Type", "application/json");
                    httpPost.setHeader("Authorization", authorization);
                    response = closeableHttpClient.execute(httpPost,new BasicHttpClientResponseHandler());
                    break;
                case "GET":
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.setHeader("Accept", "application/json");
                    httpGet.setHeader("Content-Type", "application/json");
                    httpGet.setHeader("Authorization", authorization);
                    response = closeableHttpClient.execute(httpGet,new BasicHttpClientResponseHandler());
                    break;
            }
            if (!response.isEmpty()) {
                responseBody = JSONObject.parseObject(response);
                String responseStatusCode = responseBody.getString("status");
                System.out.println("requestUrl: " + url);
                System.out.println("responseStatusCode: " + responseStatusCode);
                if (!responseStatusCode.equals("200")) {
                    System.out.println(responseBody);
                }
            }else {
                System.out.println("请求结果返回为空！！！");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseBody;

    }

}
