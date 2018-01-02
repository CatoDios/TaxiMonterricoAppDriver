package tmsystem.com.tmsystemdriver.utils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by MD on 08/08/2017.
 */

public class ReproductorAudio {

    private MediaPlayer mMediaPlayer;

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void play(Context c, int rid, boolean continuo) {
        //Para evitar duplicar o triplicar
        if (mMediaPlayer!=null && mMediaPlayer.isPlaying())
        {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }else{
            if (mMediaPlayer!=null){
                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
        }

        mMediaPlayer = MediaPlayer.create(c, rid);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });
        if (continuo==true)
        {

        }

        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

}