package xyz.alviksar.jokelibrary;

import org.junit.Test;

public class JokerTest {

    /**
     * Checks that a Joker class returns not empty string.
     */
    @Test
    public void getJoke() {
        assert !Joker.getJoke().isEmpty();
    }
}