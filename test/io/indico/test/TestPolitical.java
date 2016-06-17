package io.indico.test;

import io.indico.Indico;
import io.indico.api.text.PoliticalClass;
import io.indico.api.utils.IndicoException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 9/3/15.
 */
public class TestPolitical {
    Map<String, Object> params = new HashMap<String, Object>() {{
        put("version", 1);
    }};
    @Test
    public void testPolitical() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));
        assertTrue(test.political.predict("test", params).getPolitical().size() == PoliticalClass.values().length);
    }


    @Test
    public void testBatchPoliticalList() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));
        List<String> examples = new ArrayList<String>();
        examples.add("this is great!");
        examples.add("this is awful!");
        List<Map<PoliticalClass, Double>> results = test.political.predict(examples, params).getPolitical();
        assertTrue(results.size() == 2);
        assertTrue(results.get(0).size() == PoliticalClass.values().length);
    }

    @Test
    public void testBatchPoliticalArray() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));
        List<Map<PoliticalClass, Double>> results = test.political.predict(new String[]{"this is great!", "this is awful!"}, params).getPolitical();
        assertTrue(results.size() == 2);
        assertTrue(results.get(0).size() == PoliticalClass.values().length);
    }

    @Test
    public void testPoliticalV2() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));
        assertTrue(test.political.predict("test").getPolitical().size() == PoliticalClass.values().length);
    }


    @Test
    public void testBatchPoliticalV2List() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));
        List<String> examples = new ArrayList<String>();
        examples.add("this is great!");
        examples.add("this is awful!");
        List<Map<PoliticalClass, Double>> results = test.political.predict(examples).getPolitical();
        assertTrue(results.size() == 2);
        assertTrue(results.get(0).size() == PoliticalClass.values().length);
    }

    @Test
    public void testBatchPoliticalV2Array() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));
        List<Map<PoliticalClass, Double>> results = test.political.predict(new String[]{"this is great!", "this is awful!"}).getPolitical();
        assertTrue(results.size() == 2);
        assertTrue(results.get(0).size() == PoliticalClass.values().length);
    }

}
