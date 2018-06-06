package com.yiminwu.service.cart;

import com.yiminwu.model.cart.Cart;
import com.yiminwu.service.common.BaseService;
/**
 * 
 * @author yimin wu
 *
 */
public interface CartService extends BaseService<Cart, Long> {
   public Cart getCartByUserId(Long userId);
}
