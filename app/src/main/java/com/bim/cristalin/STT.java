package com.bim.cristalin;

import android.app.Activity;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class STT implements RecognitionListener {
    private static final String MODEL_DIR = "models";
    private static final String MODEL_NAME = "en-us";
    private static final String KEYPHRASE = "hello";
    private Activity mActivity;
    private SpeechRecognizer mRecognizer;

    public STT(Activity activity) {
        mActivity = activity;

        try {
            // Set up the recognizer with the provided assets
            Assets assets = new Assets(activity);
            File assetDir = assets.syncAssets();
            SpeechRecognizerSetup recognizerSetup = SpeechRecognizerSetup.defaultSetup()
                    .setAcousticModel(new File(assetDir, MODEL_DIR + "/" + MODEL_NAME))
                    .setDictionary(new File(assetDir, MODEL_DIR + "/" + MODEL_NAME + "/cmudict-en-us.dict"))
                    .setRawLogDir(assetDir);
            mRecognizer = recognizerSetup.getRecognizer();
            mRecognizer.addKeyphraseSearch(KEYPHRASE, KEYPHRASE);
            mRecognizer.addListener(this);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Failed to set up recognizer: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void startSpeechToText() {
        mRecognizer.startListening(KEYPHRASE);
    }

    public void stopSpeechToText() {
        mRecognizer.stop();
    }

    @Override
    public void onBeginningOfSpeech() {}

    @Override
    public void onEndOfSpeech() {}

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Exception e) {}

    @Override
    public void onTimeout() {}
}
