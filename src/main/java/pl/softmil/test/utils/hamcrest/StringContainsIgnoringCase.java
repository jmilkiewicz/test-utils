package pl.softmil.test.utils.hamcrest;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.SubstringMatcher;

public class StringContainsIgnoringCase extends SubstringMatcher {
    public StringContainsIgnoringCase(String substring) {
        super(substring);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.toLowerCase().indexOf(substring.toLowerCase()) >= 0;
    }

    @Override
    protected String relationship() {
        return "containing";
    }

    @Factory
    public static Matcher<String> containsString(String substring) {
        return new StringContains(substring);
    }

}
