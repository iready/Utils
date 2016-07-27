package org.zyq.core.lang;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class NumberUtils {

    public static void main(String[] args) {
//        System.out.println(new NumberUtils().random_not_in_set(0, 1000, new ArrayList<Integer>()));
        System.out.println(NumberUtils.toInt("123"));
    }

    public static Integer random_not_in_set(Integer start, Integer end, List<Integer> list) {
        if (list == null) throw new NullPointerException("list为空");
        Random random = new Random();
        Integer i = null;
        do {
            i = start + random.nextInt(end - start);
        } while (list.contains(i));
        return i;
    }

    public static Integer toInt(Object o) {
        if (o instanceof Double) return ((Double) o).intValue();
        else if (o instanceof BigDecimal) return ((BigDecimal) o).intValue();
        return Integer.valueOf((o + "").trim());
    }

    public static boolean isNumic(Object o) {
        boolean flag = false;
        if (o instanceof Double) flag = true;
        else if (o instanceof BigDecimal) flag = true;
        else
            try {
                Integer.valueOf(o + "");
                flag = true;
            } catch (Exception e) {
            }
        return flag;
    }
}