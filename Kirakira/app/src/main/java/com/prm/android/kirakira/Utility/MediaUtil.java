package com.prm.android.kirakira.Utility;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.tts.TextToSpeech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Khue DZ on 6/7/2017.
 */
public class MediaUtil {
    private String AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "AudioRecording.3gp";
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private TextToSpeech textToSpeech;

    public MediaUtil(Context context) {
        textToSpeech = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("vi", "VN"));
                }
            }
        });
    }

    public void mediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public int playRecord() {
        mediaPlayer = new MediaPlayer();
        int duration = 0;
        try {
            mediaPlayer.setDataSource(AudioSavePathInDevice);
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        return duration;
    }

    public void stopRecord() {
        if (mediaRecorder != null)
            mediaRecorder.stop();
    }

    public void stopPlayRecord() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaRecorderReady();
            }
        }
    }

    public void startRecord() {
        mediaRecorderReady();
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void textToSpeech(String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }

}
