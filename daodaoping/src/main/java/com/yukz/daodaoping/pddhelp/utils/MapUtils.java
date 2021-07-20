package com.yukz.daodaoping.pddhelp.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MapUtils {
	
	/**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<Integer, String> sortMapByKey(Map<Integer, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<Integer, String> sortMap = new TreeMap<Integer, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

   
}

class MapKeyComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer str1, Integer str2) {

        return str1.compareTo(str2);
    }
}
