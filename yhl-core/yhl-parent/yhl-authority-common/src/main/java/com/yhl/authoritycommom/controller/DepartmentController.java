package com.yhl.authoritycommom.controller;

import com.yhl.authoritycommom.entity.Department;
import com.yhl.base.baseController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("departments")
public class DepartmentController extends BaseController<Department,String> {
}
