package me.xethh.utils.ReqUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
                .ifMatch(m -> {
                    assertEquals("12345", m.group());
                })
                .ifMatch(m -> {
                    assertEquals("12345", m.group(1));
                })
        ;
    }
}
