package com.test.controller;

import com.test.entity.MyTestEntity2;
import com.yhl.base.baseController.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("myTestEntity2s")
@Api(value = "MyTestEntity2一个用来测试swagger注解的控制器")
public class MyTestEntity2Controller extends BaseController<MyTestEntity2,String> {
}
