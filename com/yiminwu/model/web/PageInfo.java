package com.yiminwu.model.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import com.yiminwu.util.StringUtil;

public class PageInfo {
   
   public static final int DEFAULT_PAGE_SIZE = 15;

   private int pageSize;

   private int pageNum;

   private long totalCount;

   public PageInfo(int pageNum, int pageSize) {
      this.pageSize = pageSize;
      this.pageNum = pageNum;
   }

   public int getPageSize() {
      return pageSize;
   }

   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }

   public int getPageNum() {
      return pageNum;
   }

   public void setPageNum(int pageNum) {
      this.pageNum = pageNum;
   }

   public long getTotalCount() {
      return totalCount;
   }

   public void setTotalCount(long totalCount) {
      this.totalCount = totalCount;
   }

   public static PageInfo newMPageInfo(HttpServletRequest request) {
      int pageNum = StringUtil.toInt(request.getParameter("pageNum"), 0);
      int pageSize = StringUtil.toInt(request.getParameter("pageSize"), DEFAULT_PAGE_SIZE);
      PageInfo pageInfo = new PageInfo(pageNum, pageSize);
      return pageInfo;
   }
}
