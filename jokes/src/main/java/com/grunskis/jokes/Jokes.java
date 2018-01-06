package com.grunskis.jokes;

import java.util.Random;

public class Jokes {

    private static final String[] JOKES = {
            "Joke 1",
            "Joke 2",
            "Joke 3",
            "Joke 4",
            "Joke 5"
    };

    public static String getRandomJoke() {
        Random r = new Random();
        return JOKES[r.nextInt(JOKES.length)];
    }
}
