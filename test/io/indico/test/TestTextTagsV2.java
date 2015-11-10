package io.indico.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import io.indico.Indico;
import io.indico.api.text.TextTag;
import io.indico.api.utils.IndicoException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 11/10/15.
 */
public class TestTextTagsV2 {
    @Test
    public void testTextTags() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        assertTrue(test.textTags.predict("test", new HashMap<String, Object>() {{
            put("version", 2);
        }}).getTextTags().size() == TextTag.values().length);
    }
}
