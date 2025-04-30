package com.erp.common;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class OperateYml {

    private static final String variableFilePath = "src/main/resources/environment_variable.yml";

    // 编辑 yaml 文件
    public static void writerYaml(Map<String, String> ymlData, String yaml_path){
        // 设置YAML的输出格式
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        // 写入YAML文件
        try (FileWriter writer = new FileWriter(yaml_path)) {
            yaml.dump(ymlData, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 读取 yaml 文件
    public static JSONObject readYaml(String yaml_path){
        Yaml yaml = new Yaml();
        JSONObject jsonYaml = null;
        try (FileInputStream fis = new FileInputStream(yaml_path)) {
            Map<String, Object>  data = yaml.load(fis);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonYaml = JSONObject.parseObject(objectMapper.writeValueAsString(data));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonYaml;
    }

    // 获取请求地址所有路由 json
    public static void readAddress(){
        httpDto.setJsonAddress(readYaml("src/main/resources/environment_address.yml"));
    }

    // 获取登录用户
    public static String readUser(String user) {
        return readYaml("src/main/resources/login_user.yml").getString(user);
    }

    // 获取请求 host
    public static String readHost(){
        return httpDto.getJsonAddress().getString("host");
    }

    // 获取登录 api
    public static String readLoginApi() {
        return JSONObject.parseObject(httpDto.getJsonAddress().getString("api")).getString("login");
    }

    // 获取模块api 的 json
    public static JSONObject readModuleJson(String module){
        return JSONObject.parseObject(JSONObject.parseObject(httpDto.getJsonAddress().getString("api")).getString(module));
    }

    // 文件写入token值
    public static void writerLoginToken(String token){
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", token);
        writerYaml(map,"src/main/resources/login_token.yml");
    }

    // 写入环境变量
    public static void writerEnvironmentVariable(String key, String value){
        Yaml yaml = new Yaml();
        try (FileInputStream fis = new FileInputStream(variableFilePath)) {
            Map<String, String> variableMap = yaml.load(fis);
            if(variableMap.containsKey(key)){
                variableMap.replace(key, value);
                System.out.println("replace: " + key + "= " + value);
            }else {
                variableMap.put(key, value);
                System.out.println("add: " + key + "= " + value);
            }
            writerYaml(variableMap,variableFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 读取环境变量值
    public static String readEnvironmentVariable(String variableName){
        return readYaml(variableFilePath).getString(variableName);
    }

    // 检查文件不存在并创建
    public static void checkFileIsExists(String filePath){
        try{
            Path path = Paths.get(filePath);
            if(Files.exists(path)){
                System.out.println("File mkdir");
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
