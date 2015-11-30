package io.indico.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
public class TestKeywordsV2 {
    Map<String, Object> params = new HashMap<String, Object>() {{
        put("version", 2);
    }};
    @Test
    public void testKeywords() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "Chris was here at Indico Data Solutions";
        IndicoResult result = test.keywords.predict(example, params);
        Map<String, Double> results = result.getKeywords();

        for (String key : results.keySet()) {
            assertTrue(example.contains(key));
        }
    }

    @Test
    public void testBatchKeywords() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        final String example = "Chris was here at Indico Data Solutions";
        Set<String> words = new HashSet<>();
        Collections.addAll(words, example.toLowerCase().split(" "));
        BatchIndicoResult result = test.keywords.predict(new String[] {example, example}, params);
        List<Map<String, Double>> results = result.getKeywords();
        assertTrue(results.size() == 2);
        for (String key : results.get(0).keySet()) {
            assertTrue(example.contains(key));
        }
    }
}
