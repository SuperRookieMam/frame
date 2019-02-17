package com.yhl.baseorm.component.constant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class WhereCondition implements Serializable {

    //value = "当前页", dataType = "Integer", required = true
    private  Integer pageNum;

    //value = "分页大  小", dataType = "Integer", required = true
    private Integer pageSize;

    private ConnectCondition and;

    private ConnectCondition or;
    //因为排序时有序的所以用arry来接收
    private JSONArray sort; //排序对象[{'order': 'desc',name:'字段名'}]


    public  ConnectCondition and(){
        and=and==null?new ConnectCondition():and;
        return and;
    }

    public  ConnectCondition or(){
        or=or==null?new ConnectCondition():or;
        return or;
    }

    public  WhereCondition addSort(String key,String sortType){
        sort=sort==null?new JSONArray():sort;
        if ("desc".equalsIgnoreCase(sortType)||"asc".equalsIgnoreCase(sortType)){
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("fieldName",sortType);
            sort.add(jsonObject);
        }
        return this;
    }
}
