package com.safedog.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author hongyang
 */
public final class CollectionUtil {
    /**降序 */
    private final static Integer SORT_DESC = 2;

    /**
     * 判断集合为空
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection<?> c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合不为空
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

    public static List<Integer> arrayConvert2List(String[] strs) {
        List<Integer> ints = new ArrayList<Integer>();
        for (String str : strs) {
            if (StringUtils.isNotBlank(str)) {
                ints.add(Integer.parseInt(str));
            }
        }

        return ints;
    }

    public static boolean containNullItem(List<String> strs) {
        return strs.contains("") || strs.contains(null);
    }

    public static Map<String, Integer> cloneMap(Map<String, Integer> from) {
        Map<String, Integer> sortMap = new TreeMap<String, Integer>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        sortMap.putAll(from);
        return sortMap;
    }

    /**
     * map排序并对特殊的key进行处理
     *
     * @param from
     * @param split key的分隔符
     * @return
     */
    public static Map<String, Map<String, Integer>> cloneMapSpec(Map<String, Map<String, Integer>> from, String split) {
        Map<String, Map<String, Integer>> sortMap = new TreeMap<String, Map<String, Integer>>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        sortMap.putAll(from);
        Map<String, Map<String, Integer>> to = new HashMap<>(16);
        for (String key : sortMap.keySet()) {
            if (StringUtils.isNotBlank(key) && key.contains(split)) {
                to.put(key.split(split)[1], sortMap.get(key));
            }
        }
        return sortMap;
    }

    /**
     * list数组转字符串
     *
     * @param strs         数组
     * @param split        连接符
     * @param containIndex 是否包含下标
     * @return
     */
    public static String list2String(List<String> strs, String split, boolean containIndex) {
        String s = "";
        for (int i = 0; i < strs.size(); i++) {
            if (containIndex) {
                s += (i + 1) + ".";
            }
            s += strs.get(i) + split;
        }

        return s;
    }

    /**
     * map转list排序并返回子集
     *
     * @param map
     * @param size
     * @return
     */
    public static List<Entry<Integer, Integer>> map2ListAndSort(Map<Integer, Integer> map, Integer size) {
        List<Entry<Integer, Integer>> mappingList = new ArrayList<Entry<Integer, Integer>>(map.entrySet());
        Collections.sort(mappingList, new Comparator<Entry<Integer, Integer>>() {
            @Override
            public int compare(Entry<Integer, Integer> o1,
                               Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        if (null != size) {
            mappingList = mappingList.subList(0, Math.min(mappingList.size(), size));
        }

        return mappingList;
    }

    /**
     * map按值排序
     *
     * @param oriMap
     * @param type   1 升序；2 降序。 默认升序
     * @return
     */
    public static Map<String, Integer> mapSort(Map<String, Integer> oriMap, final Integer type) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(
                oriMap.entrySet());
        Collections.sort(entryList, new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {
                if (SORT_DESC.equals(type)) {
                    return o2.getValue().compareTo(o1.getValue());
                } else {
                    return o1.getValue().compareTo(o2.getValue());
                }
            }
        });

        Iterator<Entry<String, Integer>> iter = entryList.iterator();
        Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    /**
     * 获取集合c中obj的个数
     *
     * @param c
     * @param obj
     * @return
     */
    public static int getCount(Collection<?> c, Object obj) {
        int count = CollectionUtils.countMatches(c, (Object o) -> {
            if (obj.equals(o)) {
                return true;
            }
            return false;
        });
        return count;
    }

    /**
     * 获取集合c中str的个数 忽略大小写
     *
     * @param c
     * @param str
     * @return
     */
    public static int getCountIgnorCase(Collection<String> c, String str) {
        int count = CollectionUtils.countMatches(c, (Object s) -> {
            if (str.equalsIgnoreCase(s.toString())) {
                return true;
            }
            return false;
        });
        return count;
    }

    public static List<Integer> stringList2IntegerList(List<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().map(Integer::parseInt).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static Integer[] stringArr2IntegerArr(String[] arr) {
        if (ArrayUtils.isNotEmpty(arr)) {
            Integer[] intArr = new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                intArr[i] = Integer.parseInt(arr[i]);
            }
            return intArr;
        }
        return null;
    }

}
