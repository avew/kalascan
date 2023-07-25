package io.github.avew.kala.scan;

import com.google.zxing.Result;
import org.apache.pdfbox.jbig2.JBIG2ImageReaderSpi;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.spi.IIORegistry;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class KalaReader {

    public static QrResult read(InputStream is) {
        QrResult.QrResultBuilder resultBuilder = QrResult.builder().error(true).message("ERR-QR-01=QR Code not read");

        PDDocument document = null;
        try {
            IIORegistry registry = IIORegistry.getDefaultInstance();
            registry.registerServiceProvider(new JBIG2ImageReaderSpi());
            registry.registerServiceProvider(new com.github.jaiimageio.jpeg2000.impl.J2KImageReaderSpi());
            document = PDDocument.load(is);
            List<Result> results = PDFUtils.getQRResultsFromDocument(document);
            for (Result result : results) {
                String valueQrCode = result.getText();
                resultBuilder.data(valueQrCode).error(false).message("Success");
            }
        } catch (IOException e) {
            resultBuilder.error(true).message("ERR-QR-03=" + e.getMessage());
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultBuilder.build();

    }
}
