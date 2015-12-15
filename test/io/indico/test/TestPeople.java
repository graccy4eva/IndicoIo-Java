package io.indico.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.indico.Indico;
import io.indico.api.results.BatchIndicoResult;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 9/3/15.
 */
public class TestPeople {

    @Test
    public void testSingle() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "Barack Obama is scheduled to give a talk next Saturday at the White House.";
        List<Map<String, Object>> results = test.people.predict(example).getPeople();

        boolean expected = false;
        for (Map<String, Object> obj : results) {
            if (obj.get("text").equals("Barack Obama")) {
                expected = true;
                break;
            }
        }

        assertTrue(expected);
    }

    @Test
    public void testBatch() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        final String example = "Barack Obama is scheduled to give a talk next Saturday at the White House.";
        BatchIndicoResult result = test.people.predict(new String[] {example, example});
        List<List<Map<String, Object>>> results = result.getPeople();
        assertTrue(results.size() == 2);
        boolean expected = false;
        for (Map<String, Object> obj : results.get(0)) {
            if (obj.get("text").equals("Barack Obama")) {
                expected = true;
                break;
            }
        }

        assertTrue(expected);
    }
}
