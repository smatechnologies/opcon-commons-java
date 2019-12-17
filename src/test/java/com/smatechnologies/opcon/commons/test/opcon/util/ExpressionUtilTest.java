package com.smatechnologies.opcon.commons.test.opcon.util;

import com.smatechnologies.opcon.commons.opcon.event.OpconEventException;
import com.smatechnologies.opcon.commons.opcon.util.ExpressionUtil;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre PINON
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExpressionUtilTest {

    @Test
    public void test01SplitStringWithExpressions() throws OpconEventException {
        //Simple value
        List<String> params = ExpressionUtil.splitStringWithExpressions(null, ',');
        Assert.assertEquals(null, params);

        params = ExpressionUtil.splitStringWithExpressions("", ',');
        Assert.assertEquals(0, params.size());

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2", ',');
        Assert.assertEquals(1, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2"), params);

        params = ExpressionUtil.splitStringWithExpressions(",$CAT1:ACT2", ',');
        Assert.assertEquals(2, params.size());
        Assert.assertEquals(Arrays.asList("","$CAT1:ACT2"), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,", ',');
        Assert.assertEquals(2, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", ""), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param1", ',');
        Assert.assertEquals(2, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param1"), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,,param1", ',');
        Assert.assertEquals(3, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "", "param1"), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param1,param2", ',');
        Assert.assertEquals(3, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param1", "param2"), params);

        //Value with expression
        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param[[,]]1,param2", ',');
        Assert.assertEquals(3, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param[[,]]1", "param2"), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param1,[[,]],param2", ',');
        Assert.assertEquals(4, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param1", "[[,]]", "param2"), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param1[[[[,]],]],param2", ',');
        Assert.assertEquals(3, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param1[[[[,]],]]", "param2"), params);

        //Value with complex expression
        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param1,[[,[[,]],param2", ',');
        Assert.assertEquals(5, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param1", "[[", "[[,]]", "param2"), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param1,[[,]],]],param2", ',');
        Assert.assertEquals(5, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param1", "[[,]]", "]]", "param2"), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param1,]],[[,]],param2", ',');
        Assert.assertEquals(5, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param1", "]]", "[[,]]", "param2"), params);

        params = ExpressionUtil.splitStringWithExpressions("$CAT1:ACT2,param1,[[,]],[[,param2", ',');
        Assert.assertEquals(5, params.size());
        Assert.assertEquals(Arrays.asList("$CAT1:ACT2", "param1", "[[,]]", "[[", "param2"), params);
    }

    @Test
    public void text02RemoveExpressions() {
        //Simple value
        Assert.assertEquals(null, ExpressionUtil.removeExpressions(null));
        Assert.assertEquals("", ExpressionUtil.removeExpressions(""));
        Assert.assertEquals("b", ExpressionUtil.removeExpressions("b"));
        Assert.assertEquals("blue", ExpressionUtil.removeExpressions("blue"));

        //Value with expression
        Assert.assertEquals("", ExpressionUtil.removeExpressions("[[]]"));
        Assert.assertEquals("", ExpressionUtil.removeExpressions("[[yellow]]"));
        Assert.assertEquals("blue", ExpressionUtil.removeExpressions("[[yellow]]blue"));
        Assert.assertEquals("blue", ExpressionUtil.removeExpressions("blue[[yellow]]"));
        Assert.assertEquals("bluered", ExpressionUtil.removeExpressions("blue[[yellow]]red"));
        Assert.assertEquals("blueredblack", ExpressionUtil.removeExpressions("blue[[yellow]]red[[green]]black"));
        Assert.assertEquals("bluered", ExpressionUtil.removeExpressions("blue[[[[green]]]]red"));
        Assert.assertEquals("bluered", ExpressionUtil.removeExpressions("blue[[[[green]]yellow]]red"));
        Assert.assertEquals("bluered", ExpressionUtil.removeExpressions("blue[[yellow[[green]]]]red"));
        Assert.assertEquals("bluered", ExpressionUtil.removeExpressions("blue[[yellow[[green]]]]red[[black]]"));

        //Value with complex expression
        Assert.assertEquals("[[", ExpressionUtil.removeExpressions("[["));
        Assert.assertEquals("]]", ExpressionUtil.removeExpressions("]]"));
        Assert.assertEquals("[[red", ExpressionUtil.removeExpressions("[[red"));
        Assert.assertEquals("red[[", ExpressionUtil.removeExpressions("red[["));
        Assert.assertEquals("red[[blue", ExpressionUtil.removeExpressions("red[[blue"));
        Assert.assertEquals("]]red", ExpressionUtil.removeExpressions("]]red"));
        Assert.assertEquals("red]]", ExpressionUtil.removeExpressions("red]]"));
        Assert.assertEquals("red]]blue", ExpressionUtil.removeExpressions("red]]blue"));

        Assert.assertEquals("bluered[[black", ExpressionUtil.removeExpressions("blue[[yellow[[green]]]]red[[black"));
        Assert.assertEquals("blue[[yellowred", ExpressionUtil.removeExpressions("blue[[yellow[[green]]red[[black]]"));
        Assert.assertEquals("blue[[yellow[[red", ExpressionUtil.removeExpressions("blue[[yellow[[[[green]]red[[black]]"));
        Assert.assertEquals("blueyellowredwhite]]pink", ExpressionUtil.removeExpressions("blueyellow[[green]]red[[black]]white]]pink"));
        Assert.assertEquals("blueyellowred]]orangewhite", ExpressionUtil.removeExpressions("blueyellow[[green]]red]]orange[[black]]white"));
        Assert.assertEquals("blueyellowred]]white]]pink", ExpressionUtil.removeExpressions("blueyellow[[green]]red[[black]]]]white]]pink"));
    }
}
