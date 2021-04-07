package com.umairmaik.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class MainActivity extends AppCompatActivity {
	MediaPlayer mediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mediaPlayer=MediaPlayer.create(this,R.raw.click);
		mediaPlayer.setOnCompletionListener(new  MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mediaPlayer.release();
			}
		});
	}
	public void onClickSound(){
		mediaPlayer.start();
	}
	public void MoveToCategorySelectionActivity(View view){
		onClickSound();
		Intent intent=new Intent(this,CategorySelectionActivity.class);
		startActivity(intent);
	}
}