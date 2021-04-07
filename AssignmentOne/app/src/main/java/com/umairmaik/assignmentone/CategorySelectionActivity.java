package com.umairmaik.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategorySelectionActivity extends AppCompatActivity {
	MediaPlayer btnClickMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_selection);
		btnClickMediaPlayer=MediaPlayer.create(CategorySelectionActivity.this,R.raw.click);
		btnClickMediaPlayer.setOnCompletionListener(new  MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				btnClickMediaPlayer.release();
			}
		});
	}

	public void MoveToQuizActivity(View view){
		onClickSound();
		String category=((Button)view).getText().toString();
		Intent intent=new Intent(this,QuizActivity.class);
		intent.putExtra("category",category);
		startActivity(intent);
	}

	public void onClickSound(){
		btnClickMediaPlayer.start();
	}
}