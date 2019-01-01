package com.test.controller;

import com.test.entity.MyTestEntity1;
import com.test.entity.MyTestEntity2;
import com.yhl.base.baseController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("myTestEntity2s")
public class MyTestEntity2Controller extends BaseController<MyTestEntity2,String> {
}
