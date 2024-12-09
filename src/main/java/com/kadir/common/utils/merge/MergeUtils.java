package com.kadir.common.utils.merge;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class MergeUtils {
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullAndZeroPropertyNames(source));
    }

    private static String[] getNullAndZeroPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : wrappedSource.getPropertyDescriptors()) {
            Object srcValue = wrappedSource.getPropertyValue(pd.getName());
            if (srcValue == null || (srcValue instanceof Number && ((Number) srcValue).intValue() == 0)
                    || (srcValue instanceof String && ((String) srcValue).isEmpty())) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }

//    private static String[] getNullPropertyNames(Object source) {
//        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
//        Set<String> emptyNames = new HashSet<>();
//        for (PropertyDescriptor pd : wrappedSource.getPropertyDescriptors()) {
//            Object srcValue = wrappedSource.getPropertyValue(pd.getName());
//            if (srcValue == null) emptyNames.add(pd.getName());
//        }
//        return emptyNames.toArray(new String[0]);
//    }
}
