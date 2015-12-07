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
 * Created by Chris on 9/3/15.
 */
public class TestImageFeaturesV3 {
    Map<String, Object> params = new HashMap<String, Object>() {{
        put("version", 3);
    }};
    @Test
    public void testImageFeatures() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        List<Double> results = test.imageFeatures.predict("bin/lena.png", params).getImageFeatures();
        assertTrue(results.size() == 4096);
    }

    @Test
    public void testBatchImageFeatures() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        List<String> lenas = new ArrayList<String>();
        lenas.add("bin/lena.png");
        lenas.add("bin/lena.png");

        List<List<Double>> results = test.imageFeatures.predict(lenas, params).getImageFeatures();
        assertTrue(results.size() == 2);
        assertTrue(results.get(0).equals(results.get(1)));
    }
}
