package com.yhl.baseOrm.constant;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    //当前页码
    private int pageNum;
    // 页面大小
    private int pageSize;
    // 开始行
    private int startRow;
    // 结束行
    private int endRow;
    //总行数
    private long total;
    // 总页码数
    private int pages;
    //根据排序字段
    private String orderBy;
    //实体队列
    private List<T> list;



    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
