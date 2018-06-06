package com.yiminwu.dao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yiminwu.model.web.PageInfo;

/**
 * 
 * @author yimin wu
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> {
   public T save(T entity);

   public T merge(T entity);

   public T get(PK id);

   public T getByPK(Serializable id);

   public void remove(PK id);

   public void remove(T entity);

   public void evict(T entity);

   public List<T> getAll(PageInfo pageInfo);

   public List<T> getAll();

   public List findByHql(String hql);
   
   public List findByHql(String hql, Object[] objs);

   public List findByHql(String hql, Map<String, Object> paramMap);

   public List findByHql(String hql, PageInfo pageInfo);

   public List findByHql(String hql, Object[] objs, PageInfo pageInfo);
   
   public List findBySql(String sql);

   public List findBySql(String sql, Object[] params);

   public List findBySql(String sql, Object[] params, PageInfo pageInfo, Class entity);

   public List findBySql(String sql, Map<String, Object> paramMap);

   public List<Map<String, Object>> findMapsBySql(String sql);

   public void flush();

   /**
    * 执行删除或更新语句
    * 
    * @param hql
    * @param params
    * @return 返回影响行数
    */
   public Long update(final String hql, final Object... params);

   public Long update(final String hql, final Map paramMap);

   /**
    * 返回queryString查询返回的记录数
    * 
    * @param queryString
    * @param values
    * @return Long
    */
   public Long getTotalItems(String queryString, final Object[] values);

   public Long getTotalItems(String queryString);

   public List findBySql(String sql, PageInfo pageInfo);
   
   public Integer updateBySql(final String sql, final Object[] params);

}
