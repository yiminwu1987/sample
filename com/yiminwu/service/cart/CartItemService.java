package com.yiminwu.service.cart;

import java.util.List;
import com.yiminwu.model.cart.CartItem;
import com.yiminwu.model.web.PageInfo;
import com.yiminwu.service.common.BaseService;
/**
 * 
 * @author yimin wu
 *
 */
public interface CartItemService extends BaseService<CartItem, Long> {

   public CartItem getCartItemByProductId(Long cartId, Long productId);

   public List<CartItem> getCartItemsByCartId(Long cartId);

   public List<CartItem> getCartItemsByCartId(Long cartId, PageInfo pageInfo);
   
   public List<CartItem> getCartItemsByCartId(Long cartId, Boolean isGoodsIsSelected);

   public void deleteCartItems(Long cartId);

   public void deleteCartItems(List<CartItem> itemList);
   
   public Long updateAllSelected(Long cartId);
   
}
