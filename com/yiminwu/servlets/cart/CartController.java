package com.yiminwu.servlets.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yiminwu.model.cart.Cart;
import com.yiminwu.model.cart.CartItem;
import com.yiminwu.model.product.Product;
import com.yiminwu.model.web.PageInfo;
import com.yiminwu.service.cart.CartItemService;
import com.yiminwu.service.cart.CartService;
import com.yiminwu.service.product.ProductService;
import com.yiminwu.service.user.SessionService;
import com.yiminwu.util.CollectionUtil;
import com.yiminwu.util.StringUtil;

/**
 * 
 * @author yimin wu
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {
   
   @Autowired
   private ProductService productService;
   
   @Autowired
   private SessionService sessionService;
   
   @Autowired
   private CartService cartService;

   @Autowired
   private CartItemService cartItemService;
   
   //返回购物车页面
   @RequestMapping(value = "/list", method = {RequestMethod.GET})
   public String listCart(Model model, HttpServletRequest request) {
      return "cart/cartList";
   }
   
   //查询当前会员的购物车产品项列表
   @ResponseBody
   @RequestMapping(value = "/search", method = {RequestMethod.GET})
   public Object[] list(Model model, HttpServletRequest request) {
      
      PageInfo pageInfo = PageInfo.newMPageInfo(request);//分批查询，一次查询10条
      
      Product currentUser = sessionService.getSessionUser(request.getSession());//获取当前会员
      Cart cart = cartService.getCartByUserId(currentUser.getUserId());//获取当前会员的购物车
      
      List<CartItem> itemList = cart == null ? new ArrayList<CartItem>() : 
         cartItemService.getCartItemsByCartId(cart.getCartId(), pageInfo); //查询购物车产品项
      
      return new Object[]{pageInfo, itemList};
   }

   //添加购物车
   @ResponseBody
   @RequestMapping(value = "/add", method = {RequestMethod.POST})
   public CartItem add(Model model, HttpServletRequest request, @RequestParam(required = true) Long productId,
      @RequestParam(required = false) Integer quantity) {
      
      Product currentUser = sessionService.getSessionUser(request.getSession());//获取当前会员
      Cart cart = cartService.getCartByUserId(currentUser.getUserId());//获取当前会员的购物车
      
      if (cart == null) {
         cart = new Cart();
         cart.setUser(currentUser);
         cartService.save(cart);
      }
      
      Product product = productService.get(productId);
      CartItem cartItem = cartItemService.getCartItemByProductId(cart.getCartId(), productId);
      if (cartItem == null) {
         cartItem = new CartItem();
         cartItem.setCart(cart);
         cartItem.setProduct(product);
         cartItem.setQuantity(0);
         cartItem.setSelected(true);
      }
      
      cartItem.setQuantity(cartItem.getQuantity() + (StringUtil.isNullOrZero(quantity) ? 1 : quantity));
      cartItemService.save(cartItem);
      return cartItem;
   }
   
   //修改购买数量，或者是否选中
   @ResponseBody
   @RequestMapping(value = "/update", method = {RequestMethod.POST})
   public CartItem update(Model model, @RequestParam(required = true) Long itemId,
      @RequestParam(required = false) Integer quantity, @RequestParam(required = false) boolean isSelected) {
      
      CartItem cartItem = cartItemService.get(itemId);//查询购物车产品项
      if (cartItem != null) {
         cartItem.setQuantity(quantity);
         cartItem.setSelected(isSelected);
         cartItemService.save(cartItem);
      }
      
      return cartItem;
   }

   @ResponseBody
   @RequestMapping(value = "/delete", method = {RequestMethod.POST})
   public Map delete(Model model, HttpServletRequest request, @RequestParam(required = true) Long itemId) {
      Map resultMap = new HashMap();
      CartItem cartItem = cartItemService.get(itemId);//查询购物车产品项
      if (cartItem != null) {
         cartItemService.remove(cartItem);
         resultMap.put("success", "true");
      } else {
         resultMap.put("success", "false");
      }

      return resultMap;
   }

   @ResponseBody
   @RequestMapping(value = "/batchDelete", method = {RequestMethod.POST})
   public Map batchDelete(Model model, HttpServletRequest request) {
      Map resultMap = new HashMap();
      Product currentUser = sessionService.getSessionUser(request.getSession());//获取当前会员 
      Cart cart = cartService.getCartByUserId(currentUser.getUserId());//查询当前会员购物车
      
      List<CartItem> cartItemList = cart == null ? null : cartItemService.getCartItemsByCartId(cart.getCartId(), true);
      if (CollectionUtil.isNotEmpty(cartItemList)) {
         cartItemService.deleteCartItems(cartItemList);
         resultMap.put("success", "true");
      } else {
         resultMap.put("success", "false");
      }

      return resultMap;
   }

   @ResponseBody
   @RequestMapping(value = "/selectAll", method = {RequestMethod.POST})
   public Map selectAll(Model model, HttpServletRequest request) {
      Map resultMap = new HashMap();
      Product currentUser = sessionService.getSessionUser(request.getSession());//获取当前会员 
   
      Cart cart = cartService.getCartByUserId(currentUser.getUserId());//查询当前会员购物车
      if (cart == null) {
         resultMap.put("success", "false");
         return resultMap;
      }
      cartItemService.updateAllSelected(cart.getCartId());
      resultMap.put("success", "true");
      return resultMap;
   }
}