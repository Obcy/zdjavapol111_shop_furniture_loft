package com.loft.generators;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.util.Random;

public class OrderKeyGenerator implements ValueGenerator<String> {

    @Override
    public String generateValue(Session session, Object o) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}
