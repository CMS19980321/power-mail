package com.hncu.controller;

import com.hncu.service.ProdPropService;
import io.swagger.annotations.Api;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author caimeisahng
 * @Date 2026/3/29 21:00
 * @Version 1.0
 * 商品规格管理控制层
 */

@Api(tags = "商品规格管理控制层")
@RestController
@RequestMapping("prod/spec/page")
public class ProdSpecController {
    @Autowired
    private ProdPropService prodPropService;
}
