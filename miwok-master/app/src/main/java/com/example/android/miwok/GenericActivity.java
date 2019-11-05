package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public abstract class GenericActivity extends AppCompatActivity {

    protected abstract ArrayList<Word> getWords();

    protected abstract int getBackgroundColorId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        this.mContext = this;
        this.mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> words = getWords();
        // TODO https://developer.android.com/guide/topics/ui/layout/recyclerview.html
        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(new WordAdapter(this, words, getBackgroundColorId()));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            final Word word = words.get(position);
            releaseMediaPlayer();
            int res = this.mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                this.mMediaPlayer = MediaPlayer.create(this.mContext, word.getAudioResourceId());
                this.mMediaPlayer.start();
                this.mMediaPlayer.setOnCompletionListener(mp -> releaseMediaPlayer());
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


    private class MyOnAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {
            // TODO https://medium.com/google-developers/how-to-properly-handle-audio-interruptions-3a13540d18fa#.jkibca8ml
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange ==
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            }
        }
    }


    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private final MyOnAudioFocusChangeListener mOnAudioFocusChangeListener = new MyOnAudioFocusChangeListener();
    // TODO https://www.tutorialspoint.com/android/android_audiomanager
    private AudioManager mAudioManager;


}
