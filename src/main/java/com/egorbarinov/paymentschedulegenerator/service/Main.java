package com.egorbarinov.paymentschedulegenerator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    public static void main(String[] args) {

        /*
        банки считают начисленные проценты на утро 31 декабря( то есть проценты, начисленные по 30 декабря включительно),
        соответственно, если кредит выдан 27 декабря, то начислено будет за 4 дня: 27,28,29 и 30 декабря включительно.
        Проценты за 31 декабря уже будут рассчитываться в следуюущем году, грубо говоря на утро 01 января,
         с учетом количества дней нового года
         */

        // дата выдачи 27.12.2017

        int dayOf365 = 4;
        int dayOf366 = 27;
        BigDecimal interest = new BigDecimal(0.112);

        //  ОП * ПС* КОЛ ДНЕЙ В МЕСЯЦЕ / КОЛ-ВО ДНЕЙ В ГОДУ
        BigDecimal op = new BigDecimal(2924692.77);

        BigDecimal percentOf365 = op.multiply(interest.multiply(BigDecimal.valueOf(dayOf365)))
                .divide(BigDecimal.valueOf(365), 2, RoundingMode.HALF_UP);
        System.out.println(percentOf365);
        BigDecimal percentOf366 = op.multiply(interest.multiply(BigDecimal.valueOf(dayOf366)))
                .divide(BigDecimal.valueOf(366),2, RoundingMode.HALF_UP);

        System.out.println(percentOf366);
        System.out.println(percentOf365.add(percentOf366));     //должно быть 27754,43





    }
}
