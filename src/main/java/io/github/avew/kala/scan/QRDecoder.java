package io.github.avew.kala.scan;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.awt.image.BufferedImage;

public class QRDecoder {
	public static Result decodeBufferedImage(BufferedImage bufferedImage) throws NotFoundException, ChecksumException, FormatException {
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader qrCodeReader = new QRCodeReader();
		return qrCodeReader.decode(bitmap);
	}
}
