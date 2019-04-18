package com.happyldc.util;

import java.math.BigDecimal;

/**
 * @author ldc
 * @Created at 2018/6/22 8:55.
 */

public class MathUtil {
    /**
     * 浮点型减法运算
     * @param a
     * @param b
     * @return
     */
    public static double  subtractOfFloat(double a,double b){
        BigDecimal _a=new BigDecimal(Double.toString(a));
        BigDecimal _b=new BigDecimal(Double.toString(b));
        return _a.subtract(_b).doubleValue();
    }

    public static double addOfFloat(double a, double b) {
        BigDecimal _a=new BigDecimal(Double.toString(a));
        BigDecimal _b=new BigDecimal(Double.toString(b));
        return _a.add(_b).doubleValue();
    }
}
