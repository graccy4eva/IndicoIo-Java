package io.indico.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.indico.Indico;
import io.indico.api.text.Persona;
import io.indico.api.text.Personality;
import io.indico.api.utils.IndicoException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 9/3/15.
 */
public class TestPersonality {
    String testString = "I love my friends!";

    @Test
    public void testPersonality() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        Map<Personality, Double> results = test.personality.predict(testString).getPersonality();
        for (Personality personality: Personality.values()) {
            assertTrue(results.containsKey(personality));
        }
    }
    @Test
    public void testPersona() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        Map<Persona, Double> results = test.persona.predict(testString).getPersona();
        for (Persona persona: Persona.values()) {
            assertTrue(results.containsKey(persona));
        }
    }
}