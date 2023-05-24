package com.impllife.split.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.impllife.split.R;
import com.impllife.split.ui.custom.component.NavFragment;

public class DiscountFragment extends NavFragment {
    public DiscountFragment() {
        super(R.layout.fragment_discount, "Discount");
    }

    @Override
    protected void init() {
        ImageView img = findViewById(R.id.img);
        runAsync(() -> {
            String data = " ";
            post(() -> {
                img.setImageBitmap(createQRImage(data, 1024));
            });
        });
    }

    private Bitmap createQRImage(String qrCodeText, int size) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            Log.i("DiscountFragment", "qr code generate complete");
            return bmp;
        } catch (WriterException e) {
            Log.e("DiscountFragment", "QR code", e);
            throw new IllegalArgumentException(e);
        }
    }
}