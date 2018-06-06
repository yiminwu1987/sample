package com.yiminwu.dao.cart.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yiminwu.dao.cart.CartDao;
import com.yiminwu.dao.common.impl.BaseDaoImpl;
import com.yiminwu.model.cart.Cart;
import com.yiminwu.model.cart.CartItem;
/**
 * 
 * @author yimin wu
 *
 */
@Repository
public class CartDaoImpl extends BaseDaoImpl<Cart, Long> implements CartDao{

   public CartDaoImpl() {
      super(CartItem.class);
   }

   @Override
   public Cart getCartByUserId(Long userId) {
      String hql = " from Cart c where c.user.userId = ?";
      List<Cart> cartList = findByHql(hql, new Object[]{userId});
      if (cartList != null && !cartList.isEmpty()) {
         return cartList.iterator().next();
      }
      return null;
   }

   
}
