package me.xethh.utils.ReqUtils;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    }

    public static Reg of(String reg){
        return new Reg(reg);
    }
}
