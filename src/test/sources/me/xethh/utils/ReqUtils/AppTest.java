package me.xethh.utils.ReqUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        Reg.of("^(\\d+)$")
                .matching("12345")
                .ifMatch(m -> assertEquals("12345", m.group()))
                .ifMatch(m -> assertEquals("12345", m.group(1)))
        ;
    }

    @Test
    public void test1() {
        Optional<String> rs = Reg.of("^(\\d+)$")
                .matching("12345")
                .ifMatchAndReturn(Matcher::group);
        rs.ifPresentOrElse(str -> assertEquals("12345", str), Assert::fail);
    }

    @Test
    public void test() {
        List<Optional<String>> rs = Reg
                .of("^(\\d+),(\\d+)$")
                .matching("12345,1234")
                .ifMatchAndReturns(
                        m -> m.group(1),
                        m -> m.group(2)
                );

        rs.get(0).ifPresentOrElse(str -> assertEquals("12345", str), Assert::fail);
        rs.get(1).ifPresentOrElse(str -> assertEquals("1234", str), Assert::fail);
    }

    @Test
    public void test3() {
        String str = "122,444,6673,00,12,1";
        Reg.Matching matching = Reg
                .of("\\d+")
                .matching(str);

        Iterator<String> it = matching.loopMatched();
        it.hasNext();
        assertEquals("122", it.next());
        it.hasNext();
        assertEquals("444", it.next());
        it.hasNext();
        assertEquals("6673", it.next());
        it.hasNext();
        assertEquals("00", it.next());
        it.hasNext();
        assertEquals("12", it.next());
        it.hasNext();
        assertEquals("1", it.next());

        it = matching.loopMatched(0);
        it.hasNext();
        assertEquals("122", it.next());
        it.hasNext();
        assertEquals("444", it.next());
        it.hasNext();
        assertEquals("6673", it.next());
        it.hasNext();
        assertEquals("00", it.next());
        it.hasNext();
        assertEquals("12", it.next());
        it.hasNext();
        assertEquals("1", it.next());

        it = matching.loopMatched(1);
        it.hasNext();
        assertEquals("22", it.next());
        it.hasNext();
        assertEquals("444", it.next());
        it.hasNext();
        assertEquals("6673", it.next());
        it.hasNext();
        assertEquals("00", it.next());
        it.hasNext();
        assertEquals("12", it.next());
        it.hasNext();
        assertEquals("1", it.next());

        it = matching.loopMatched(2);
        it.hasNext();
        assertEquals("2", it.next());
        it.hasNext();
        assertEquals("444", it.next());
        it.hasNext();
        assertEquals("6673", it.next());
        it.hasNext();
        assertEquals("00", it.next());
        it.hasNext();
        assertEquals("12", it.next());
        it.hasNext();
        assertEquals("1", it.next());

        it = matching.loopMatched(3);
        it.hasNext();
        assertEquals("444", it.next());
        it.hasNext();
        assertEquals("6673", it.next());
        it.hasNext();
        assertEquals("00", it.next());
        it.hasNext();
        assertEquals("12", it.next());
        it.hasNext();
        assertEquals("1", it.next());

        it = matching.loopMatched(4);
        it.hasNext();
        assertEquals("444", it.next());
        it.hasNext();
        assertEquals("6673", it.next());
        it.hasNext();
        assertEquals("00", it.next());
        it.hasNext();
        assertEquals("12", it.next());
        it.hasNext();
        assertEquals("1", it.next());

        assertEquals("122:444:6673:00:12:1", matching.loopMatchedStream().collect(Collectors.joining(":")));
        matching.loopMatchedZippedStream()
                .forEach(item -> {
                    switch (item.v1) {
                        case 0:
                            assertEquals("122", item.v2);
                            break;
                        case 1:
                            assertEquals("444", item.v2);
                            break;
                        case 2:
                            assertEquals("6673", item.v2);
                            break;
                        case 3:
                            assertEquals("00", item.v2);
                            break;
                        case 4:
                            assertEquals("12", item.v2);
                            break;
                        case 5:
                            assertEquals("1", item.v2);
                            break;
                    }
                });

    }

}
