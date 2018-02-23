package com.example.hp.qr;

import android.graphics.Bitmap;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button,sbutton;
    EditText editText;
    String EditTextValue ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        sbutton = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextValue = editText.getText().toString();

                if(!EditTextValue.isEmpty()) {
                    try
                    {
                        bitmap = TextToImageEncode(EditTextValue);
                        editText.setText(null);
                        imageView.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Enter Something to Generate QR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    Bitmap TextToImageEncode(String Value) throws WriterException
    {

        BitMatrix bitMatrix;
        try
        {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        }
        catch (IllegalArgumentException Illegalargumentexception)
        {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++)
        {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++)
            {
                pixels[offset + x] = bitMatrix.get(x, y) ? getResources().getColor(R.color.QRcolorB):getResources().getColor(R.color.QRcolorW);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
