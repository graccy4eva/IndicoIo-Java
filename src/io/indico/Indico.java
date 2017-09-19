package io.indico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import io.indico.api.*;
import io.indico.api.utils.IndicoException;

public class Indico {
    public TextApi sentiment, sentimentHQ, political, language, textTags,
        keywords, twitterEngagement, intersections, personality, persona,
        people, places, organizations, relevance, textFeatures, emotion, text, summarization;
    public ImageApi fer, facialFeatures, imageFeatures, imageRecognition, contentFiltering, facialLocalization, image;
    public PDFApi pdfExtraction;
    public CustomApiClient custom;

    static public String HOST="indico.io", CLOUD="apiv2", URL_PROTOCOL="https", API_KEY;
    public Indico(String apiKey) throws IndicoException {
        Indico.API_KEY = apiKey;
        this.initializeClients();
    }

    public Indico(String apiKey, String cloud) throws IndicoException {
        Indico.API_KEY = apiKey;
        Indico.CLOUD = cloud;

        this.initializeClients();
    }

    public Indico(File configurationFile) throws IOException, IndicoException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream(configurationFile);
        prop.load(input);

        Indico.API_KEY = prop.getProperty("apiKey");
        Indico.CLOUD = prop.getProperty("cloud", "apiv2");
        Indico.URL_PROTOCOL = prop.getProperty("url_protocol", "https");
        Indico.HOST = prop.getProperty("host", "indico.io");

        this.initializeClients();
    }

    public void createPropertiesFile(String filePath) throws IOException {
        Properties prop = new Properties();
        OutputStream output;

        output = new FileOutputStream(filePath);
        prop.setProperty("apiKey", Indico.API_KEY);
        if (Indico.CLOUD!= null) {
            prop.setProperty("cloud", Indico.CLOUD);
        }
        prop.store(output, null);

        output.close();
    }

    public static void setHost(String HOST) {
        Indico.HOST = HOST;
    }

    public static void setcloud(String CLOUD) {
        Indico.CLOUD = CLOUD;
    }

    public static void setUrlProtocol(String urlProtocol) {
        Indico.URL_PROTOCOL = urlProtocol;
    }

    public static void setApiKey(String apiKey) {
        Indico.API_KEY = apiKey;
    }

    private void initializeClients() throws IndicoException {
        this.sentiment = new TextApi(Api.Sentiment);
        this.sentimentHQ = new TextApi(Api.SentimentHQ);
        this.political = new TextApi(Api.Political);
        this.language = new TextApi(Api.Language);
        this.textTags = new TextApi(Api.TextTags);
        this.text = new TextApi(Api.MultiText);
        this.intersections = new TextApi(Api.Intersections);
        this.keywords = new TextApi(Api.Keywords);
        this.twitterEngagement = new TextApi(Api.TwitterEngagement);
        this.personality = new TextApi(Api.Personality);
        this.persona = new TextApi(Api.Persona);
        this.people = new TextApi(Api.People);
        this.places = new TextApi(Api.Places);
        this.organizations = new TextApi(Api.Organizations);
        this.relevance = new TextApi(Api.Relevance);
        this.emotion = new TextApi(Api.Emotion);
        this.textFeatures = new TextApi(Api.TextFeatures);
        this.pdfExtraction = new PDFApi(Api.PDFExtraction);
        this.summarization = new TextApi(Api.Summarization);

        this.fer = new ImageApi(Api.FER);
        this.facialFeatures = new ImageApi(Api.FacialFeatures);
        this.imageFeatures = new ImageApi(Api.ImageFeatures);
        this.imageRecognition = new ImageApi(Api.ImageRecognition);
        this.contentFiltering = new ImageApi(Api.ContentFiltering);
        this.facialLocalization = new ImageApi(Api.FacialLocalization);
        this.image = new ImageApi(Api.MultiImage);

        this.custom = new CustomApiClient();
    }

}
