package com.yiminwu.service.cart.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yiminwu.dao.cart.CartDao;
import com.yiminwu.dao.cart.CartItemDao;
import com.yiminwu.dao.common.GenericDao;
import com.yiminwu.model.cart.CartItem;
import com.yiminwu.model.web.PageInfo;
import com.yiminwu.service.cart.CartItemService;
import com.yiminwu.service.common.impl.BaseServiceImpl;
/**
 * 
 * @author yimin wu
 *
 */
@Service
public class CartItemServiceImpl extends BaseServiceImpl<CartItem, Long> implements CartItemService {

   @Autowired
   private CartItemDao dao;

   @Autowired
   private CartDao cartDao;

   public CartItemServiceImpl() {
   }

   @Override
   protected GenericDao<CartItem, Long> getDao() {
      return dao;
   }

   @Override
   public CartItem getCartItemByProductId(Long cartId, Long productId) {
      return dao.getCartItemByProductId(cartId, productId);
   }

   @Override
   public List<CartItem> getCartItemsByCartId(Long cartId) {
      return dao.getCartItemsByCartId(cartId);
   }

   @Override
   public List<CartItem> getCartItemsByCartId(Long cartId, PageInfo pageInfo) {
      return dao.getCartItemsByCartId(cartId, pageInfo);
   }
   
   @Override
   public List<CartItem> getCartItemsByCartId(Long cartId, Boolean isSelected) {
      return dao.getCartItemsByCartId(cartId, isSelected);
   }

   @Override
   public void deleteCartItems(Long cartId) {
      dao.deleteCartItems(cartId);
   }

   public void deleteCartItems(List<CartItem> itemList) {
      dao.deleteCartItems(itemList);
   }
   
   public Long updateAllSelected(Long cartId) {
      String hql = "select si.itemId from CartItem si where si.goodsIsSelected = 0 and si.cart.cartId = ?";
      List params = new ArrayList();
      params.add(cartId);
      Long totalCount = dao.getTotalItems(hql, params.toArray());

      hql = "update CartItem set goodsIsSelected = ? where cart.cartId = ?";
      return dao.update(hql, totalCount > 0, cartId);
   }

}