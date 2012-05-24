package pl.softmil.test.utils;

import java.util.Random;

public class EmailGenerator {
    private String prefix = "user";
    private String domain = "foo.bar";

    public String generateEmail() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(generateUserName());
        stringBuilder.append("@");
        stringBuilder.append(domain);
        return stringBuilder.toString();
    }

    private String generateUserName() {

        String randomNumber = generateSomeRandomNumber();
        String currentTimeFragment = currentTimeFragment();
        StringBuilder result = new StringBuilder();

        result.append(prefix);
        result.append(randomNumber);
        result.append(currentTimeFragment);     
        return result.toString();
    }

    private String currentTimeFragment() {
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(currentTimeMillis);
        return stringBuilder.substring(8);
    }

    private String generateSomeRandomNumber() {
        Random random = new Random(System.currentTimeMillis());
        int nextInt = random.nextInt(999);
        return Integer.toString(nextInt);
    }
}
