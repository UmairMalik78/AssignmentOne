package com.umairmaik.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
	TextView questionDescription;
	ArrayList<Question> questionsList;
	RadioGroup radioGroup;
	RadioButton option1;
	RadioButton option2;
	RadioButton option3;
	RadioButton option4;
	TextView txtQuestionNumber;
	TextView quizCategory;
	Button nextQuestionButton;
	ImageView img;
	DBHelper dbHelper=new DBHelper(QuizActivity.this);
	Question currentQuestion;
	int countOfCorrectAnswers=0;
	Boolean[] answersStatusList=new Boolean[10];

	int currentQuestionNumber=0;//will be use as index variable

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		//Used to get full screen view
		this.getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		//Getting quiz category from intent
		Intent intent=getIntent();
		String category=intent.getStringExtra("category");

		//Getting All the elements by their IDs
		questionDescription=findViewById(R.id.txtQuestionDescription);
		option1=findViewById(R.id.txtOption1);
		option2=findViewById(R.id.txtOption2);
		option3=findViewById(R.id.txtOption3);
		option4=findViewById(R.id.txtOption4);
		txtQuestionNumber=findViewById(R.id.txtQuestNum);
		quizCategory=findViewById(R.id.txtQuizCategory);
		nextQuestionButton=findViewById(R.id.nextQuestionButton);
		img=findViewById(R.id.imageView);

		//Creating DBHelper class object which we'll use for the whole sessions.

		//Setting All the layout elements for first time
		setQuizCategory(category);
		setQuestionNumber();
		setQuestionsList();
		setCurrentQuestion(questionsList.get(currentQuestionNumber));//pass first question from list ini as argument.
		// Now all other functions will use this current object by themselves.
		// No need to pass information to each method individually
		setQuestionDescription();//Sets first question
		setOptions();//set 4 possible options

	}
	public void setCurrentQuestion(Question question){
		currentQuestion=question;
	}

	public void setQuestionsList(){
		questionsList=dbHelper.GetQuestions(quizCategory.getText().toString());
	}

	public void setQuizCategory(String categroy){
		quizCategory.setText(categroy);
	}

	public void setQuestionNumber(){
		txtQuestionNumber.setText(String.valueOf(currentQuestionNumber+1));//since starts fro 0, so add 1 to view properly
	}

	public void setQuestionDescription(){
		questionDescription.setText(currentQuestion.getQuestionDescription());
	}

	public void setOptions(){
		option1.setText(currentQuestion.getOption1());
		option2.setText(currentQuestion.getOption2());
		option3.setText(currentQuestion.getOption3());
		option4.setText(currentQuestion.getOption4());
	}

	public void setImg(){
		int res = getResources().getIdentifier(currentQuestion.getImg_path(), "drawable", this.getPackageName());
		img.setImageResource(res);
	}

	public void updateAnswersStatusList(){
		int selectedOptionID=radioGroup.getCheckedRadioButtonId();
		RadioButton selectedRadioBtn=(RadioButton)findViewById(selectedOptionID);
		if(selectedRadioBtn.getText().toString().equals(currentQuestion.getAnswer())){
			countOfCorrectAnswers++;
			answersStatusList[currentQuestionNumber]=true;
		}else{
			answersStatusList[currentQuestionNumber]=false;
		}
	}

	public void SetNextQuestionOnScreen(){
		if(radioGroup.getCheckedRadioButtonId()==-1){
			Toast.makeText(QuizActivity.this,"Please Select at least one option to proceed",Toast.LENGTH_SHORT).show();
			return;
		}
		updateAnswersStatusList();
		setCurrentQuestion(questionsList.get(++currentQuestionNumber));
		updateAllTheElements();
	}
	public void updateAllTheElements(){
		setImg();
		setQuestionDescription();
		setOptions();
		setQuestionNumber();
	}

}