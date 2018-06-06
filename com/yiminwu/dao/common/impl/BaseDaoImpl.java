package com.yiminwu.dao.common.impl;

import java.io.Serializable;
import com.yiminwu.dao.common.BaseDao;
import com.yiminwu.dao.common.impl.GenericDaoImpl;

/**
 * @author yimin wu
 * @param <T>
 *           基础表类，对于主键为long类型　，则直接继承该类，若主键为其他类型， 需要直接继承GenericDaoImpl
 */
public class BaseDaoImpl<T, PK extends Serializable> extends GenericDaoImpl<T, PK> implements BaseDao<T, PK> {

   public BaseDaoImpl(Class persistType) {
      super(persistType);
   }

}
