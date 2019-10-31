package com.zydsj.userorder.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private Integer id;
    private String address;
    private Date createTime;
    private String receiveName;
    private String phone;
    private Integer user_id;
    private Double totalPrice;
    private Integer status;
    private String description;


}
