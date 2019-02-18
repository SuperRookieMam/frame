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
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public ResultDto findById(@PathVariable("id") ID id){
        return baseService.findById(id);
    }
    /**
     * 根据参数自定义查询
     * */
    @GetMapping(params = {"list"})
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public ResultDto findByParams(@RequestBody WhereCondition whereCondition){
        return  baseService.findByParams(whereCondition);
    }

    /**
     * 分页查询
     * */
    @GetMapping(params ={"page"})
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public ResultDto findPageByParams(@RequestBody WhereCondition whereCondition){
        return  baseService.findPageByParams(whereCondition);
    }


    @PostMapping(params = {"insertEntity"})
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public<T> ResultDto insertByEntity(@RequestBody T entity){
        return  baseService.insertByEntity(entity);
    }

   /**
     * 批量插入
     * */
    @PostMapping(params = {"insertList"})
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public <T> ResultDto insertByList(@RequestBody List<T> entitys){
        return  baseService.insertByList(entitys);
    }

    /**
     * 根据实体跟新
     * */
    @PutMapping(params = {"update"})
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public<T> ResultDto updateByUpdateFields(@RequestBody UpdateFields updateFields){
        return  baseService.updateByUpdateFields(updateFields);
    }

    /**
     * 根据实体跟新
     * */
    @PutMapping(params = {"updateList"})
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public<T> ResultDto updateByUpdateParams(@RequestBody UpdateFields[] updateFieldss){
        return  baseService.updateByUpdateFields(updateFieldss,1000);
    }
    /**
     * 根据实体跟新
     * */
    @PostMapping(params = {"updateWhere"})
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public<T> ResultDto updateByFree(@RequestBody UpdateCondition updateCondition){
        return  baseService.updateByWhereCondition(updateCondition.getUpdateFields(),updateCondition.getWhereCondition(),1000);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public<T> ResultDto deleteByEntity(@PathVariable  ID id){

      return   baseService.deleteById(id);
    }
    @DeleteMapping(params = {"deleteWhere"})
    @ResponseBody
    @ApiOperation(value="根据Id查询实体", notes="getEntityById")
    public ResultDto deleteByParams(@RequestBody WhereCondition whereCondition){
        return  baseService.deleteByWhereCondition(whereCondition);
    }

}
