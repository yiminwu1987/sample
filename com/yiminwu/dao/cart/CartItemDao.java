package com.yiminwu.dao.cart;

import java.util.List;
import com.yiminwu.dao.common.BaseDao;
import com.yiminwu.model.cart.CartItem;
import com.yiminwu.model.web.PageInfo;
/**
 * 
 * @author yimin wu
 *
 */
public interface CartItemDao extends BaseDao<CartItem, Long> {

   public CartItem getCartItemByProductId(Long cartId, Long productId);

   public List<CartItem> getCartItemsByCartId(Long cartId);

   public List<CartItem> getCartItemsByCartId(Long cartId, PageInfo pageInfo);

   public List<CartItem> getCartItemsByCartId(Long cartId, Boolean isSelected);

   public List<CartItem> getCartItemsByProductIds(Long cartId, Long[] productIds);

   public void deleteCartItems(Long cartId);

   public void deleteCartItems(Long cartId, Long[] productIds);

   public void deleteCartItems(List<CartItem> itemList);
}

