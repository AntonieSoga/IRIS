package com.bim.cristalin;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class TextExtractor {

    public static String extractTextFromImage(Context context,Bitmap bitmap) {
        TextRecognizer recognizer = new TextRecognizer.Builder(context).build();
        if (!recognizer.isOperational()) {
            return null;
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> sparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < sparseArray.size(); i++) {
                TextBlock textBlock = sparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
