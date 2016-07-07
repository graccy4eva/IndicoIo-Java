package io.indico.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.indico.api.custom.IndicoCollection;
import io.indico.api.utils.IndicoException;

/**
 * Created by Chris on 11/30/15.
 */
public class CustomApiClient extends ApiClient {
    Api api;

    public CustomApiClient(String apiKey, String privateCloud) throws IndicoException {
        super(apiKey, privateCloud);
        this.api = Api.CUSTOM;
    }

    public IndicoCollection getCollection(String collectionName, String domain, boolean shared) {
        return new IndicoCollection(this, collectionName, domain, shared);
    }

    public IndicoCollection getCollection(String collectionName, String domain) {
        return getCollection(collectionName, domain, false);
    }

    public IndicoCollection getCollection(String collectionName, boolean shared) {
        return getCollection(collectionName, null, shared);
    }

    public IndicoCollection getCollection(String collectionName) {
        return getCollection(collectionName, null, false);
    }

    public IndicoCollection newCollection(String collectionName, String domain, boolean shared) {
        return getCollection(collectionName, domain, shared);
    }

    public IndicoCollection newCollection(String collectionName, String domain) {
        return getCollection(collectionName, domain, false);
    }

    public IndicoCollection newCollection(String collectionName, boolean shared) {
        return getCollection(collectionName, null, shared);
    }

    public IndicoCollection newCollection(String collectionName) {
        return getCollection(collectionName, null, false);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Map<String, ?>> getAllCollections() throws IOException, IndicoException {
        return (Map<String, Map<String, ?>>) customCall("collections", null, false, null);
    }

    public String addData(List<String[]> data, Map<String, Object> config) throws IOException, IndicoException {
        return customCall("add_data", data, true, config).toString();
    }

    public String train(Map<String, Object> config) throws IOException, IndicoException {
        return customCall( "train", null, false, config).toString();
    }

    public String clear(Map<String, Object> config) throws IOException, IndicoException {
        return customCall("clear_collection", null, false, config).toString();
    }

    @SuppressWarnings("unchecked")
    public String removeExamples(List<String> data, Map<String, Object> config) throws IOException, IndicoException {
        return customCall("remove_example", data, true, config).toString();
    }

    @SuppressWarnings("unchecked")
    public String register(Map<String, Object> config) throws IOException, IndicoException {
        return customCall("register", null, true, config).toString();
    }

    @SuppressWarnings("unchecked")
    public String deregister(Map<String, Object> config) throws IOException, IndicoException {
        return customCall("deregister", null, true, config).toString();
    }

    @SuppressWarnings("unchecked")
    public String authorize(Map<String, Object> config) throws IOException, IndicoException {
        return customCall("authorize", null, true, config).toString();
    }

    @SuppressWarnings("unchecked")
    public String deauthorize(Map<String, Object> config) throws IOException, IndicoException {
        return customCall("deauthorize", null, true, config).toString();
    }

    @SuppressWarnings("")
    public String rename(Map<String, Object> config) throws IOException, IndicoException {
        return customCall("rename", null, true, config).toString();
    }

    @SuppressWarnings("unchecked")
    public Map<String, ?> info(Map<String, Object> config) throws IOException, IndicoException {
        return (Map<String, ?>) customCall("info", null, false, config);
    }

    public List<?> predict(List<String> data, Map<String, Object> config) throws IOException, IndicoException {
        return (List<?>) customCall("predict", data, true, config);

    }

    public Object predict(String data, Map<String, Object> config) throws IOException, IndicoException {
        return customCall("predict", data, false, config);
    }

    private Object customCall(String method, Object data, boolean batch, final Map<String, Object> config) throws IOException, IndicoException {
        Map<String, ?> response = baseCall(api, data, batch, method, config);
        if (!response.containsKey("results")) {
            throw new IndicoException(api + " " + method + " failed with error: " +
                (response.containsKey("error") ? response.get("error") : "unexpected error")
            );
        }

        return response.get("results");
    }
}
