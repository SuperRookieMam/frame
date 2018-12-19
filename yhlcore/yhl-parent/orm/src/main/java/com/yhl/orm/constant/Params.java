package com.yhl.orm.constant;


import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * {
 * leftJoin:[
 *           {field:value,
 *            with:[{condition:field.字段名 +条件},...]
 *             },...],
 * rightJoin:[
 *           {field:value,
 *            with:[{condition:field.字段名 +条件},...]
 *             },...],
 * innerJoin:[
 *           {field:value,
 *            with:[{condition:field.字段名 +条件},...]
 *             },...],
 * like:[{field:value,condition:value},...],
 * sort：[{field: value+desc}]
 * .
 * .
 * }
 * */
public class Params implements Serializable {

    private static final long serialVersionUID = 8057709322192900635L;

    //value = "当前页", dataType = "Integer", required = true
    private  Integer pageNum;

    //value = "分页大  小", dataType = "Integer", required = true
    private Integer pageSize;

    //value = "排序", dataType = "JSONArray"
    private JSONArray sort; //排序对象{'name': 'desc'}

    //value = "模糊", dataType = "JSONArray"
    private JSONArray like; //模糊

    //value = "模糊反向", dataType = "JSONArray"
    private JSONArray notLike; //模糊

    //value = "小于", dataType = "JSONArray"
    private JSONArray lt; //小于


    //value = "大于", dataType = "JSONArray"
    private JSONArray gt; //大于

    //value = "小于等于", dataType = "JSONArray"
    private JSONArray le; //小于等于

    //value = "大于等于", dataType = "JSONArray"
    private JSONArray ge; //大于等于

    //value = "等于", dataType = "JSONArray"
    private JSONArray eq; //等于


    //value = "不等于", dataType = "JSONArray"
    private JSONArray notEq; //等于


    //value = "in条件", dataType = "JSONArray"
    private JSONArray in; //等于


    //value = "not in条件", dataType = "JSONArray"
    private JSONArray notIn; //等于

    //value = "左链接", dataType = "JSONArray"
    private JSONArray leftJoin; //等于

    //value = "左链接", dataType = "JSONArray"
    private JSONArray rightJoin; //等于
    //value = "左链接", dataType = "JSONArray"
    private JSONArray innerJoin;

    private JSONArray or;

    private JSONArray groupby;

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

    public JSONArray getLike() {
        return like;
    }

    public void setLike(JSONArray like) {
        this.like = like;
    }

    public JSONArray getNotLike() {
        return notLike;
    }

    public void setNotLike(JSONArray notLike) {
        this.notLike = notLike;
    }

    public JSONArray getLt() {
        return lt;
    }

    public void setLt(JSONArray lt) {
        this.lt = lt;
    }

    public JSONArray getGt() {
        return gt;
    }

    public void setGt(JSONArray gt) {
        this.gt = gt;
    }

    public JSONArray getLe() {
        return le;
    }

    public void setLe(JSONArray le) {
        this.le = le;
    }

    public JSONArray getGe() {
        return ge;
    }

    public void setGe(JSONArray ge) {
        this.ge = ge;
    }

    public JSONArray getEq() {
        return eq;
    }

    public void setEq(JSONArray eq) {
        this.eq = eq;
    }

    public JSONArray getNotEq() {
        return notEq;
    }

    public void setNotEq(JSONArray notEq) {
        this.notEq = notEq;
    }

    public JSONArray getIn() {
        return in;
    }

    public void setIn(JSONArray in) {
        this.in = in;
    }

    public JSONArray getNotIn() {
        return notIn;
    }

    public void setNotIn(JSONArray notIn) {
        this.notIn = notIn;
    }

    public JSONArray getGroupby() {
        return groupby;
    }

    public void setGroupby(JSONArray groupby) {
        this.groupby = groupby;
    }

    public JSONArray getOr() {
        return or;
    }

    public void setOr(JSONArray or) {
        this.or = or;
    }

    public JSONArray getLeftJoin() {
        return leftJoin;
    }

    public void setLeftJoin(JSONArray leftJoin) {
        this.leftJoin = leftJoin;
    }

    public JSONArray getRightJoin() {
        return rightJoin;
    }

    public void setRightJoin(JSONArray rightJoin) {
        this.rightJoin = rightJoin;
    }

    public JSONArray getInnerJoin() {
        return innerJoin;
    }

    public void setInnerJoin(JSONArray innerJoin) {
        this.innerJoin = innerJoin;
    }

    public String toString() {
        return "Params:{"
                +"\"pageNum\":"+ pageNum +","
                +"\"pageSize\":" + pageSize +","
                +"\"sort\":" + sort +","
                +"\"like\":" + like +","
                +"\"notLike\":" + notLike +","
                +"\"lt\":" + lt +","
                +"\"gt\":" + gt +","
                +"\"le\":" + le +","
                +"\"ge\":" + ge +","
                +"\"eq\":" + eq +","
                +"\"notEq\":" + notEq +","
                +"\"in\":" + in +","
                +"\"or\":" + or +","
                +"\"leftJoin\":" + leftJoin +","
                +"\"rightJoin\":" + rightJoin +","
                +"\"innerJoin\":" + innerJoin +","
                +"\"notIn\":" + notIn +
                '}';
            }
}
