package com.example.demo.bo;

import lombok.Data;

@Data
public class AddMenuBO {

    private Long menuId;

    private String name;

    private String path;

    private String icon;

    private Long pid;

    private String pagePath;
}
