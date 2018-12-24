package com.yhl.baseOrm.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhl.baseOrm.constant.Params;
import org.springframework.stereotype.Component;

@Component
public class ParamUtil {

    /**
     * 按照Hql的规则解析Hql
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
     * }
     * */
    public  static  String parseParamToHql(Params params){
          StringBuffer  condition = new StringBuffer();
          //添加左链接
          JSONArray  array =null;
          array =  params.getLeftJoin();
          condition.append(getJoinStr(array,"left"));
          //添加右链接
          array =  params.getRightJoin();
          condition.append(getJoinStr(array,"right"));
          //内链接
          array =  params.getRightJoin();
          condition.append(getJoinStr(array,"inner"));
          //表链接完了 后面应该加 where 条件了
          //like
          array =  params.getLike();
          condition.append(getWhereCondition(array,"like"));
         //notLike
          array =  params.getLike();
          condition.append(getWhereCondition(array,"not like"));
          //lt
          array =  params.getLt();
          condition.append(getWhereCondition(array,"<"));
          //le
          array =  params.getLe();
          condition.append(getWhereCondition(array,"<="));
           //gt
          array =  params.getGt();
          condition.append(getWhereCondition(array,">"));
          //ge
          array =  params.getGe();
          condition.append(getWhereCondition(array,">="));
          //notEq
          array =  params.getNotEq();
          condition.append(getWhereCondition(array,"!="));
          //in
          array =  params.getIn();
          condition.append(getWhereCondition(array,"in"));
          //not in
          array =  params.getNotIn();
          condition.append(getWhereCondition(array,"not in"));
          //or
          array =  params.getOr();
          condition.append(getWhereCondition(array,"or"));
          //所有条件解析完成 分组信息
          array =  params.getGroupby();
          condition.append(getGroupBy(array,"group by"));
          //所有条件解析完成 分组信息
          array =  params.getGroupby();
          condition.append(getGroupBy(array,"order by"));
          //分页查询
          if (params.getPageNum()!=null&&params.getPageSize()!=null){
              condition.append(" limit ");
              condition.append(""+(params.getPageNum()*params.getPageSize()));
              condition.append(""+params.getPageSize());
          }
        return  condition.toString();
    }
    /**
     * 获取链接字符串
     * @param array 参数数组
     * @param type 链接类型 left(outter)、right(outter)、inner(outter)
     * */
    private  static String  getJoinStr(JSONArray  array ,String type){
        if (array!=null){
            StringBuffer  condition = new StringBuffer(" ");
            for (int i = 0; i <array.size() ; i++) {
                JSONObject temp =array.getJSONObject(i);
                // 例如 left outer join n.field as field  with field.name = n.name
                condition.append(" "+type+" join n."+temp.getString("field"));
                condition.append(" as "+temp.getString("field"));
                JSONArray  tempArray = temp.getJSONArray("with");
                if (tempArray!=null){
                    condition.append(" with ");
                    for (int j = 0; j < tempArray.size(); j++) {
                        JSONObject tempTemp =tempArray.getJSONObject(j);
                        condition= j==0?
                                condition.append(" "+tempTemp.getString("condition")):
                                condition.append(" and "+tempTemp.getString("condition"));
                    }
                }
            }
            return condition.toString();
        }
        return "";
    }
    /**
     * where 因为前面我会用一个 where 0=0  后面的条件只用追加 an
     *   like、notLike、lt、 gt、le、eq、notEq、in、notIn
     * */
    private  static String   getWhereCondition(JSONArray array ,String type){
        if (array!=null){
            StringBuffer  condition = new StringBuffer(" ");
            for (int i = 0; i <array.size() ; i++) {
                JSONObject temp =array.getJSONObject(i);
                condition.append(" and n."+temp.getString("field"));
                condition.append(" "+type);
                condition.append(" "+temp.getString("condition"));
            }
            return  condition.toString();
        }
        return "";
    }
        /**
         * @param type group by \ order by
         * [{field:value}]
         * */
        private static String getGroupBy(JSONArray array,String type){
            if (array!=null){
                StringBuffer  condition = new StringBuffer(" ");
                for (int i = 0; i <array.size() ; i++) {
                    JSONObject temp =array.getJSONObject(i);
                    condition=i==0?condition.append(" "+type+" "+temp.getString("field"))
                                  :condition.append(" , "+temp.getString("field"));
                }
                return  condition.toString();
            }
            return  "";
        }

    public  static<T> String getHqlSelectStr(Class<T> clazz){
            return "select n from "+clazz.getSimpleName()+" as n ";
    }

    public  static<T> String getHqlSelectCountStr(Class<T> clazz){
        return "select count(*) from "+clazz.getSimpleName()+" as n ";
    }
    public static<T> String getHqlDeleteStr(Class<T> clazz,String idName,Object[] ids){
            StringBuffer stringBuffer =new StringBuffer("");
            stringBuffer.append("delete from ")
                        .append(clazz.getSimpleName())
                        .append(" where ")
                        .append(idName)
                        .append("in (");
            for (int i = 0; i <ids.length ; i++) {
                stringBuffer= i==0?stringBuffer.append("'"+ids[i]+"'"):stringBuffer.append(",'"+ids[i]+"'");
            }
        return stringBuffer.append(")").toString();
    }

}
