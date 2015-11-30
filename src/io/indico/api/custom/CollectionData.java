package io.indico.api.custom;

import java.awt.image.BufferedImage;
import java.io.IOException;

import io.indico.api.Api;
import io.indico.api.utils.ImageUtils;

/**
 * Created by Chris on 11/30/15.
 */
public class CollectionData {
    ModelType type;
    String data;
    Object result;

    public CollectionData(String data, String classification) {
        this.data = data;
        this.type = ModelType.CLASSIFICATION;
        this.result = classification;
    }

    public CollectionData(String data, float score) {
        this.data = data;
        this.type = ModelType.REGRESSION;
        this.result = score;
    }

    public CollectionData(BufferedImage data, float score) throws IOException {
        this.data = ImageUtils.handleImage(data, Api.CUSTOM.getSize(null), (boolean) Api.CUSTOM.get("minResize"));
        this.type = ModelType.REGRESSION;
        this.result = score;
    }

    public CollectionData(BufferedImage data, String classification) throws IOException {
        this.data = ImageUtils.handleImage(data, Api.CUSTOM.getSize(null), (boolean) Api.CUSTOM.get("minResize"));
        this.type = ModelType.CLASSIFICATION;
        this.result = classification;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setScore(float score) {
        this.result = score;
    }

    public void setClass(String classification) {
        this.result = classification;
    }

    public String getData() {
        return data;
    }

    public Object getResult() {
        return result;
    }
}
