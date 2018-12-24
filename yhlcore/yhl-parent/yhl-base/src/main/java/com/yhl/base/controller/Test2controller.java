package com.yhl.base.controller;

import com.yhl.base.entity.Test2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test2s")
public class Test2controller extends  BaseController<Test2,String> {
}
