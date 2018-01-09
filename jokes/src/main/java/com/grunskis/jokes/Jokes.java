package com.grunskis.jokes;

import java.util.Random;

public class Jokes {

    private static final String[] JOKES = {
            // taken from http://www.gotlines.com/jokes/
            "Just changed my Facebook name to 'No one' so when I see stupid posts I can click like and it will say 'No one likes this'.",
            "What's the difference between snowmen and snowladies? Snowballs",
            "How do you make holy water? You boil the hell out of it.",
            "Why did the blonde get excited after finishing her puzzle in 6 months? -- The box said 2-4 years!",
            "What do you call a fat psychic? A four chin teller."
    };

    public static String getRandomJoke() {
        Random r = new Random();
        return JOKES[r.nextInt(JOKES.length)];
    }
}
