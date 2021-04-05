package com.umairmaik.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategorySelectionActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_selection);
	}

	public void MoveToQuizActivity(View view){
		String category=((Button)view).getText().toString();
		Intent intent=new Intent(this,QuizActivity.class);
		intent.putExtra("category",category);
		startActivity(intent);
	}
}