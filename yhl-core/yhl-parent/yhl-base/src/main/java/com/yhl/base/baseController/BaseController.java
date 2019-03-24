package com.yhl.base.baseController;

import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.base.baseService.BaseService;
import com.yhl.base.component.dto.ResultDto;
import com.yhl.baseorm.component.constant.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public class BaseController<T extends BaseEntity<ID>,ID extends Serializable>{

    @Autowired
    BaseService<T,ID> baseService;

    @GetMapping("{id}")
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="findById")
    public ResultDto findById(@PathVariable("id") ID id){
        return baseService.findById(id);
    }
    /**
     * 根据参数自定义查询
     * */
    @GetMapping(params = {"list"})
    @ResponseBody
    @ApiOperation(value="根据条件查询列表", notes="findByParams")
    public ResultDto findByParams(@RequestBody WhereCondition whereCondition){
        return  baseService.findByParams(whereCondition);
    }

    /**
     * 分页查询
     * */
    @GetMapping(params ={"page"})
    @ResponseBody
    @ApiOperation(value="根据条件分页查询", notes="findPageByParams")
    public ResultDto findPageByParams(@RequestBody WhereCondition whereCondition){
        return  baseService.findPageByParams(whereCondition);
    }


    @PostMapping
    @ResponseBody
    @ApiOperation(value="根据实体插入", notes="insertByEntity")
    public<T> ResultDto insertByEntity(@RequestBody T entity){
        return  baseService.insertByEntity(entity);
    }

   /**
     * 批量插入
     * */
    @PostMapping("list")
    @ResponseBody
    @ApiOperation(value="根据list插入", notes="getEntityById")
    public <T> ResultDto insertByList(@RequestBody List<T> entitys){
        return  baseService.insertByList(entitys);
    }


    /**
     * 根据实体跟新
     * */
    @PutMapping
    @ResponseBody
    @ApiOperation(value="完全根据传入实体字段跟新", notes="updateByUpdateFields")
    public<T> ResultDto updateByEntity(@RequestBody T entity){
        return  baseService.updateByEntity(entity);
    }

    /**
     * 根据实体跟新
     * */
    @PutMapping
    @ResponseBody
    @ApiOperation(value="根据传入的实体数组跟新", notes="updateByEntitys")
    public<T> ResultDto updateByEntitys(@RequestBody T[] entitys){
        return  baseService.updateByEntitys(entitys);
    }
    /**
     * 根据实体跟新
     * */
    @PutMapping("field")
    @ResponseBody
    @ApiOperation(value="根据传入的跟新字段条件，跟新单个实体", notes="updateByUpdateFields")
    public<T> ResultDto updateByUpdateFields(@RequestBody UpdateFields updateFields){
        return  baseService.updateByUpdateFields(updateFields);
    }

    /**
     * 根据实体跟新
     * */
    @PutMapping("fields")
    @ResponseBody
    @ApiOperation(value="根据传入的跟新字段条件,数组跟新", notes="updateByUpdateParams")
    public<T> ResultDto updateByUpdateParams(@RequestBody UpdateFields[] updateFieldss){
        return  baseService.updateByUpdateFields(updateFieldss,1000);
    }
    /**
     * 根据实体跟新
     * */
    @PutMapping("free")
    @ResponseBody
    @ApiOperation(value="根据传入的field条件,和where条件批量跟新", notes="updateByFree")
    public<T> ResultDto updateByFree(@RequestBody UpdateCondition updateCondition){
        return  baseService.updateByWhereCondition(updateCondition.getUpdateFields(),updateCondition.getWhereCondition(),1000);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    @ApiOperation(value="根据Id删除实体", notes="deleteByEntity")
    public<T> ResultDto deleteByEntity(@PathVariable  ID id){

      return   baseService.deleteById(id);
    }
    @DeleteMapping("where")
    @ResponseBody
    @ApiOperation(value="根据Where条件批量删除实体", notes="deleteByParams")
    public ResultDto deleteByParams(@RequestBody WhereCondition whereCondition){
        return  baseService.deleteByWhereCondition(whereCondition);
    }

}
