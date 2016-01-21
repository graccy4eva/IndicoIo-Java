package io.indico.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.indico.Indico;
import io.indico.api.utils.IndicoException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Annie on 1/21/16.
 */
public class TestTextFeatures {
    @Test
    public void testTextFeatures() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "Barack Obama is scheduled to give a talk next Saturday at the White House.";
        List<Double> results = test.textFeatures.predict(example).getTextFeatures();
        assertTrue(results.size() == 300);
    }

    @Test
    public void testBatchTextFeatures() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

       final String example = "Barack Obama is scheduled to give a talk next Saturday at the White House.";

        List<List<Double>> results = test.textFeatures.predict(new String[] {example, example}).getTextFeatures();
        assertTrue(results.size() == 2);
        assertTrue(results.get(0).equals(results.get(1)));
    }
}
