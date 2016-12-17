package io.indico.api.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Chris on 12/16/16.
 */
public class PDFUtils {
    final private static Pattern base64_regex = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$");

    public static List<String> convertToPDFs(List<?> pdfs) throws IOException {
        List<String> results = new ArrayList<>(pdfs.size());
        for (int i = 0; i < pdfs.size(); i++) {
            results.add(convertToPDF(pdfs.get(i)));
        }
        return results;
    }

    public static String convertToPDF(Object pdf) throws IOException {
        if (pdf instanceof File) {
            return handleFile((File) pdf);
        } else if (pdf instanceof String) {
            return handleString((String) pdf);
        } else {
            throw new IllegalArgumentException(
                    "PDF method only supports lists of Files and lists of Strings"
            );
        }
    }

    public static String handleFile(File file) throws IOException {
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.encodeBase64(bytes);
        return new String(encoded);
    }

    private static String handleString(String stringPDF) throws IOException {
        File file = new File(stringPDF);
        if (file.exists()) {
            return handleFile(file);
        } else {
            try {
                new URL(stringPDF);
                return stringPDF;
            } catch (MalformedURLException malformedURLException) {
                // Check If Base64
                boolean isBase64 = base64_regex.matcher(stringPDF).matches();
                if (!isBase64) {
                    throw new IllegalArgumentException("String must be valid file path, valid base64, or URL");
                }

                return stringPDF;
            }
        }
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("PDF File is too large to load");
        }

        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }

}
