package io.indico.api.results;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.indico.api.Api;
import io.indico.api.image.FacialEmotion;
import io.indico.api.text.Category;
import io.indico.api.text.Emotion;
import io.indico.api.text.Language;
import io.indico.api.text.Persona;
import io.indico.api.text.Personality;
import io.indico.api.text.PoliticalClass;
import io.indico.api.text.TextTag;
import io.indico.api.utils.EnumParser;
import io.indico.api.utils.ImageUtils;
import io.indico.api.utils.IndicoException;

/**
 * Created by Chris on 6/23/15.
 */
public class BatchIndicoResult {
    Map<Api, Object> results;

    @SuppressWarnings("unchecked")
    public BatchIndicoResult(Api api, Map<String, ?> response) throws IndicoException {
        this.results = new HashMap<>();
        if (!response.containsKey("results")) {
            throw new IndicoException(api + " failed with error " +
                (response.containsKey("error") ? response.get("error") : "unexpected error")
            );
        }

        if (api != Api.MultiImage && api != Api.MultiText) {
            results.put(api, response.get("results"));
        } else {
            Map<String, ?> responses = (Map<String, ?>) response.get("results");
            for (Api res : Api.values()) {
                if (!responses.containsKey(res.toString()))
                    continue;
                Map<String, ?> apiResponse = (Map<String, ?>) responses.get(res.toString());
                if (apiResponse.containsKey("error"))
                    throw new IndicoException(res + " failed with error " + apiResponse.get("error"));
                results.put(res, apiResponse.get("results"));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Double> getSentiment() throws IndicoException {
        return (List<Double>) get(Api.Sentiment);
    }

    @SuppressWarnings("unchecked")
    public List<Double> getSentimentHQ() throws IndicoException {
        return (List<Double>) get(Api.SentimentHQ);
    }

    @SuppressWarnings("unchecked")
    public List<Map<PoliticalClass, Double>> getPolitical() throws IndicoException {
        return EnumParser.parse(PoliticalClass.class, ((List<Map<String, Double>>) get(Api.Political)));
    }

    @SuppressWarnings("unchecked")
    public List<Map<Language, Double>> getLanguage() throws IndicoException {
        return EnumParser.parse(Language.class, ((List<Map<String, Double>>) get(Api.Language)));
    }

    @SuppressWarnings("unchecked")
    public List<Map<Emotion, Double>> getEmotion() throws IndicoException {
        return EnumParser.parse(Emotion.class, ((List<Map<String, Double>>) get(Api.Emotion)));
    }

    @SuppressWarnings("unchecked")
    public List<Map<TextTag, Double>> getTextTags() throws IndicoException {
        return EnumParser.parse(TextTag.class, ((List<Map<String, Double>>) get(Api.TextTags)));
    }

    @SuppressWarnings("unchecked")
    public List<Map<FacialEmotion, Double>> getFer() throws IndicoException {
        return EnumParser.parse(FacialEmotion.class, ((List<Map<String, Double>>) get(Api.FER)));
    }

    @SuppressWarnings("unchecked")
    public List<Map<Rectangle, Map<FacialEmotion, Double>>> getLocalizedFer() throws IndicoException {
        List<Map<Rectangle, Map<FacialEmotion, Double>>> ret = new ArrayList<>();

        try {
            List<List<Map<String, Object>>> result = (List<List<Map<String, Object>>>) get(Api.FER);
            for (List<Map<String, Object>> res : result) {
                Map<Rectangle, Map<FacialEmotion, Double>> parsed = new HashMap<>();
                for (Map<String, Object> each : res) {
                    parsed.put(ImageUtils.getRectangle(
                            (Map<String, List<Double>>) each.get("location")),
                        EnumParser.parse(FacialEmotion.class, (Map<String, Double>) each.get("emotions"))
                    );
                }
                ret.add(parsed);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    public List<List<Double>> getImageFeatures() throws IndicoException {
        return (List<List<Double>>) get(Api.ImageFeatures);
    }

    @SuppressWarnings("unchecked")
    public List<List<Double>> getFacialFeatures() throws IndicoException {
        return (List<List<Double>>) get(Api.FacialFeatures);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Double>> getKeywords() throws IndicoException {
        return (List<Map<String, Double>>) get(Api.Keywords);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Double>> getImageRecognition() throws IndicoException {
        return (List<Map<String, Double>>) get(Api.ImageRecognition);
    }

    @SuppressWarnings("unchecked")
    public List<Double> getContentFiltering() throws IndicoException {
        return (List<Double>) get(Api.ContentFiltering);
    }

    @SuppressWarnings("unchecked")
    public List<Double> getTwitterEngagement() throws IndicoException {
        return (List<Double>) get(Api.TwitterEngagement);
    }

    @SuppressWarnings("unchecked")
    public List<List<Rectangle>> getFacialLocalization() throws IndicoException {
        List<List<Rectangle>> images = new ArrayList<>();

        for (List<Map<String, List<Double>>> each : (List<List<Map<String, List<Double>>>>) get(Api.FacialLocalization)) {
            List<Rectangle> rectangles = new ArrayList<>();
            for (Map<String, List<Double>> face : each) {
                rectangles.add(ImageUtils.getRectangle(face));
            }
            images.add(rectangles);
        }

        return images;
    }

    @SuppressWarnings("unchecked")
    public List<Map<Personality, Double>> getPersonality() throws IndicoException {
        return EnumParser.parse(Personality.class, (List<Map<String, Double>>) get(Api.Personality));
    }

    @SuppressWarnings("unchecked")
    public List<Map<Persona, Double>> getPersona() throws IndicoException {
        return EnumParser.parse(Persona.class, (List<Map<String, Double>>) get(Api.Persona));
    }

    @SuppressWarnings("unchecked")
    public List<List<Map<String, Object>>> getPeople() throws IndicoException {
        return (List<List<Map<String, Object>>>) get(Api.People);
    }

    @SuppressWarnings("unchecked")
    public List<List<Map<String, Object>>> getPlaces() throws IndicoException {
        return (List<List<Map<String, Object>>>) get(Api.Places);
    }

    @SuppressWarnings("unchecked")
    public List<List<Map<String, Object>>> getOrganizations() throws IndicoException {
        return (List<List<Map<String, Object>>>) get(Api.Organizations);
    }

    @SuppressWarnings("unchecked")
    public List<List<Double>> getRelevance() throws IndicoException {
        return (List<List<Double>>) get(Api.Relevance);
    }

    @SuppressWarnings("unchecked")
    public List<List<Double>> getTextFeatures() throws IndicoException {
        return (List<List<Double>>) get(Api.TextFeatures);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Map<String, Map<String, Double>>> getIntersections() throws IndicoException {
        return (Map<String, Map<String, Map<String, Double>>>) get(Api.Intersections);
    }

    private Object get(Api api) throws IndicoException {
        if (!results.containsKey(api))
            throw new IndicoException(api.toString() + " was not included in the request");
        return results.get(api);
    }
}

