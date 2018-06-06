package com.yiminwu.dao.common.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiminwu.dao.common.GenericDao;
import com.yiminwu.model.web.PageInfo;
import com.yiminwu.util.CollectionUtil;

/**
 * 
 * @author yimin wu
 *
 * @param <T>
 * @param <PK>
 */
@SuppressWarnings("unchecked")
abstract public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements
   GenericDao<T, PK> {
   protected Log logger = LogFactory.getLog(GenericDaoImpl.class);

   @Autowired
   protected JdbcTemplate jdbcTemplate;

   @Autowired
   public void setMySessionFactory(SessionFactory sessionFactory) {
      super.setSessionFactory(sessionFactory);
   }

   protected Class persistType;

   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   public void setPersistType(Class persistType) {
      this.persistType = persistType;
   }

   public GenericDaoImpl(Class persistType) {
      this.persistType = persistType;
   }

   public T get(PK id) {
      return (T) getHibernateTemplate().get(persistType, id);
   }

   public T getByPK(Serializable id) {
      return (T) getHibernateTemplate().get(persistType, id);
   }

   public T save(T entity) {
      getHibernateTemplate().saveOrUpdate(entity);
      return entity;
   }

   public T merge(T entity) {
      getHibernateTemplate().merge(entity);
      return entity;
   }

   public void evict(T entity) {
      getHibernateTemplate().evict(entity);
   }

   public List<T> getAll(final PageInfo pageInfo) {
      String hql = "from " + persistType.getName();
      return findByHql(hql, null, pageInfo);
   }

   public List<T> getAll() {
      return getAll(null);
   }

   public List findByHql(final String hql) {
      return findByHql(hql, Collections.EMPTY_MAP);
   }
   
   public List findByHql(final String hql, final Object[] objs) {
      return findByHql(hql, objs, null);
   }

   public List findByHql(final String hql, final Object[] objs, final PageInfo pageInfo) {
      return (List) getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query query = session.createQuery(hql);

            if (objs != null) {
               for (int i = 0; i < objs.length; i++) {
                  query.setParameter(i, objs[i]);
               }
            }

            List result = new ArrayList();
            if (null != pageInfo && pageInfo.getPageSize() > 0) {
               query.setFirstResult(pageInfo.getPageNum() * pageInfo.getPageSize());
               query.setMaxResults(pageInfo.getPageSize());
               result = query.list();

               if (result.size() <= 0 && pageInfo.getPageNum() > 0) {
                  // reset the page number to the first page.
                  pageInfo.setPageNum(0);
                  query.setFirstResult(0);
                  query.setMaxResults(pageInfo.getPageSize());
                  result = query.list();
               }

               Long totalCount = getTotalItems(hql, objs);
               pageInfo.setTotalCount(totalCount);

            } else {
               result = query.list();
            }
            return result;

         }
      });
   }

   public List findByHql(final String hql, final PageInfo pageInfo) {
      return findByHql(hql, null, pageInfo);
   }

   public List findByHql(final String hql, final Map<String, Object> paramMap) {
      return (List) getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query query = session.createQuery(hql);

            if (paramMap != null) {
               Set<String> keySet = paramMap.keySet();
               for (String string : keySet) {
                  Object obj = paramMap.get(string);
                  // 这里考虑传入的参数是什么类型，不同类型使用的方法不同
                  if (obj instanceof Collection) {
                     query.setParameterList(string, (Collection) obj);
                  } else if (obj instanceof Object[]) {
                     query.setParameterList(string, (Object[]) obj);
                  } else {
                     query.setParameter(string, obj);
                  }
               }
            }

            return query.list();
         }
      });
   }

   public void remove(PK id) {
      getHibernateTemplate().delete(get(id));
   }

   public void remove(T entity) {
      getHibernateTemplate().delete(entity);
   }

   public void flush() {
      getHibernateTemplate().flush();
   }

   /**
    * 执行删除或更新语句
    * 
    * @param hql
    * @param params
    * @return 返回影响行数
    */
   public Long update(final String hql, final Object... params) {
      return (Long) getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query query = session.createQuery(hql);
            int i = 0;
            for (Object param : params) {
               query.setParameter(i++, param);
            }
            Integer rows = query.executeUpdate();
            return new Long(rows);
         }
      });
   }

   public Long update(final String hql, final Map paramMap) {
      return (Long) getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query query = session.createQuery(hql);
            int i = 0;

            if (paramMap != null) {
               Set<String> keySet = paramMap.keySet();
               for (String string : keySet) {
                  Object obj = paramMap.get(string);
                  // 这里考虑传入的参数是什么类型，不同类型使用的方法不同
                  if (obj instanceof Collection) {
                     query.setParameterList(string, (Collection) obj);
                  } else if (obj instanceof Object[]) {
                     query.setParameterList(string, (Object[]) obj);
                  } else {
                     query.setParameter(string, obj);
                  }
               }
            }

            Integer rows = query.executeUpdate();
            return new Long(rows);
         }
      });
   }

   /**
    * 返回queryString查询返回的记录数
    * 
    * @param queryString
    * @param values
    * @return Long
    */
   public Long getTotalItems(String queryString, final Object[] values) {

      int orderByIndex = queryString.toUpperCase().indexOf(" ORDER BY ");

      if (orderByIndex != -1) {
         queryString = queryString.substring(0, orderByIndex);
      }

      QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(queryString, queryString,
         java.util.Collections.EMPTY_MAP, (org.hibernate.engine.SessionFactoryImplementor) getSessionFactory());
      queryTranslator.compile(java.util.Collections.EMPTY_MAP, false);
      final String sql = "select count(*) from (" + queryTranslator.getSQLString() + ") tmp_count_t";

      Object reVal = getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query = session.createSQLQuery(sql);
            if (values != null) {
               for (int i = 0; i < values.length; i++) {
                  query.setParameter(i, values[i]);
               }
            }
            return query.uniqueResult();
         }
      });

      // if(reVal==null) return new Long(0);

      return new Long(reVal.toString());
   }

   /**
    * 返回queryString查询返回的记录数
    * 
    * @param queryString
    * @param values
    * @return Long
    */
   public Long getTotalItemsBySql(String queryString) {

      int orderByIndex = queryString.toUpperCase().indexOf(" ORDER BY ");

      if (orderByIndex != -1) {
         queryString = queryString.substring(0, orderByIndex);
      }

      final String sql = "select count(*) from (" + queryString + ") tmp_count_t";

      Object reVal = getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query = session.createSQLQuery(sql);
            return query.uniqueResult();
         }
      });

      // if(reVal==null) return new Long(0);
      return new Long(reVal.toString());
   }

   public Long getTotalItemsBySql(String queryString, final Object[] values) {

      int orderByIndex = queryString.toUpperCase().indexOf(" ORDER BY ");

      if (orderByIndex != -1) {
         queryString = queryString.substring(0, orderByIndex);
      }

      final String sql = "select count(*) from (" + queryString + ") tmp_count_t";

      Object reVal = getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query = session.createSQLQuery(sql);
            if (values != null) {
               for (int i = 0; i < values.length; i++) {
                  query.setParameter(i, values[i]);
               }
            }
            return query.uniqueResult();
         }
      });

      // if(reVal==null) return new Long(0);

      return new Long(reVal.toString());
   }

   public Long getTotalItems(String queryString) {
      return getTotalItems(queryString, null);
   }

   @Override
   public List findBySql(final String sql) {
      return findBySql(sql, CollectionUtil.EMPTY_MAP);
   }

   @Override
   public List findBySql(final String sql, final Map<String, Object> paramMap) {
      return getHibernateTemplate().execute(new HibernateCallback<List>() {

         @Override
         public List doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query = session.createSQLQuery(sql);

            if (paramMap != null) {
               Set<String> keySet = paramMap.keySet();
               for (String string : keySet) {
                  Object obj = paramMap.get(string);
                  if (obj instanceof Collection) {
                     query.setParameterList(string, (Collection) obj);
                  } else if (obj instanceof Object[]) {
                     query.setParameterList(string, (Object[]) obj);
                  } else {
                     query.setParameter(string, obj);
                  }
               }
            }

            return query.list();
         }
      });
   }

   @Override
   public List findBySql(final String sql, final Object[] params) {
      return getHibernateTemplate().execute(new HibernateCallback<List>() {

         @Override
         public List doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query = session.createSQLQuery(sql);

            if (params != null) {
               for (int i = 0; i < params.length; i++) {
                  query.setParameter(i, params[i]);
               }
            }

            return query.list();
         }
      });
   }
   
   @Override
   public Integer updateBySql(final String sql, final Object[] params){
      return getHibernateTemplate().execute(new HibernateCallback<Integer>(){
         
         @Override
         public Integer doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query = session.createSQLQuery(sql);
            
            if (params != null) {
               for (int i = 0; i < params.length; i++) {
                  query.setParameter(i, params[i]);
               }
            }
            return query.executeUpdate();
         }
      });
   }
   @Override
   public List findBySql(final String sql, final Object[] params, final PageInfo pageInfo, final Class entity) {
      return getHibernateTemplate().execute(new HibernateCallback<List>() {

         @Override
         public List doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query = session.createSQLQuery(sql);
            if (entity != null) {
               query.addEntity(entity);
            }

            if (params != null) {
               for (int i = 0; i < params.length; i++) {
                  query.setParameter(i, params[i]);
               }
            }

            List result = new ArrayList();
            if (null != pageInfo && pageInfo.getPageSize() > 0) {
               query.setFirstResult(pageInfo.getPageNum() * pageInfo.getPageSize());
               query.setMaxResults(pageInfo.getPageSize());
               result = query.list();

               if (result.size() <= 0 && pageInfo.getPageNum() > 0) {
                  // reset the page number to the first page.
                  pageInfo.setPageNum(0);
                  query.setFirstResult(0);
                  query.setMaxResults(pageInfo.getPageSize());
                  result = query.list();
               }

               Long totalCount = getTotalItemsBySql(sql, params);
               pageInfo.setTotalCount(totalCount);

            } else {
               result = query.list();
            }
            return result;
         }
      });
   }
   
   public List findBySql(final String sql, final PageInfo pageInfo) {
      return getHibernateTemplate().execute(new HibernateCallback<List>() {

         @Override
         public List doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query = session.createSQLQuery(sql);

            List result = new ArrayList();
            if (null != pageInfo && pageInfo.getPageSize() > 0) {
               query.setFirstResult(pageInfo.getPageNum() * pageInfo.getPageSize());
               query.setMaxResults(pageInfo.getPageSize());
               result = query.list();

               if (result.size() <= 0 && pageInfo.getPageNum() > 0) {
                  // reset the page number to the first page.
                  pageInfo.setPageNum(0);
                  query.setFirstResult(0);
                  query.setMaxResults(pageInfo.getPageSize());
                  result = query.list();
               }

               Long totalCount = getTotalItemsBySql(sql);
               pageInfo.setTotalCount(totalCount);

            } else {
               result = query.list();
            }

            return result;
         }
      });
   }

   @Override
   public List<Map<String, Object>> findMapsBySql(final String sql) {
      return (List<Map<String, Object>>) getHibernateTemplate().execute(new HibernateCallback() {

         @Override
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query query = session.createSQLQuery(sql);
            return (List<Map<String, Object>>) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
         }
      });
   }
   
}
