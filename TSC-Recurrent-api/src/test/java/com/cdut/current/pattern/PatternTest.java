package com.cdut.current.pattern;

import com.cdut.current.util.PatternUtil;
import org.junit.Test;
import java.util.*;

public class PatternTest {
    @Test
    public void testPattern() {
        String formula = "${1}-0.9*(${1}-${2})";

        List<Long> ids = PatternUtil.getIdsFromFormula(formula);
        System.out.println(ids);
        Map<Long, Float> agesMap = new HashMap<>();
        agesMap.put(1L, 5.335f);
        agesMap.put(2L, 2.58f);

        String infixExpression = PatternUtil.changeFormula(agesMap, formula);
        System.out.println(infixExpression);

        float age = PatternUtil.calculateAge(agesMap,infixExpression);
        System.out.println(age);

    }
}
