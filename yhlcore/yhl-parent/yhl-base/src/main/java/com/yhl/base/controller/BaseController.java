package com.yhl.base.controller;

import com.yhl.base.dto.ResultDto;
import com.yhl.base.service.BaseService;
import com.yhl.baseOrm.constant.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public class BaseController<T,ID extends Serializable>{

    @Autowired
    BaseService<T,ID> baseService;
    @GetMapping("/{id}")
    @ResponseBody
    public  ResultDto findById(@PathVariable("id") ID id){
        return baseService.findById(id);
    }
    /**
     * 根据参数自定义查询
     * */
    @GetMapping("/list")
    @ResponseBody
    public ResultDto findByParams(@RequestBody  Params params){
        return  baseService.findByParams(params);
    }

    /**
     * 分页查询
     * */
    @GetMapping("/pageInfo")
    @ResponseBody
    public ResultDto findPageByParams(@RequestBody  Params params){
        return  baseService.findPageByParams(params);
    }
    @PostMapping("/insert")
    @ResponseBody
    public<T> ResultDto insertByEntity(@RequestBody T entity){
        return  baseService.updateByEntity(entity);
    }

    /**
     * 批量插入
     * */
    @PostMapping("/insert/entitys")
    @ResponseBody
    public <T> ResultDto insertByList(@RequestBody T[] entitys){
        return  baseService.insertByList(entitys);
    }

    /**
     * 根据实体跟新
     * */
    @PutMapping("/update")
    @ResponseBody
    public<T> ResultDto updateByEntity(T entity){
        return  baseService.updateByEntity(entity);
    }
    @DeleteMapping("/delete")
    @ResponseBody
    public<T> ResultDto deleteByEntity(@RequestBody  T entity){
        baseService.deleteByEntity(entity);
      return   ResultDto.success(null);
    }
    @DeleteMapping("/delete/ids")
    @ResponseBody
    public ResultDto deleteByIds(ID[] ids){
        return  ResultDto.success(baseService.deleteByIds(ids));
    }

}
