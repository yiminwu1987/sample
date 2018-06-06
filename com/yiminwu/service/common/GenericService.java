package com.yiminwu.service.common;

import java.io.Serializable;
import java.util.List;
import com.yiminwu.model.web.PageInfo;

/**
 * @author yimin wu
 * @param <T>
 * @param <PK>
 */
// @WebService
public interface GenericService<T, PK extends Serializable> {

   /**
    * @param entity
    * @return
    */
   public T save(T entity);

   /**
    * merge the object
    * 
    * @param entity
    * @return
    */
   public T merge(T entity);

   /**
    * evict the object
    * 
    * @param entity
    */
   public void evict(T entity);

   /**
    * @param id
    * @return
    */
   public T get(PK id);

   /**
    * @return
    */
   public List<T> getAll();

   public List<T> getAll(PageInfo pageInfo);

   public void remove(PK id);

   public void remove(T entity);

   /**
    * flush the session
    */
   public void flush();

}
