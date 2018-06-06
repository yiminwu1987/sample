package com.yiminwu.model.cart;

import java.math.BigDecimal;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.yiminwu.model.product.Product;

/**
 * 
 * @author yimin wu
 * 购物车的商品项
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItem {

   private Long itemId; // 主键

   @JsonIgnore
   private Cart cart; // 所属购物车

   private Product product; // 商品

   private Integer quantity; // 商品数量
   
   private boolean selected; //是否勾选

   public Long getItemId() {
      return itemId;
   }

   public void setItemId(Long itemId) {
      this.itemId = itemId;
   }

   public Cart getCart() {
      return cart;
   }

   public void setCart(Cart cart) {
      this.cart = cart;
   }

   public Product getProduct() {
      return product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public Integer getQuantity() {
      return quantity;
   }

   public void setQuantity(Integer quantity) {
      this.quantity = quantity;
   }

   public BigDecimal getAmount() {
      return new BigDecimal(this.quantity).multiply(new BigDecimal(this.product.getPrice()));
   }

   public boolean isSelected() {
      return selected;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }
}
