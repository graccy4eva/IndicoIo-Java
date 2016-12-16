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
public class TestSummarization {
    Map<String, Object> params = new HashMap<String, Object>() {{
        put("top_n", 5);
    }};
    @Test
    public void testSummarization() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "The origin of bourbon is not well documented. There are many conflicting legends and claims, some more credible than others. For example, the invention of bourbon is often attributed to Elijah Craig, a Baptist minister and distiller credited with many Kentucky firsts (e.g., fulling mill, paper mill, ropewalk) who is also said to have been the first to age the product in charred oak casks, a process which gives bourbon its reddish color and distinctive taste.[5] Across the county line in Bourbon County, an early distiller named Jacob Spears is credited with being the first to label his product as Bourbon whiskey. Spears\\' home, Stone Castle, warehouse and spring house survive; one can drive by the Spears\\' home on Clay-Kiser Road.\\nAlthough still popular and often repeated, the Craig legend is apocryphal. Similarly, the Spears story is a local favorite, rarely repeated outside the county. There likely was no single \"inventor\" of bourbon, which developed into its present form only in the late 19th century. Essentially any type of grain can be used to make whiskey, and the practice of aging whiskey and charring the barrels for better flavor had also been known in Europe for centuries.[6] The late date of the Bourbon County etymology has led Louisville historian Michael Veach to dispute its authenticity. He proposes that the whiskey was named after Bourbon Street in New Orleans, a major port where shipments of Kentucky whiskey sold well as a cheaper alternative to French cognac.[1]";
        IndicoResult result = test.summarization.predict(example, params);
        List<String> results = result.getSummarization();
        assertTrue(results.size() == 5);
    }

    @Test
    public void testBatchSummarization() throws IOException, IndicoException {
        Indico test = new Indico(new File("config.properties"));

        String example = "The origin of bourbon is not well documented. There are many conflicting legends and claims, some more credible than others. For example, the invention of bourbon is often attributed to Elijah Craig, a Baptist minister and distiller credited with many Kentucky firsts (e.g., fulling mill, paper mill, ropewalk) who is also said to have been the first to age the product in charred oak casks, a process which gives bourbon its reddish color and distinctive taste.[5] Across the county line in Bourbon County, an early distiller named Jacob Spears is credited with being the first to label his product as Bourbon whiskey. Spears\\' home, Stone Castle, warehouse and spring house survive; one can drive by the Spears\\' home on Clay-Kiser Road.\\nAlthough still popular and often repeated, the Craig legend is apocryphal. Similarly, the Spears story is a local favorite, rarely repeated outside the county. There likely was no single \"inventor\" of bourbon, which developed into its present form only in the late 19th century. Essentially any type of grain can be used to make whiskey, and the practice of aging whiskey and charring the barrels for better flavor had also been known in Europe for centuries.[6] The late date of the Bourbon County etymology has led Louisville historian Michael Veach to dispute its authenticity. He proposes that the whiskey was named after Bourbon Street in New Orleans, a major port where shipments of Kentucky whiskey sold well as a cheaper alternative to French cognac.[1]";
        BatchIndicoResult result = test.summarization.predict(new String[] {example, example}, params);
        List<List<String>> results = result.getSummarization();
        assertTrue(results.size() == 2);
        assertTrue(results.get(0).size() == 5);
    }
}
