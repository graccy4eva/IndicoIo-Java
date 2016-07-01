package io.indico.api.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.indico.api.Api;
import io.indico.api.CustomApiClient;
import io.indico.api.utils.ImageUtils;
import io.indico.api.utils.IndicoException;

/**
 * Created by Chris on 11/30/15.
 */
public class IndicoCollection {
    CustomApiClient client;
    Map<String, Object> configs;

    public IndicoCollection(CustomApiClient client, final String collectionName, final String domain, final boolean shared) {
        this.client = client;
        configs = new HashMap<String, Object>() {{
            put("collection", collectionName);
            if (domain != null)
                put("domain", domain);
            put("shared", shared);
        }};
    }

    public IndicoCollection(CustomApiClient client, final String collectionName, final String domain) {
        this(client, collectionName, domain, false);
    }

    public String addData(List<CollectionData> examples) throws IOException, IndicoException {
        List<String[]> postPackage = new ArrayList<>();
        for (CollectionData data : examples) {
            postPackage.add(new String[] {data.data, data.result.toString()});
        }
        return this.client.addData(postPackage, this.configs);
    }

    public String train() throws IOException, IndicoException {
        return this.client.train(this.configs);
    }

    public String clear() throws IOException, IndicoException {
        return this.client.clear(this.configs);
    }

    public String register() throws IOException, IndicoException {
        return this.register(false);
    }

    public String register(boolean makePublic) throws IOException, IndicoException {
        Map<String, Object> data = new HashMap<>();
        data.putAll(this.configs);
        data.put("make_public", makePublic);
        return this.client.register(data);
    }

    public String deregister() throws IOException, IndicoException {
        return this.client.deregister(this.configs);
    }

    public String authorize(String email, Permission permission) throws IOException, IndicoException {
        Map<String, Object> data = new HashMap<>();
        data.putAll(this.configs);
        data.put("email", email);
        data.put("permission_type", permission.toString());
        return this.client.authorize(data);
    }

    public String deauthorize(String email) throws IOException, IndicoException {
        Map<String, Object> data = new HashMap<>();
        data.putAll(this.configs);
        data.put("email", email);
        return this.client.deauthorize(data);
    }

    public String rename(String newName) throws IOException, IndicoException {
        Map<String, Object> data = new HashMap<>();
        data.putAll(this.configs);
        data.put("name", newName);
        String result = this.client.rename(data);
        this.configs.put("collection", newName);
        return result;
    }

    public String removeExamples(List<CollectionData> data) throws IOException, IndicoException {
        List<String> postData = new ArrayList<>();
        for (CollectionData entry : data) {
            postData.add(entry.data);
        }

        return this.client.removeExamples(postData, this.configs);
    }

    public Map<String, ?> info() throws IOException, IndicoException {
        return this.client.info(this.configs);
    }

    public void waitUntilReady(long interval) throws IOException, IndicoException {
        String status;
        while (true) {
            status = this.info().get("status").toString();
            if (Objects.equals(status, "ready"))
                return;
            if (!Objects.equals(status, "training"))
                throw new IndicoException("Collection training has failed with status " + status);
            try {
                Thread.sleep(interval);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public String name() {
        return configs.get("collection").toString();
    }

    public List<?> predict(List<String> data) throws IOException, IndicoException {
        List<String> postData = ImageUtils.convertToImages(data, Api.CUSTOM.getSize(null), (boolean) Api.CUSTOM.get("minResize"));
        return this.client.predict(postData, this.configs);
    }

    public Object predict(String data) throws IOException, IndicoException {
        String postData = ImageUtils.convertToImage(data, Api.CUSTOM.getSize(null), (boolean) Api.CUSTOM.get("minResize"));
        return this.client.predict(postData, this.configs);
    }
}
