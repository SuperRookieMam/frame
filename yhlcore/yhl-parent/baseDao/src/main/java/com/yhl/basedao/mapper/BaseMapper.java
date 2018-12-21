package com.yhl.basedao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T>   {
   public List<Map<String,Object>> selectBySql(@Param("sqlStr") String sqlStr);
}
