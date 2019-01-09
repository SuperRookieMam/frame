package com.yhl.base.baseController;

import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.base.baseService.BaseService;
import com.yhl.base.component.dto.ResultDto;
import com.yhl.baseorm.component.constant.FreeParam;
import com.yhl.baseorm.component.constant.SelecteParam;
import com.yhl.baseorm.component.constant.UpdateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public class BaseController<T extends BaseEntity<ID>,ID extends Serializable>{

    @Autowired
    BaseService<T,ID> baseService;

    @GetMapping("{id}")
    @ResponseBody
    public ResultDto findById(@PathVariable("id") ID id){
        return baseService.findById(id);
    }

    @PostMapping("/insert")
    @ResponseBody
    public<T> ResultDto insertByEntity(@RequestBody T entity){
        return  baseService.insertByEntity(entity);
    }

   /**
     * 批量插入
     * */
    @PostMapping("/insert/entitys")
    @ResponseBody
    public <T> ResultDto insertByList(@RequestBody List<T> entitys){
        return  baseService.insertByList(entitys);
    }

    /**
     * 根据实体跟新
     * */
    @PostMapping("/update")
    @ResponseBody
    public<T> ResultDto updateByUpdateParam(@RequestBody UpdateParam updateParams){
        return  baseService.updateByUpdateParam(updateParams);
    }

    /**
     * 根据实体跟新
     * */
    @PostMapping("/updates")
    @ResponseBody
    public<T> ResultDto updateByUpdateParams(@RequestBody UpdateParam[] updateParams){
        return  baseService.updateByUpdateParams(updateParams,1000);
    }
    /**
     * 根据实体跟新
     * */
    @PostMapping("/freeUpdate")
    @ResponseBody
    public<T> ResultDto updateByFree(@RequestBody FreeParam freeParam){
        return  baseService.updateByselecteParam(freeParam.getUpdateParam(),freeParam.getSelecteParam(),1000);
    }
    /**
     * 根据参数自定义查询
     * */
    @GetMapping("/list")
    @ResponseBody
    public ResultDto findByParams(@RequestBody  SelecteParam selecteParam){
        return  baseService.findByParams(selecteParam);
    }

    /**
     * 分页查询
     * */
    @GetMapping("/pageInfo")
    @ResponseBody
    public ResultDto findPageByParams(@RequestBody SelecteParam selecteParam){
        return  baseService.findPageByParams(selecteParam);
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public<T> ResultDto deleteByEntity(@PathVariable  ID id){

      return   baseService.deleteById(id);
    }
    @PostMapping("/delete/params")
    @ResponseBody
    public ResultDto deleteByParams(@RequestBody SelecteParam selecteParam){
        return  baseService.deleteBySelectParam(selecteParam);
    }

}
