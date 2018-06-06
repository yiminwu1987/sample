package com.yiminwu.service.cart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yiminwu.dao.cart.CartDao;
import com.yiminwu.dao.common.GenericDao;
import com.yiminwu.model.cart.Cart;
import com.yiminwu.service.cart.CartService;
import com.yiminwu.service.common.impl.BaseServiceImpl;

/**
 * 
 * @author yimin wu
 *
 */
@Service
public class CartServiceImpl extends BaseServiceImpl<Cart, Long> implements CartService{
   
   @Autowired
   private CartDao dao;

   @Override
   protected GenericDao<Cart, Long> getDao() {
      return dao;
   }

   @Override
   public Cart getCartByUserId(Long userId) {
      return dao.getCartByUserId(userId);
   }
}