package me.xethh.utils.ReqUtils;

import java.util.regex.Pattern;

public class Demo {
    public static void main(String[] args) {
        // Directly extract target string with smooth method call
        String result = Reg.of("[^\\d]*(\\d+)[^\\d]*")
                .matching("ab 1234 xd")
                .ifMatchAndReturn(m -> m.group(1)).get();

        System.out.println(result); // 1234


        // Create Reg object
        Reg reg = Reg.of("(\\d+)");

        // Get the pattern
        Pattern pattern = reg.pattern;

        // match a string
        Reg.Matching matching = reg.matching("12345");

        System.out.println(matching.ifMatchAndReturn(m->m.group(1)).get());

    }
}
