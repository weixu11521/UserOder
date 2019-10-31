package com.zydsj.userorder.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Product implements Serializable {
    private Integer id;
    private Integer productName;
    private Integer description;
    private Integer price;
    private Integer count;

}
