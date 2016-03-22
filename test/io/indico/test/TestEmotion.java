package io.indico.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.indico.Indico;
import io.indico.api.results.BatchIndicoResult;
import io.indico.api.results.IndicoResult;
import io.indico.api.text.Emotion;
import io.indico.api.utils.IndicoException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 3/18/16.
 */
public class TestEmotion {

    @Test
    public void testEmotion() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "Today is a good day. The sun is out. There's a gentle breeze.";
        IndicoResult result = test.emotion.predict(example, new HashMap<String, Object>() {{
            put("top_n", 3);
        }});
        Map<Emotion, Double> results = result.getEmotion();
        assertTrue(results.size() == 3);
    }

    @Test
    public void testBatchEmotion() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));
        String example = "Today is a good day. The sun is out. There's a gentle breeze.";

        BatchIndicoResult result = test.emotion.predict(new String[] {example, example});
        List<Map<Emotion, Double>> results = result.getEmotion();
        assertTrue(results.size() == 2);
    }
}
