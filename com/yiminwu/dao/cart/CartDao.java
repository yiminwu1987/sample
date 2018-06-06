package com.yiminwu.dao.cart;

import com.yiminwu.dao.common.BaseDao;
import com.yiminwu.model.cart.Cart;
/**
 * 
 * @author yimin wu
 *
 */
public interface CartDao extends BaseDao<Cart, Long> {

   public Cart getCartByUserId(Long userId);
}

