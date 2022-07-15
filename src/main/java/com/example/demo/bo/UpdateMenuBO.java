package com.example.demo.bo;

import lombok.Data;

@Data
public class UpdateMenuBO {

    private Long roleId;

    private String name;

    private String description;

    private Long[] menuIds;

}
