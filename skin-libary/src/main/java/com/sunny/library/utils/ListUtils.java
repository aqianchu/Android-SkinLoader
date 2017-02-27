package com.sunny.library.utils;

import java.util.List;

public class ListUtils {
	
	
	public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

}
