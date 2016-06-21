package io.indico.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.indico.Indico;
import io.indico.api.results.BatchIndicoResult;
import io.indico.api.utils.IndicoException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 9/3/15.
 */
public class TestOrganizations {

    @Test
    public void testSingle() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example ="A year ago, the New York Times published confidential comments about ISIS' ideology by Major General Michael K. Nagata, then U.S. Special Operations commander in the Middle East.";
        List<Map<String, Object>> results = test.organizations.predict(example).getOrganizations();

        boolean expected = false;
        for (Map<String, Object> obj : results) {
            if (obj.get("text").equals("U.S. Special Operations")) {
                expected = true;
                break;
            }
        }

        assertTrue(expected);
    }

    @Test
    public void testBatch() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        final String example = "A year ago, the New York Times published confidential comments about ISIS' ideology by Major General Michael K. Nagata, then U.S. Special Operations commander in the Middle East.";
        BatchIndicoResult result = test.organizations.predict(new String[] {example, example});
        List<List<Map<String, Object>>> results = result.getOrganizations();
        assertTrue(results.size() == 2);
        boolean expected = false;
        for (Map<String, Object> obj : results.get(0)) {
            if (obj.get("text").equals("U.S. Special Operations")) {
                expected = true;
                break;
            }
        }

        assertTrue(expected);
    }
}
