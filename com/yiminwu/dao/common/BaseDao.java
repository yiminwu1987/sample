package com.yiminwu.dao.common;

import java.io.Serializable;

/**
 * 大部分Dao仅需要继承该接口即可
 * 
 */
public interface BaseDao<T, PK extends Serializable> extends GenericDao<T, PK> {

}
