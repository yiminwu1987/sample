package com.yiminwu.service.common.impl;

import java.io.Serializable;
import com.yiminwu.service.common.BaseService;

public abstract class BaseServiceImpl<T, PK extends Serializable> extends GenericServiceImpl<T, PK> implements
   BaseService<T, PK> {

   public BaseServiceImpl() {
   }
}
