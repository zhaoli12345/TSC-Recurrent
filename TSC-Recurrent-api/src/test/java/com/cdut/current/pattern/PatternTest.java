package com.cdut.current.pattern;

import com.cdut.current.util.PatternUtil;
import org.junit.Test;
import java.util.*;

public class PatternTest {
    @Test
    public void testPattern() {
        String formula = "${1}";

        List<Long> ids = PatternUtil.getIdsFromFormula(formula);
        System.out.println(ids);
        Map<Long, Float> agesMap = new HashMap<>();
        agesMap.put(1L, 0.00011f);

        String infixExpression = PatternUtil.changeFormula(agesMap, formula);
        System.out.println(infixExpression);

        float age = PatternUtil.calculateAge(agesMap,infixExpression);
        System.out.println(age);

    }
}
