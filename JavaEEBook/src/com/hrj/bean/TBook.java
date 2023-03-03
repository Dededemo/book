package com.hrj.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBook {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String author;
    private Integer sales;
    private Integer stock;
    private String imgpath = "static/img/default.jpg";
}
