package io.github.avew.kala.scan;

import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFUtils {

    public static List<Result> getQRResultsFromDocument(PDDocument document) throws IOException {
        List<Result> results = new ArrayList<>();
        List<BufferedImage> images = getImagesFromDocument(document);
        for (BufferedImage image : images) {
            try {
                results.add(QRDecoder.decodeBufferedImage(image));
            } catch (ReaderException ignored) {
            }
        }

        return results;
    }

    public static List<BufferedImage> getImagesFromDocument(PDDocument document) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        for (PDPage page : document.getPages()) {
            images.addAll(getImagesFromPage(page));
        }
        return images;
    }

    public static List<BufferedImage> getImagesFromPage(PDPage page) throws IOException {
        return new ArrayList<>(getImagesFromResources(page.getResources()));
    }

    private static List<BufferedImage> getImagesFromResources(PDResources resources) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        for (COSName xObjectName : resources.getXObjectNames()) {
            PDXObject xObject = resources.getXObject(xObjectName);

            if (xObject instanceof PDFormXObject) {
                images.addAll(getImagesFromResources(((PDFormXObject) xObject).getResources()));
            } else if (xObject instanceof PDImageXObject) {
                images.add(((PDImageXObject) xObject).getImage());
            }
        }
        return images;
    }
}
