package com.yiminwu.service.common.impl;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yiminwu.dao.common.GenericDao;
import com.yiminwu.model.web.PageInfo;
import com.yiminwu.service.common.GenericService;

/**
 * 
 * @author yimin wu
 *
 * @param <T>
 * @param <PK>
 */
public abstract class GenericServiceImpl<T, PK extends Serializable> implements GenericService<T, PK> {

   protected Log logger = LogFactory.getLog(GenericServiceImpl.class);

   protected abstract GenericDao<T, PK> getDao();

   public GenericServiceImpl() {
   }

   public T get(PK id) {
      if (id == null) {
         return null;
      }
      return (T) getDao().get(id);
   }

   public T save(T entity) {
      return (T) getDao().save(entity);
   }

   public T merge(T entity) {
      return (T) getDao().merge(entity);
   }

   public void evict(T entity) {
      getDao().evict(entity);
   }

   public List<T> getAll() {
      return getAll(null);
   }

   public List<T> getAll(PageInfo pageInfo) {
      return getDao().getAll(pageInfo);
   }

   public void remove(PK id) {
      getDao().remove(id);
   }

   public void remove(T entity) {
      getDao().remove(entity);
   }

   public void flush() {
      getDao().flush();
   }

}
