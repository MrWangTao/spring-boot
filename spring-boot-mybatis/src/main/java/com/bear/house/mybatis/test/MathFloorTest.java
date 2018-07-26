package com.bear.house.mybatis.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author WangTao
 *         Created at 18/7/26 上午10:20.
 */
public class MathFloorTest {

    public static void main(String[] args) {
        /**
         * BigDecimal 做除法
         */
        double a = 9996;
        double b = 10001;
        BigDecimal bd = new BigDecimal(a);
        BigDecimal divide = bd.divide(new BigDecimal(b), 2, RoundingMode.DOWN);
        System.out.println(divide);

    }

}
