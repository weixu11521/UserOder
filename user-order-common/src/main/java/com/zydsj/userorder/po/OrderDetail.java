package com.zydsj.userorder.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {

    private Integer id;
    private Integer order_id;
    private Integer product_id;
    private Integer price;
    private Integer number;
}
