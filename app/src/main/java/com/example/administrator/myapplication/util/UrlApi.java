package com.example.administrator.myapplication.util;

public class UrlApi {
    private String baseUrl = "https://home.yuandingbang.cn/mini";

    //孩子列表
    public String childUrl() {
        return baseUrl + "/child";
    }

    //使用指南
    public String helpUrl() {
        return baseUrl + "/help";
    }

    //使用指南具体内容
    public String helpContentUrl() {
        return helpUrl() + "/content";
    }

    //提交的内容
    public String suggestionUrl() {
        return baseUrl + "/feed-back/add";
    }
}
