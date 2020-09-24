package me.xethh.utils.ReqUtils;

import me.xethh.utils.functionalPacks.JStream;
import me.xethh.utils.functionalPacks.tuples.Tuple2;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * {@link Reg} is targeted to reduce the redundant coding for using java.util.regex.*
 */
public class Reg {
    private final Pattern pattern;

    /**
     * Constructor only accessible from static method {@link #of(String)}
     * @param reg the regular expression string
     */
    private Reg(String reg){
        pattern = Pattern.compile(reg);
    }

    /**
     * Match the string with regex pattern and return {@link Matching} object
     * @param str string to match
     * @return {@link Matching}
     */
    public Matching matching(String str){
        return new Matching(pattern.matcher(str), this);
    }

    /**
     * {@link Matching} object provide functionality to as match whole word ({@link #ifMatch(Consumer)}, {@link #ifMatchAndReturn(Function)}, {@link #ifMatchAndReturns(Function[])})
     * or find matched segment ({@link #loopMatched()}, {@link #loopMatched(int)}, {@link #loopMatchedStream()}, {@link #loopMatchedStream(int), {@link #loopMatchedZippedStream()}})
     */
    public static class Matching{
        private final Matcher matcher;
        private final Reg reg;
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

        public boolean matches(){
            return matcher.matches();
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

        public Iterator<String> loopMatched(){
            return loopMatched(0);
        }

        public Iterator<String> loopMatched(int index){
            return new Iterator<>() {
                final AtomicBoolean first = new AtomicBoolean(true);
                @Override
                public boolean hasNext() {
                    if(first.get()){
                        first.compareAndSet(true, false);
                        return matcher.find(index);
                    }
                    else{
                        return matcher.find();
                    }
                }

                @Override
                public String next() {
                    return matcher.group();
                }
            };
        }

        @SafeVarargs
        public final List<Optional<String>> ifMatchAndReturns(Function<Matcher, String>... operation){
            if(matcher.matches()){
                return Arrays.stream(operation).map(it->Optional.ofNullable(it.apply(matcher))).collect(Collectors.toList());
            }
            return new ArrayList<>();
        }
        public final Stream<String> loopMatchedStream(int index){
            Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(loopMatched(), index);
            return StreamSupport.stream(spliterator, false);
        }
        public final Stream<String> loopMatchedStream(){
            return loopMatchedStream(0);
        }
        public final Stream<Tuple2<Integer, String>> loopMatchedZippedStream(){
            return JStream.zipWithIndex(loopMatched());
        }

    }

    public static Reg of(String reg){
        return new Reg(reg);
    }

}
