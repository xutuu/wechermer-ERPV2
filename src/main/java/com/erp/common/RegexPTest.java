package com.erp.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPTest {

    public static List<Object> regexPattern(String regex, String input) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<Object> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group(1));
        }
        System.out.println(list);
        return list;
    }

}
