package pl.softmil.test.utils.waituntil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class WaitUntilTest {

    @Test(expected = TimeoutException.class)
    public void timeoutForAlwaysFalse() {
        Until<Object> alwaysFalseUntil = fixedAnswerUntil(false);

        WaitUntil<Object> waitUntil = new WaitUntil<Object>(1, 100,
                alwaysFalseUntil);
        waitUntil.waitFor();
    }

    @Test
    public void successForAlwaysTrue() {
        Until<Object> alwaysFalseUntil = fixedAnswerUntil(true);

        WaitUntil<Object> waitUntil = new WaitUntil<Object>(1, 100,
                alwaysFalseUntil);
        waitUntil.waitFor();
    }

    @Test(expected = TimeoutException.class)
    public void timeOutWhenTrueAfterTimeout() {
        Until<Map<String, Integer>> alwaysFalseUntil = trueAfterGivenNumberOfAttemps(12);

        WaitUntil<Map<String, Integer>> waitUntil = new WaitUntil<Map<String, Integer>>(
                1000, 100, alwaysFalseUntil);
        waitUntil.waitFor();
    }

    @Test
    public void successWhenTrueBeforeTimeout() {
        Until<Map<String, Integer>> alwaysFalseUntil = trueAfterGivenNumberOfAttemps(3);

        WaitUntil<Map<String, Integer>> waitUntil = new WaitUntil<Map<String, Integer>>(
                7000, 100, alwaysFalseUntil);
        waitUntil.waitFor();
    }

    private Until<Map<String, Integer>> trueAfterGivenNumberOfAttemps(
            final int attemps) {
        return new Until<Map<String, Integer>>() {
            private static final String INVOCATION_NUMBER= "invocationsNumber";
            @Override
            public boolean isTrue(Map<String, Integer> context) {
                Integer numberOfInvocations = context.get(INVOCATION_NUMBER) + 1;
                context.put(INVOCATION_NUMBER, numberOfInvocations);
                return numberOfInvocations.intValue() > attemps;
            }

            @Override
            public Map<String, Integer> getContext() {
                Map<String, Integer> result = new ConcurrentHashMap<String, Integer>();                              
                result.put(INVOCATION_NUMBER, new Integer(0));
                return result;
            }
        };
    }

    private Until<Object> fixedAnswerUntil(final boolean fixedAnswer) {
        return new Until<Object>() {

            @Override
            public boolean isTrue(Object t) {
                return fixedAnswer;
            }

            @Override
            public Object getContext() {
                return null;
            }
        };
    }

}
