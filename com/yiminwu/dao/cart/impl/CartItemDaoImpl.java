package com.yiminwu.dao.cart.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import com.yiminwu.dao.cart.CartItemDao;
import com.yiminwu.dao.common.impl.BaseDaoImpl;
import com.yiminwu.model.cart.CartItem;
import com.yiminwu.model.web.PageInfo;
import com.yiminwu.util.CollectionUtil;

@Repository
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

   public CartItemDaoImpl() {
      super(CartItem.class);
   }

   @Override
   public CartItem getCartItemByProductId(Long cartId, Long productId) {
      if (cartId == null || productId == null) {
         return null;
      }
      String hql = " from CartItem si where si.cart.cartId = ? and si.product.productId = ?";
      List<CartItem> itemList = findByHql(hql, new Object[]{cartId, productId});
      if (itemList != null && !itemList.isEmpty()) {
         return itemList.iterator().next();
      }
      return null;
   }

   @Override
   public List<CartItem> getCartItemsByProductIds(Long cartId, Long[] productIds) {
      if (productIds == null || productIds.length == 0) {
         return Collections.EMPTY_LIST;
      }

      final String hql = " from CartItem si where si.cart.cartId = ? and product.productId in (:productIds) ";
      final Long tCartId = cartId;
      final Long[] tProductIds = productIds;

      return (List) getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query query = session.createQuery(hql);
            query.setParameter(0, tCartId);
            query.setParameterList("productIds", tProductIds);
            return (List<CartItem>) query.list();
         }
      });
   }

   @Override
   public List<CartItem> getCartItemsByCartId(Long cartId) {
      return this.getCartItemsByCartId(cartId, null, null);
   }

   @Override
   public List<CartItem> getCartItemsByCartId(Long cartId, PageInfo pageInfo) {
      return this.getCartItemsByCartId(cartId, null, null);
   }

   @Override
   public List<CartItem> getCartItemsByCartId(Long cartId, Boolean isSelected) {
      return this.getCartItemsByCartId(cartId, isSelected, null);
   }

   public List<CartItem> getCartItemsByCartId(Long cartId, Boolean isSelected, PageInfo pageInfo) {
      if (cartId == null || cartId == 0) {
         return Collections.EMPTY_LIST;
      }
      List params = new ArrayList();
      StringBuilder hql = new StringBuilder(" from CartItem si where si.cart.cartId = ? and si.productId is not null and si.shop is not null");
      params.add(cartId);
      if (isSelected != null) {
         hql.append(" and si.selected = ? ");
         params.add(isSelected);
      }
      if (pageInfo == null) {
         return findByHql(hql.toString(), params.toArray());
      } else {
         return findByHql(hql.toString(), params.toArray(), pageInfo);
      }

   }

   @Override
   public void deleteCartItems(Long cartId) {
      String hql = "delete CartItem where cartId = ? ";
      update(hql, cartId);
   }

   @Override
   public void deleteCartItems(Long cartId, Long[] productIds) {
      if (productIds == null || productIds.length == 0) {
         return;
      }

      getHibernateTemplate().deleteAll(getCartItemsByProductIds(cartId, productIds));
   }

   public void deleteCartItems(List<CartItem> itemList) {
      if (CollectionUtil.isEmpty(itemList)) {
         return;
      }
      getHibernateTemplate().deleteAll(itemList);
   }

}
