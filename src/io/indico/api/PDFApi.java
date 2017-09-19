package io.indico.api;

import io.indico.api.results.BatchIndicoResult;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;
import io.indico.api.utils.PDFUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 6/26/15.
 */
public class PDFApi extends ApiClient {
    Api api;

    public PDFApi(Api api) throws IndicoException {
        super();
        this.api = api;
    }

    public IndicoResult predict(String filePath, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, PDFUtils.convertToPDF(filePath), params);
    }

    public IndicoResult predict(File pdfFile, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, PDFUtils.handleFile(pdfFile), params);
    }

    public BatchIndicoResult predict(List<?> pdfs, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, PDFUtils.convertToPDFs(pdfs), params);
    }

    public BatchIndicoResult predict(String[] pdfs, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(Arrays.asList(pdfs), params);
    }

    public BatchIndicoResult predict(File[] pdfs, Map<String, Object> params)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(Arrays.asList(pdfs), params);
    }

    public IndicoResult predict(String filePath)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, PDFUtils.convertToPDF(filePath), null);
    }

    public IndicoResult predict(File pdfFile)
        throws UnsupportedOperationException, IOException, IndicoException {
        return call(api, PDFUtils.convertToPDF(pdfFile), null);
    }

    public BatchIndicoResult predict(List<?> pdfs)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(PDFUtils.convertToPDFs(pdfs), null);
    }

    public BatchIndicoResult predict(String[] pdfs)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(Arrays.asList(pdfs), null);
    }

    public BatchIndicoResult predict(File[] pdfs)
        throws UnsupportedOperationException, IOException, IndicoException {
        return predict(Arrays.asList(pdfs), null);
    }

}
