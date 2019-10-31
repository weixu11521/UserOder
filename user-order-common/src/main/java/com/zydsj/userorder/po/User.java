package com.zydsj.userorder.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private Integer id;
    private String userName;
    private String passWord;
    private Double score;


}
