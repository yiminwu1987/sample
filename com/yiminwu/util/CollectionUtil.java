package com.yiminwu.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CollectionUtil {
   
   public static final Collection EMPTY_COLLECTION = Collections.unmodifiableCollection(new ArrayList(0));

   public static final Map EMPTY_MAP = Collections.unmodifiableMap(new HashMap(0));

   public static final List EMPTY_LIST = Collections.unmodifiableList(new ArrayList(0));


   public static boolean isEmpty(Collection collection) {
      return collection == null || collection.isEmpty();
   }

   public static boolean isEmpty(Map map) {
      return map == null || map.isEmpty();
   }

   public static boolean isNotEmpty(Collection collection) {
      return !isEmpty(collection);
   }
}
