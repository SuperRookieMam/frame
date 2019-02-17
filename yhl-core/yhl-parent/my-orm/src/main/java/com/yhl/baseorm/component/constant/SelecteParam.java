package com.yhl.baseorm.component.constant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.weaver.ast.And;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelecteParam  implements Serializable {
    private static final long serialVersionUID = 6326630913095192025L;

    //value = "当前页", dataType = "Integer", required = true
    private  Integer pageNum;

    //value = "分页大  小", dataType = "Integer", required = true
    private Integer pageSize;

    //因为排序时有序的所以用arry来接收
    private JSONArray sort; //排序对象[{'order': 'desc',name:'字段名'}]
    /*and 可以添加条件
    * {
    *  like:{field.field：value}
    *  notLike:{}
    *  lt：{} 小于
    *  le:{}小于等于
    *  gt:{}大于
    *  ge:{}大于等于
    *  eq:{}等于
    *  notEq:{}不等于
    *  in:{"字段名":[]}
    *  notIn:[]
    *  null:{}
    *  not null:{}
    *  }
    * */
    private  JSONObject and;

    private JSONObject or;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public JSONArray getSort() {
        return sort;
    }

    public void setSort(JSONArray sort) {
        this.sort = sort;
    }

    public JSONObject getOr() {
        return or;
    }

    public void setOr(JSONObject or) {
        this.or = or;
    }

    public JSONObject getAnd() {
        return and;
    }

    public void setAnd(JSONObject and) {
        this.and = and;
    }

    public Sort getToSort(){
       if (this.sort==null||this.sort.isEmpty()){
           return null;
       }else {
           List<Sort.Order> list =new ArrayList<>();
           for (int i = 0; i < this.sort.size(); i++) {
               JSONObject jsonObject =this.sort.getJSONObject(i);
               Sort.Direction direction=  "desc".equalsIgnoreCase(jsonObject.getString("order"))
                                            ?Sort.Direction.DESC
                                            :Sort.Direction.ASC;
                list.add(new Sort.Order(direction,jsonObject.getString("name")));
           }
            return   new Sort(list);
       }
    }
    public String toString() {
        return "Params:{"
                +"\"pageNum\":"+ pageNum +","
                +"\"pageSize\":" + pageSize +","
                +"\"sort\":" + sort +","
                +"\"and\":" + and +","
                +"\"or\":" + or +","
                +'}';
    }

}
