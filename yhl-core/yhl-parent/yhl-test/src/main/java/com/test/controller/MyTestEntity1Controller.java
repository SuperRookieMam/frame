package com.test.controller;

import com.test.entity.MyTestEntity1;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("myTestEntity1s")
public class MyTestEntity1Controller extends BaseController<MyTestEntity1,String> {
}
