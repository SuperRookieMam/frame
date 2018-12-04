package demo.constpackage;


import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "查询数据库参数")
public class Params implements Serializable {

    private static final long serialVersionUID = 8057709322192900635L;

    @ApiModelProperty(value = "当前页", dataType = "Integer", required = true)
    private  Integer pageNum;

    @ApiModelProperty(value = "分页大小", dataType = "Integer", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "排序", dataType = "JSONArray")
    private JSONArray sort; //排序对象{'name': 'desc'}

    @ApiModelProperty(value = "模糊", dataType = "JSONArray")
    private JSONArray like; //模糊

    @ApiModelProperty(value = "模糊反向", dataType = "JSONArray")
    private JSONArray notLike; //模糊

    @ApiModelProperty(value = "小于", dataType = "JSONArray")
    private JSONArray lt; //小于


    @ApiModelProperty(value = "大于", dataType = "JSONArray")
    private JSONArray gt; //大于

    @ApiModelProperty(value = "小于等于", dataType = "JSONArray")
    private JSONArray le; //小于等于

    @ApiModelProperty(value = "大于等于", dataType = "JSONArray")
    private JSONArray ge; //大于等于

    @ApiModelProperty(value = "等于", dataType = "JSONArray")
    private JSONArray eq; //等于


    @ApiModelProperty(value = "不等于", dataType = "JSONArray")
    private JSONArray notEq; //等于


    @ApiModelProperty(value = "in条件", dataType = "JSONArray")
    private JSONArray in; //等于


    @ApiModelProperty(value = "not in条件", dataType = "JSONArray")
    private JSONArray notIn; //等于

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
                +"\"notIn\":" + notIn +
                '}';
            }
}
