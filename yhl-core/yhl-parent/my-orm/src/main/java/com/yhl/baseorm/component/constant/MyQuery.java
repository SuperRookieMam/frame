package com.yhl.baseorm.component.constant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MyQuery {




    public static <T> TypedQuery<T> getTypedQuery(Class<T> tClass, EntityManager entityManager,SelecteParam selecteParam){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> root =applySpecificationToCriteria(selecteParam,tClass,query,entityManager);
        Sort sort = getToSort( selecteParam);
        if (sort != null) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }
        return  entityManager.createQuery(query);
    }

    public static <T> TypedQuery<Long> getCountQuery(Class<T> tClass, EntityManager entityManager,SelecteParam selecteParam) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = applySpecificationToCriteria(selecteParam,tClass,query,entityManager);
        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }
        query.orderBy(Collections.emptyList());
        return entityManager.createQuery(query);
    }

    public static <T> Page<T> readPage(SelecteParam selecteParam,Class<T> tClass,EntityManager entityManager) {
        TypedQuery<T> query=getTypedQuery(tClass,entityManager,selecteParam);
        Sort sort = getToSort(selecteParam);
        PageRequest pageable =new PageRequest(selecteParam.getPageNum() - 1, selecteParam.getPageSize(),sort);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        long l=executeCountQuery(getCountQuery(tClass, entityManager, selecteParam));
        return new PageImpl(query.getResultList(), pageable, l);
    }



    public static Long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        List<Long> totals = query.getResultList();
        Long total = 0L;

        Long element;
        for(Iterator var3 = totals.iterator(); var3.hasNext(); total = total + (element == null ? 0L : element)) {
            element = (Long)var3.next();
        }

        return total;
    }



    public static Sort getToSort(SelecteParam selecteParam){
        JSONArray sort =selecteParam.getSort();
        if (sort==null||sort.isEmpty()){
            return null;
        }else {
            List<Sort.Order> list =new ArrayList<>();
            for (int i = 0; i < sort.size(); i++) {
                JSONObject jsonObject =sort.getJSONObject(i);
                Sort.Direction direction=  "desc".equalsIgnoreCase(jsonObject.getString("order"))
                        ?Sort.Direction.DESC
                        :Sort.Direction.ASC;
                list.add(new Sort.Order(direction,jsonObject.getString("name")));
            }
            return   new Sort(list);
        }
    }


    private static <T>  Root<T> applySpecificationToCriteria(SelecteParam selecteParam, Class<T> domainClass, CriteriaQuery query,EntityManager entityManager) {
        Assert.notNull(domainClass, "实体必须不为空!");
        Assert.notNull(query, "CriteriaQuery 必须不为空!");
        Root<T> root = query.from(domainClass);
        if (selecteParam == null) {
            return root;
        } else {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            Predicate predicate = toPredicate(root,builder,selecteParam);
            if (predicate != null) {
                query.where(predicate);
            }
            return root;
        }
    }

    public static <T> Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder,SelecteParam selecteParam) {
        List<Predicate> predicates = getPredicates(root,criteriaBuilder,selecteParam);
        int m =predicates.size();
        if (m>0){
            return criteriaBuilder.and(predicates.toArray(new  Predicate[predicates.size()]));
        }
        return criteriaBuilder.conjunction();
    }

    /**
     * 获得过滤条件数组
     * */
    private static<T>  List<Predicate> getPredicates(Root<T> root, CriteriaBuilder criteriaBuilder,SelecteParam selecteParam){
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
    private static <T> Map<Path,Object> getPath(Root<T> root, JSONObject jsonObject){
        Map<Path,Object> map =null;
        if (jsonObject!=null&&!jsonObject.isEmpty()){
            Iterator<String> iterator = jsonObject.keySet().iterator();
            map =new HashMap<>();
            Path path=null;
            while (iterator.hasNext()){
                String  key =iterator.next();
                //此处是表关联数据，注意仅限一层关联，如user.address，
                //查询user的address集合中，address的name为某个值
                if (key.contains(".")) {
                    String[] keys = StringUtils.split(key, ".");
                    //获取该属性的类型，Set？List？Map？
                    path = root.get(keys[0]);
                    Class clazz = path.getJavaType();
                    if (clazz.equals(Set.class)) {
                        SetJoin setJoin = root.joinSet(keys[0]);
                        for (int i = 1; i <keys.length ; i++) {
                            path = i==1?setJoin.get(keys[i]):path.get(keys[i]);
                        }
                    } else if (clazz.equals(List.class)) {
                        ListJoin listJoin = root.joinList(keys[0]);
                        for (int i = 1; i <keys.length ; i++) {
                            path = i==1?listJoin.get(keys[i]):path.get(keys[i]);
                        }
                    } else if (clazz.equals(Map.class)) {
                        MapJoin mapJoin = root.joinMap(keys[0]);
                        for (int i = 1; i <keys.length ; i++) {
                            path = i==1?mapJoin.get(keys[i]):path.get(keys[i]);
                        }
                    } else {
                        //是many to one时
                        for (int i = 1; i <keys.length ; i++) {
                            path = path.get(keys[i]);
                        }
                    }
                } else {
                    //单表查询
                    path = root.get(key);
                }
                map.put(path,jsonObject.get(key));
            }
        }
        return map;
    }

    private static <T> Predicate[] getPredicateArray(Root<T> root, JSONObject jsonObject , CriteriaBuilder criteriaBuilder) {
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
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    //注意这个时值哈,且注意Path 的参数，is
                     JSONArray jsonArray =(JSONArray)entry.getValue();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Predicate predicate= criteriaBuilder.isMember(jsonArray.get(i),entry.getKey());
                        list.add(predicate);
                    }

                }
            }else if ("isNotMember".equalsIgnoreCase(key)){
                while (iterator1.hasNext()){
                    Map.Entry<Path,Object> entry = iterator1.next();
                    //注意这个时值哈,且注意Path 的参数，is
                    JSONArray jsonArray =(JSONArray)entry.getValue();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Predicate predicate= criteriaBuilder.isNotMember(jsonArray.get(i),entry.getKey());
                        list.add(predicate);
                    }

                }
            }
        }
        return list.size()==0?null:list.toArray(new Predicate[list.size()]);
    }

}