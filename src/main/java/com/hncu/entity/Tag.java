package com.hncu.entity;

import com.hncu.utils.Entity;
import lombok.Data;

@Data
public class Tag extends Entity {
    private Integer id;
    private String tagName;
    private Integer count;
}
