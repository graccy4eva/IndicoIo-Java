package io.indico.api.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.indico.api.Api;
import io.indico.api.CustomApiClient;
import io.indico.api.utils.ImageUtils;
import io.indico.api.utils.IndicoException;

/**
 * Created by Chris on 11/30/15.
 */
public class IndicoCollection {
    CustomApiClient client;
    String collectionName;

    public IndicoCollection(CustomApiClient client, String collectionName) {
        this.collectionName = collectionName;
        this.client = client;
    }

    public String addData(List<CollectionData> examples) throws IOException, IndicoException {
        List<String[]> postPackage = new ArrayList<>();
        for (CollectionData data : examples) {
            postPackage.add(new String[] {data.data, data.result.toString()});
        }
        return this.client.addData(this.collectionName, postPackage);
    }

    public String train() throws IOException, IndicoException {
        return this.client.train(this.collectionName);
    }

    public String clear() throws IOException, IndicoException {
        return this.client.clear(this.collectionName);
    }

    public String removeExamples(List<CollectionData> data) throws IOException, IndicoException {
        List<String> postData = new ArrayList<>();
        for (CollectionData entry : data) {
            postData.add(entry.data);
        }

        return this.client.removeExamples(this.collectionName, postData);
    }

    public Map<String, ?> info() throws IOException, IndicoException {
        return this.client.info(this.collectionName);
    }

    public void waitUntilReady(long interval) throws IOException, IndicoException {
        while (!Objects.equals(this.client.info(this.collectionName).get("status").toString(), "ready")) {
            try {
                Thread.sleep(interval);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<?> predict(List<String> data) throws IOException, IndicoException {
        List<String> postData = ImageUtils.convertToImages(data, Api.CUSTOM.getSize(null), (boolean) Api.CUSTOM.get("minResize"));
        return this.client.predict(this.collectionName, postData);
    }

    public Object predict(String data) throws IOException, IndicoException {
        String postData = ImageUtils.convertToImage(data, Api.CUSTOM.getSize(null), (boolean) Api.CUSTOM.get("minResize"));
        return this.client.predict(this.collectionName, postData);
    }
}
