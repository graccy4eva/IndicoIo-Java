package io.indico.test;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.indico.Indico;
import io.indico.api.custom.CollectionData;
import io.indico.api.custom.IndicoCollection;
import io.indico.api.custom.Permission;
import io.indico.api.utils.IndicoException;

/**
 * Created by Chris on 9/3/15.
 */
public class TestCustomApi extends TestCase {
    String collectionName = "__test_java_clientlib__";
    String altName = "__alt_test_java_clientlib__";
    String test_user_email="contact@indico.io";

    @Override protected void setUp() throws Exception {
        super.setUp();
        Indico test = new Indico(new File("config.properties"));
        try {
            test.custom.getCollection(collectionName).clear();
        } catch (Exception ignored) {}
        try {
            test.custom.getCollection(altName).clear();
        } catch (Exception ignored) {}
        try {
            test.custom.getCollection(collectionName).deregister();
        } catch (Exception ignored) {}
        try {
            test.custom.getCollection(altName).deregister();
        } catch (Exception ignored) {}
    }

    List<CollectionData> textData = new ArrayList<CollectionData>() {{
        add(new CollectionData("input 1", "label 1"));
        add(new CollectionData("input 2", "label 2"));
        add(new CollectionData("input 3", "label 3"));
        add(new CollectionData("input 4", "label 4"));
    }};

    List<CollectionData> imageTestData = new ArrayList<CollectionData>() {{
        add(new CollectionData("https://i.imgur.com/xUX1rvY.png", "dog"));
        add(new CollectionData("https://i.imgur.com/xUX1rvY.png", "dog"));
        add(new CollectionData("https://i.imgur.com/2Q0EWRz.png", "cat"));
        add(new CollectionData("https://i.imgur.com/XhUDCMP.png", "cat"));

    }};



    @SuppressWarnings("unchecked")
    public void testText() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);
        collection.addData(textData);
        collection.train();
        collection.waitUntilReady(1000);
        Map<String, Double> result = (Map<String, Double>) collection.predict(textData.get(0).getData());

        assertTrue(result.get(textData.get(0).getResult()) > result.get(textData.get(1).getResult()));
        assertTrue(result.get(textData.get(0).getResult()) > result.get(textData.get(2).getResult()));
        assertTrue(result.get(textData.get(0).getResult()) > result.get(textData.get(3).getResult()));
    }

    @SuppressWarnings("unchecked")
    public void testImage() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);
        collection.addData(imageTestData);
        collection.train();
        collection.waitUntilReady(1000);
        Map<String, Double> result = (Map<String, Double>) collection.predict(imageTestData.get(0).getData());
        assertTrue(result.get(imageTestData.get(0).getResult()) > result.get(imageTestData.get(2).getResult()));
    }

    @SuppressWarnings("unchecked")
    public void testCollections() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);
        collection.addData(textData);
        collection.train();
        collection.waitUntilReady(1000);
        assertTrue(test.custom.getAllCollections().get(collectionName) != null);
    }

    @SuppressWarnings("unchecked")
    public void testRemoveExample() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);
        collection.addData(textData);
        collection.train();
        collection.waitUntilReady(1000);
        Map<String, Double> result = (Map<String, Double>) collection.predict(textData.get(0).getData());

        assertTrue(result.get(textData.get(0).getResult()) > result.get(textData.get(1).getResult()));
        assertTrue(result.get(textData.get(0).getResult()) > result.get(textData.get(2).getResult()));
        assertTrue(result.get(textData.get(0).getResult()) > result.get(textData.get(3).getResult()));

        collection.removeExamples(new ArrayList<CollectionData>() {{
            add(textData.get(1));
        }});
        collection.train();
        Map<String, Double> result_removed = (Map<String, Double>) collection.predict(textData.get(0).getData());
        assertFalse(result_removed.containsKey(textData.get(1).getResult()));

    }

    @SuppressWarnings("unchecked")
    public void testRegister() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);

        // Test Not Registered && Not Public
        collection.addData(textData);
        collection.train();
        collection.waitUntilReady(1000);
        collection.register();
        Map<String, ?> results = collection.info();
        assertTrue((Boolean) results.get("registered"));
        assertFalse((Boolean) results.get("public"));
        collection.deregister();
        results = collection.info();
        assertFalse((Boolean) results.get("registered"));
        assertFalse((Boolean) results.get("public"));
        collection.clear();
    }

    @SuppressWarnings("unchecked")
    public void testMakePublic() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);

        // Test Not Registered && Not Public
        collection.addData(textData);
        collection.train();
        collection.waitUntilReady(1000);
        collection.register(true);
        Map<String, ?> results = collection.info();
        assertTrue((Boolean) results.get("registered"));
        assertTrue((Boolean) results.get("public"));
        collection.deregister();
        results = collection.info();
        assertFalse((Boolean) results.get("registered"));
        assertFalse((Boolean) results.get("public"));
        collection.clear();
    }

    @SuppressWarnings("unchecked")
    public void testAuthorizedRead() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);

        // Test Not Registered && Not Public
        collection.addData(textData);
        collection.train();
        collection.waitUntilReady(1000);
        collection.register();
        collection.authorize(test_user_email, Permission.READ);
        Map<String, ?> results = collection.info();
        assertTrue(((Map<String, List<String>>) results.get("permissions")).get("read").contains(test_user_email));
        assertFalse(((Map<String, List<String>>) results.get("permissions")).get("write").contains(test_user_email));
        collection.deauthorize(test_user_email);
        results = collection.info();
        assertFalse(((Map<String, List<String>>) results.get("permissions")).get("read").contains(test_user_email));
        assertFalse(((Map<String, List<String>>) results.get("permissions")).get("write").contains(test_user_email));
        collection.clear();
    }

    @SuppressWarnings("unchecked")
    public void testAuthorizedWrite() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);

        // Test Not Registered && Not Public
        collection.addData(textData);
        collection.train();
        collection.waitUntilReady(1000);
        collection.register();
        collection.authorize(test_user_email, Permission.WRITE);
        Map<String, ?> results = collection.info();
        assertFalse(((Map<String, List<String>>) results.get("permissions")).get("read").contains(test_user_email));
        assertTrue(((Map<String, List<String>>) results.get("permissions")).get("write").contains(test_user_email));
        collection.deauthorize(test_user_email);
        results = collection.info();
        assertFalse(((Map<String, List<String>>) results.get("permissions")).get("read").contains(test_user_email));
        assertFalse(((Map<String, List<String>>) results.get("permissions")).get("write").contains(test_user_email));
        collection.clear();
    }

    @SuppressWarnings("unchecked")
    public void testRename() throws IOException, IndicoException, InterruptedException {
        Indico test = new Indico(new File("config.properties"));

        IndicoCollection collection = test.custom.getCollection(collectionName);

        // Test Not Registered && Not Public
        collection.addData(textData);
        collection.train();
        collection.waitUntilReady(1000);
        collection.rename(altName);
        try {
            IndicoCollection oldCollection = test.custom.getCollection(collectionName);
            oldCollection.train();
            assertTrue(false);
        } catch (IndicoException ignored) {}

        assertTrue(collection.name().equals(altName));
        collection.info();
        collection.clear();
    }
}
