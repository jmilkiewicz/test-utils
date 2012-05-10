package pl.softmil.test.utils.hamcrest;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class StringContainsIgnoringCaseTest {

    @Test
    public void test() {
        assertThat("aSsS", StringContainsIgnoringCase.containsStringIgnoringCase("sss"));
    }
    
    @Test
    public void test2() {
        assertThat("aSsS", StringContainsIgnoringCase.containsStringIgnoringCase("SSS"));
    }
    
    @Test
    public void test3() {
        assertThat("aSsS", StringContainsIgnoringCase.containsStringIgnoringCase("SsS"));
    }
    
    @Test
    public void test4() {
        assertThat("ssSa", not(StringContainsIgnoringCase.containsStringIgnoringCase("saa")));
    }

}
