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

    public IndicoCollection getCollection(String collectionName, String domain) {
        return new IndicoCollection(this, collectionName, domain);
    }

    public IndicoCollection getCollection(String collectionName) {
        return getCollection(collectionName, null);
    }

    public IndicoCollection newCollection(String collectionName, String domain) {
        return getCollection(collectionName, domain);
    }

    public IndicoCollection newCollection(String collectionName) {
        return getCollection(collectionName, null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Map<String, ?>> getAllCollections() throws IOException, IndicoException {
        return (Map<String, Map<String, ?>>) customResult(baseCall(api, null, false, "collections", null), "getAllCollections");
    }

    public String addData(final String collectionName, List<String[]> data, String domain) throws IOException, IndicoException {
        return customCall(collectionName, "add_data", data, true, domain).toString();
    }

    public String train(final String collectionName) throws IOException, IndicoException {
        return customCall(collectionName, "train", null, false, null).toString();
    }

    public String clear(final String collectionName) throws IOException, IndicoException {
        return customCall(collectionName, "clear_collection", null, false, null).toString();
    }

    @SuppressWarnings("unchecked")
    public String removeExamples(final String collectionName, List<String> data) throws IOException, IndicoException {
        return customCall(collectionName, "remove_example", data, true, null).toString();
    }

    @SuppressWarnings("unchecked")
    public Map<String, ?> info(String collectionName) throws IOException, IndicoException {
        Map<String, ?> collection = getAllCollections().get(collectionName);
        if (collection == null) {
            throw new IndicoException("Collection " + collectionName + " does not exist");
        }
        return collection;
    }

    public List<?> predict(final String collectionName, List<String> data, String domain) throws IOException, IndicoException {
        return (List<?>) customCall(collectionName, "predict", data, true, domain);

    }

    public Object predict(final String collectionName, String data, String domain) throws IOException, IndicoException {
        return customCall(collectionName, "predict", data, false, domain);
    }

    private Object customCall(final String collectionName, String method, Object data, boolean batch, final String domain) throws IOException, IndicoException {
        return customResult(baseCall(api, data, batch, method, new HashMap<String, Object>() {{
            put("collection", collectionName);
            if (domain != null)
                put("domain", domain);
        }}), method);
    }

    private Object customResult(Map<String, ?> response, String method) throws IndicoException {
        if (!response.containsKey("results")) {
            throw new IndicoException(api + " " + method + " failed with error: " +
                (response.containsKey("error") ? response.get("error") : "unexpected error")
            );
        }

        return response.get("results");
    }
}
