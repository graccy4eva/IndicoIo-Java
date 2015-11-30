package io.indico.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.indico.api.results.BatchIndicoResult;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.ImageUtils;
import io.indico.api.utils.IndicoException;

/**
 * Created by Chris on 6/26/15.
 */
public class ImageApi extends ApiClient {
    Api api;

    public ImageApi(Api api, String apiKey, String privateCloud) throws IndicoException {
        super(apiKey, privateCloud);
        this.api = api;
    }

    public IndicoResult predict(String filePath, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, ImageUtils.handleString(filePath, api.getSize(params), (Boolean) api.get("minResize")), params);
    }

    public IndicoResult predict(File imageFile, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, ImageUtils.handleFile(imageFile, api.getSize(params), (Boolean) api.get("minResize")), params);
    }

    public BatchIndicoResult predict(List<?> images, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, ImageUtils.convertToImages(images, api.getSize(params), (Boolean) api.get("minResize")), params);
    }

    public BatchIndicoResult predict(String[] images, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(Arrays.asList(images), params);
    }

    public BatchIndicoResult predict(File[] images, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(Arrays.asList(images), params);
    }

    public IndicoResult predict(String filePath)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, ImageUtils.handleString(filePath, (Integer) api.get("size"), (Boolean) api.get("minResize")), null);
    }

    public IndicoResult predict(File imageFile)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, ImageUtils.handleFile(imageFile, (Integer) api.get("size"), (Boolean) api.get("minResize")), null);
    }

    public IndicoResult predict(BufferedImage image)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, ImageUtils.handleImage(image, (Integer) api.get("size"), (Boolean) api.get("minResize")), null);
    }

    public BatchIndicoResult predict(List<?> images)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(ImageUtils.convertToImages(images, (Integer) api.get("size"), (Boolean) api.get("minResize")), null);
    }

    public BatchIndicoResult predict(String[] images)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(Arrays.asList(images), null);
    }

    public BatchIndicoResult predict(File[] images)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(Arrays.asList(images), null);
    }

}
