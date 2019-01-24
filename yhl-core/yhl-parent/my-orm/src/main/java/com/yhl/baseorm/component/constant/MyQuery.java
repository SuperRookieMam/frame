package com.yhl.baseorm.component.constant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MyQuery {




    public static <T> TypedQuery<T> getTypedQuery(Class<T> tClass, EntityManager entityManager){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);

        return  null;
    }


    private static <S,  T> Root<T> applySpecificationToCriteria(Specification<T> spec, Class<T> domainClass, CriteriaQuery<S> query,EntityManager entityManager) {
        Assert.notNull(domainClass, "实体必须不为空!");
        Assert.notNull(query, "CriteriaQuery 必须不为空!");
        Root<T> root = query.from(domainClass);
        if (spec == null) {
            return root;
        } else {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            Predicate predicate = spec.toPredicate(root, query, builder);
            if (predicate != null) {
                query.where(predicate);
            }
            return root;
        }
    }

    public<T> Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = getPredicates(root,criteriaBuilder);
        int m =predicates.size();
        if (m>0){
            return criteriaBuilder.and(predicates.toArray(new  Predicate[predicates.size()]));
        }
        return criteriaBuilder.conjunction();
    }

    /**
     * 获得过滤条件数组
     * */
    public<T>  List<Predicate> getPredicates(Root<T> root, CriteriaBuilder criteriaBuilder){
        List<Predicate> predicates = new ArrayList();
        JSONObject orObject = selecteParam.getOr();
        if (orObject!=null&&!orObject.isEmpty()){
            Predicate[] predicates1 = getPredicateArray(root,orObject,criteriaBuilder);
            if (predicates1!=null){
                Predicate predicate=  criteriaBuilder.or(predicates1);
                predicates.add(predicate);
            }

        }
        JSONObject andObject = selecteParam.getAnd();
        if (andObject!=null&&!andObject.isEmpty()){
            Predicate[] predicates1 = getPredicateArray(root,andObject,criteriaBuilder);
            if (predicates1!=null){
                Predicate predicate=  criteriaBuilder.and(predicates1);
                predicates.add(predicate);
            }
        }
        return  predicates;
    }
    /**
     *取得引用对象的值比如说field.field2.field3 的值
     * */
    public static <T> Map<Path,Object> getPath(Root<T> root, JSONObject jsonObject){
        Map<Path,Object> map =null;
        if (jsonObject!=null&&!jsonObject.isEmpty()){
            Iterator<String> iterator = jsonObject.keySet().iterator();
            map =new HashMap<>();
            Path path=null;
            while (iterator.hasNext()){
                String  key =iterator.next();
                String[] keys=key.contains(".")?key.split("\\."):new String[]{key};
                path = root.get(keys[0]);
                for (int i = 1; i <keys.length ; i++) {
                    path = path.get(keys[i]);
                }
                map.put(path,jsonObject.get(key));
            }
        }
        return map;
    }

    public <T> Predicate[] getPredicateArray(Root<T> root, JSONObject jsonObject , CriteriaBuilder criteriaBuilder){
        List<Predicate> list = new ArrayList<>();
        Iterator<String> iterator =jsonObject.keySet().iterator();
        while (iterator.hasNext()){
            String key =iterator.next();
            JSONObject json =jsonObject.getJSONObject(key);
            Map<Path,Object> map  = getPath(root,json);
            Iterator<Map.Entry<Path,Object>> iterator1 =  map.entrySet().iterator();
            if ("like".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    Predicate predicate= criteriaBuilder.like(entry.getKey(),"%/"+entry.getValue()+"%",'/');
                    list.add(predicate);
                }
            }else if ("notLike".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    Predicate predicate= criteriaBuilder.notLike(entry.getKey(),"%/"+entry.getValue()+"%",'/');
                    list.add(predicate);
                }
            }else if ("lt".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    //判断时不时时间格式.只要匹配的倒时间格式则用时间比较
                    Predicate predicate=null;
                    String value=  entry.getValue().toString();
                    //比大小只支持时间和数字
                    if (value.matches("\\d{4}-\\d{2}-\\{d}{2}")
                            ||value.matches("\\d{4}-\\d{2}-\\{d}{2}\\s+\\d{2}:\\d{2}:\\d{2}")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(entry.getValue().toString(), formatter);
                        predicate = criteriaBuilder.lessThan(entry.getKey(),dateTime);
                    }else {
                        predicate= criteriaBuilder.lt(entry.getKey(),new BigDecimal(entry.getValue().toString()));
                    }
                    list.add(predicate);
                }
            }else if ("le".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    //判断时不时时间格式.只要匹配的倒时间格式则用时间比较
                    Predicate predicate=null;
                    String value=  entry.getValue().toString();
                    //比大小只支持时间和数字
                    if (value.matches("\\d{4}-\\d{2}-\\{d}{2}")
                            ||value.matches("\\d{4}-\\d{2}-\\{d}{2}\\s+\\d{2}:\\d{2}:\\d{2}")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(entry.getValue().toString(), formatter);
                        predicate = criteriaBuilder.lessThanOrEqualTo(entry.getKey(),dateTime);
                    }else {
                        predicate= criteriaBuilder.le(entry.getKey(),new BigDecimal(entry.getValue().toString()));
                    }
                    list.add(predicate);
                }
            }else if ("gt".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    //判断时不时时间格式.只要匹配的倒时间格式则用时间比较
                    Predicate predicate=null;
                    String value=  entry.getValue().toString();
                    //比大小只支持时间和数字
                    if (value.matches("\\d{4}-\\d{2}-\\{d}{2}")
                            ||value.matches("\\d{4}-\\d{2}-\\{d}{2}\\s+\\d{2}:\\d{2}:\\d{2}")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(entry.getValue().toString(), formatter);
                        predicate = criteriaBuilder.greaterThan(entry.getKey(),dateTime);
                    }else {
                        predicate= criteriaBuilder.gt(entry.getKey(),new BigDecimal(entry.getValue().toString()));
                    }
                    list.add(predicate);
                }
            }else if ("ge".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    //判断时不时时间格式.只要匹配的倒时间格式则用时间比较
                    Predicate predicate=null;
                    String value=  entry.getValue().toString();
                    //比大小只支持时间和数字
                    if (value.matches("\\d{4}-\\d{2}-\\{d}{2}")
                            ||value.matches("\\d{4}-\\d{2}-\\{d}{2}\\s+\\d{2}:\\d{2}:\\d{2}")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(entry.getValue().toString(), formatter);
                        predicate = criteriaBuilder.greaterThanOrEqualTo(entry.getKey(),dateTime);
                    }else {
                        predicate= criteriaBuilder.ge(entry.getKey(),new BigDecimal(entry.getValue().toString()));
                    }
                    list.add(predicate);
                }
            }else if ("eq".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    //判断时不时时间格式.只要匹配的倒时间格式则用时间比较
                    Predicate predicate=null;
                    String value=  entry.getValue().toString();
                    //比大小只支持时间和数字
                    if (value.matches("\\d{4}-\\d{2}-\\{d}{2}")
                            ||value.matches("\\d{4}-\\d{2}-\\{d}{2}\\s+\\d{2}:\\d{2}:\\d{2}")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(entry.getValue().toString(), formatter);
                        predicate = criteriaBuilder.equal(entry.getKey(),dateTime);
                    }else {
                        predicate= criteriaBuilder.equal(entry.getKey(),entry.getValue());
                    }
                    list.add(predicate);
                }
            }else if ("notEq".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    //判断时不时时间格式.只要匹配的倒时间格式则用时间比较
                    Predicate predicate=null;
                    String value=  entry.getValue().toString();
                    //比大小只支持时间和数字
                    if (value.matches("\\d{4}-\\d{2}-\\{d}{2}")
                            ||value.matches("\\d{4}-\\d{2}-\\{d}{2}\\s+\\d{2}:\\d{2}:\\d{2}")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(entry.getValue().toString(), formatter);
                        predicate = criteriaBuilder.notEqual(entry.getKey(),dateTime);
                    }else {
                        predicate= criteriaBuilder.notEqual(entry.getKey(),entry.getValue());
                    }
                    list.add(predicate);
                }
            }else if ("in".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    CriteriaBuilder.In predicate= criteriaBuilder.in(entry.getKey());
                    JSONArray jsonArray =(JSONArray) entry.getValue();
                    for (int i = 0; i <jsonArray.size() ; i++) {
                        predicate.value(jsonArray.get(i));
                    }
                    list.add(predicate);
                }
            }else if ("notIn".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    CriteriaBuilder.In predicate= criteriaBuilder.in(entry.getKey());
                    JSONArray jsonArray =(JSONArray) entry.getValue();
                    for (int i = 0; i <jsonArray.size() ; i++) {
                        predicate.value(jsonArray.get(i));
                    }
                    list.add(criteriaBuilder.not(predicate));
                }
            }else if ("null".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    Predicate predicate= criteriaBuilder.isNull(entry.getKey());
                    list.add(predicate);
                }
            }else if ("notNull".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    Predicate predicate= criteriaBuilder.isNotNull(entry.getKey());
                    list.add(predicate);
                }
            }else  if("isMember".equalsIgnoreCase(key)){

            }else if ("isNotMember".equalsIgnoreCase(key)){

            }
        }
        return list.size()==0?null:list.toArray(new Predicate[list.size()]);
    }


}
