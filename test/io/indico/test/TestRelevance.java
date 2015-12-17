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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 9/3/15.
 */
public class TestRelevance {
    Map<String, Object> params = new HashMap<String, Object>() {{
        put("queries", new String[] {"president", "president"});
    }};
    @Test
    public void testSingle() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "president";
        List<Double> results = test.relevance.predict(example, params).getRelevance();

        assertEquals(results.size(), 2);
    }

    @Test
    public void testBatch() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        final String example = "president";
        List<List<Double>> results = test.relevance.predict(new String[] {example, example}, params).getRelevance();
        assertTrue(results.size() == 2);
        assertEquals(results.get(0).size(), 2);

    }
}
