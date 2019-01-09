package com.test.controller;

import com.test.entity.MyTestEntity1;
import com.yhl.base.baseController.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("myTestEntity1s")
@Api(value = "MyTestEntity1一个用来测试swagger注解的控制器")
public class MyTestEntity1Controller extends BaseController<MyTestEntity1,String> {
}
