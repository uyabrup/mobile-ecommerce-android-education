package com.google.mcommerce.sample.android.chapter11.mediaplay;

import com.google.mcommerce.sample.android.R;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;

public class MediaDemoActivity extends Activity implements OnPreparedListener {
	static final String AUDIO_PATH =
	// "mms://enmms.chinabroadcast.cn/fm91.5";
	"http://streaming103.radionomy.com:80/Radio-Mozart";
	// "rtsp://64.202.98.91:554/sa.sdp"; // http://somafm.com/secretagent/
	// "http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/play.mp3";
	// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
	// + "/music_file.mp3";
	// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
	// + "/movie.mp4";
	private static final String TAG = "MediaDemo";

	private MediaPlayer mediaPlayer;
	private int playbackPosition = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c11_media_demo);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.startPlayerBtn:
			try {
				// Only have one of these play methods uncommented
				playAudio(AUDIO_PATH);
				// playLocalAudio();
				// playLocalAudio_UsingDescriptor();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.pausePlayerBtn:
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				playbackPosition = mediaPlayer.getCurrentPosition();
				mediaPlayer.pause();
			}
			break;
		case R.id.restartPlayerBtn:
			if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
				mediaPlayer.seekTo(playbackPosition);
				mediaPlayer.start();
			}
			break;
		case R.id.stopPlayerBtn:
			if (mediaPlayer != null) {
				mediaPlayer.stop();
				playbackPosition = 0;
			}
			break;
		}
	}

	private void playAudio(String url) throws Exception {
		killMediaPlayer();

		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setDataSource(this, Uri.parse(url));
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
		mediaPlayer.prepareAsync();
	}

	private void playLocalAudio() throws Exception {
		// mediaPlayer = MediaPlayer.create(this,Uri.parse(url));
		mediaPlayer = MediaPlayer.create(this, R.raw.music_file);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.start();
	}

	private void playLocalAudio_UsingDescriptor() throws Exception {

		AssetFileDescriptor fileDesc = getResources().openRawResourceFd(
				R.raw.music_file);
		if (fileDesc != null) {

			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(fileDesc.getFileDescriptor(),
					fileDesc.getStartOffset(), fileDesc.getLength());

			fileDesc.close();

			mediaPlayer.prepare();
			mediaPlayer.start();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		killMediaPlayer();
	}

	private void killMediaPlayer() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onPrepared(MediaPlayer mp) {
		Log.d(TAG, "MediaPlayer is prepared");
		mp.start();
	}
}
