package com.umairmaik.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		TextView quizCategoryText=findViewById(R.id.txtQuizCategory);

		Intent intent=getIntent();
		quizCategoryText.setText(intent.getStringExtra("category"));
		Log.d("alc",intent.getStringExtra("category"));
		DBHelper dbHelper=new DBHelper(this);

		String questionDiscription="Where is Lahore";
		String option1="Pakistan";
		String option2="India";
		String option3="Sirilanka";
		String option4="Bangladesh";
		String answer="Pakistan";

		Question question=new Question(questionDiscription,"Islamic",option1,option2,option3,option4,answer);
		Log.d("option1","On Create");
		dbHelper.addQuestion(question);

	}
}