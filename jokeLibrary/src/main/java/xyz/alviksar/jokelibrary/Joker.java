package xyz.alviksar.jokelibrary;

/**
 * Provides jokes.
 */

public class Joker {
    private static final String[] jokes = {
            "The past does not repeat itself, but it rhymes.",
            "Few things are more irritating than when someone who is wrong is also very effective in making his point.",
            "If you find yourself in a hole, stop digging.",
            "Facts are stubborn things, but statistics are more pliable.",
            "Giving up smoking is easy… I've done it hundreds of times.",
            "Education: The path from cocky ignorance to miserable uncertainty.",
            "When I was a boy of fourteen, my father was so ignorant I could hardly stand to have the old man around; but when I got to be twenty-one, I was astonished at how much the old man had learned in seven years."
    };

    public static String getJoke() {
        int i = (int) (Math.random()*jokes.length);
        return jokes[i]
                + "\n\nMark Twain";
    }
}
