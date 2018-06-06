package com.yiminwu.service.common;

import java.io.Serializable;

/**
 * @author yimin wu
 * @param <T>实体类
 */
public interface BaseService<T, PK extends Serializable> extends GenericService<T, PK> {

}
