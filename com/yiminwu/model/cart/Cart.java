package com.yiminwu.model.cart;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import com.yiminwu.model.user.User;

/**
 * 
 * @author yimin wu
 * 购物车，一个会员对应一个购物车
 *
 */
public class Cart {

   private Long cartId; // 主键

   private User user; // 会员

   private Set<CartItem> items = new HashSet<CartItem>(); // 商品项

   public Long getCartId() {
      return cartId;
   }

   public void setCartId(Long cartId) {
      this.cartId = cartId;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Set<CartItem> getItems() {
      return items;
   }

   public void setItems(Set<CartItem> items) {
      this.items = items;
   }

   public BigDecimal getTotalItemNum() {
      BigDecimal res = new BigDecimal(0);
      for (CartItem item : items) {
         res = res.add(new BigDecimal(item.getQuantity()));
      }
      return res;
   }
   
   public BigDecimal getTotalAmount() {
      BigDecimal res = new BigDecimal(0);
      for (CartItem item : items) {
         res = res.add(item.getAmount());
      }
      return res;
   }
}
