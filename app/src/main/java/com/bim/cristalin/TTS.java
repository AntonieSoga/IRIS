package com.bim.cristalin;

import android.graphics.Bitmap;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;


public class TTS {
    int ok;
    // Initialize Variable for all the Button ID's you created in the xml file
    private Button TTS;
    // Object for TextToSpeech Engine that will convert the Text into the Audio File
    private TextToSpeech textToSpeech;
    private String s;
    private Bitmap frame;
    public TTS(MainActivity activity, String str) {
        s = "";
        textToSpeech = new TextToSpeech(activity.getApplicationContext(), i -> {
            // Called to signal the completion of the TextToSpeech engine initialization.
            if (i == TextToSpeech.SUCCESS) {
                // Select Language
                int lang = textToSpeech.setLanguage(Locale.ENGLISH);
            }
            speak(str);
        });
    }

    public void click() {
        TTS.performClick();
    }

    public TTS(MainActivity activity) {
        ok = 0;

        // Assign Variables declared above to each and every Button by using findViewById...
        // ... to find the ID of the buttons from the xml file so that it links to the Java file...
        // ...and we can assign any task that the particular tool will perform through the Java file

       //to-do
        //TTS = activity.findViewById(R.id.tts);


        // Creating a new Object for textToSpeech
        // getAppliationContext() is to Return the context of the single, global Application object of the current process.
        // OnInitListener() is nothing but an Interface definition of a callback to be invoked, indicating the completion of the TextToSpeech engine initialization.
        textToSpeech = new TextToSpeech(activity.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                // Called to signal the completion of the TextToSpeech engine initialization.
                if (i == TextToSpeech.SUCCESS) {
                    // Select Language
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        // Setting the button (button variable ID of the Java file) as a clickable item
        // View.OnClickListener() is an Interface definition for a callback to be invoked, when a view is clicked.
        TTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
    public void setString(String s) {
        this.s = s;
    }
    public void speak(String str) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "end of utterance");
        textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, params);
// Add a break after each sentence
        String utteranceId = this.hashCode() + "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.playSilentUtterance(750, TextToSpeech.QUEUE_ADD, utteranceId);
        }
    }

    public String getString() {
        return s;
    }

    public void stop() {
        textToSpeech.stop();
    }

    public void release() throws IOException {
        // Release the TextToSpeech engine resources
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
