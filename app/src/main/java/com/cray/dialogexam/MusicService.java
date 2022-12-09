package com.cray.dialogexam;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MusicService extends Service {

	MediaPlayer mediaPlayer;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 음악 리소스 값
		String result = intent.getStringExtra("music");

		// 원래 음악이 재생중이면 중지
		if(mediaPlayer!=null && mediaPlayer.isPlaying())
			mediaPlayer.stop();

		// 음악 리소스 설정
		mediaPlayer = MediaPlayer.create(
			this,
			Integer.parseInt(result) );

		// 반복 재생 설정
		mediaPlayer.setLooping(true);

		// 음악을 켜!
		mediaPlayer.start();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {

		// 음악을 꺼!
		mediaPlayer.stop();
		super.onDestroy();
	}
}
