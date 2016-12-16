package io.indico.test;

import io.indico.Indico;
import io.indico.api.results.BatchIndicoResult;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 9/3/15.
 */
public class TestPDFExtraction {
    Map<String, Object> params = new HashMap<String, Object>() {{
        put("images", true);
        put("tables", true);
    }};
    @Test
    public void testPDFExtraction() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "bin/test.pdf";
        IndicoResult result = test.pdfExtraction.predict(example, params);
        Map<String, List<Object>> results = result.getPDFExtraction();

        assertTrue(results.containsKey("metadata"));
        assertTrue(results.containsKey("text"));
        assertTrue(results.containsKey("images"));
        assertTrue(results.containsKey("tables"));
    }

    @Test
    public void testURLPDFExtraction() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "https://papers.nips.cc/paper/4824-imagenet-classification-with-deep-convolutional-neural-networks.pdf";
        IndicoResult result = test.pdfExtraction.predict(example, params);
        Map<String, List<Object>> results = result.getPDFExtraction();

        assertTrue(results.containsKey("metadata"));
        assertTrue(results.containsKey("text"));
        assertTrue(results.containsKey("images"));
        assertTrue(results.containsKey("tables"));
    }

    @Test
    public void testBatchPDFExtraction() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        final String example = "bin/test.pdf";
        BatchIndicoResult result = test.pdfExtraction.predict(new String[] {example, example}, params);
        List<Map<String, List<Object>>> results = result.getPDFExtraction();
        assertTrue(results.size() == 2);

        assertTrue(results.get(0).containsKey("images"));
        assertTrue(results.get(0).containsKey("metadata"));
        assertTrue(results.get(0).containsKey("text"));
        assertTrue(results.get(0).containsKey("tables"));
    }
}
