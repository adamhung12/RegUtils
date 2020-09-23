package me.xethh.utils.ReqUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Reg {
    private Pattern pattern;
    private Reg(String reg){
        pattern = Pattern.compile(reg);
    }

    public Matching matching(String str){
        return new Matching(pattern.matcher(str), this);
    }

    public static class Matching{
        private Matcher matcher;
        private Reg reg;
        private Matching(Matcher matcher, Reg reg){
            this.matcher = matcher;
            this.reg = reg;
        }

        public Matcher matcher() {
            return matcher;
        }

        public Reg reg() {
            return reg;
        }

        public Matching ifMatch(Consumer<Matcher> consumer){
            if(matcher.matches()){
                consumer.accept(matcher);
            }
            return this;
        }
        public Optional<String> ifMatchAndReturn(Function<Matcher, String> operation){
            if(matcher.matches()){
                return Optional.ofNullable(operation.apply(matcher));
            }
            return Optional.empty();
        }
        public List<Optional<String>> ifMatchAndReturns(Function<Matcher, String> ...operation){
            if(matcher.matches()){
                return Arrays.stream(operation).map(it->Optional.ofNullable(it.apply(matcher))).collect(Collectors.toList());
            }
            return new ArrayList<>();
        }
    }

    public static Reg of(String reg){
        return new Reg(reg);
    }
}
