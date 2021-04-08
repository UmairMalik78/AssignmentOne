package com.umairmaik.assignmentone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
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
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {
	private static final long COUNTDOWN_IN_MILLIS=20000;
	private ColorStateList textColorDefaultRb;
	private ColorStateList textColorDefaultCd;
	private CountDownTimer countDownTimer;
	private  long timeLeftInMillis;

	MediaPlayer radioBtnMediaPlayer,nextBtnMediaPlayer;
	TextView questionDescription,txtQuestionNumber,quizCategory,questionTimer;
	ArrayList<Question> questionsList;
	RadioGroup radioGroup;
	RadioButton option1,option2,option3,option4;
	Button nextQuestionButton;
	ImageView img;
	DBHelper dbHelper=new DBHelper(QuizActivity.this);
	Question currentQuestion;
	int countOfCorrectAnswers=0,MAX_QUESTIONS=10;
	Boolean[] answersStatusList=new Boolean[10];
	int currentQuestionNumber=0;//will be use as index variable
	boolean isTimeOut=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		//Loading data
		//dbHelper.addQuestion(Question.GetAllQuestions());
		ConstraintLayout constraintLayout=findViewById(R.id.quizLayoutScreen);
		//Getting quiz category from intent
		Intent intent=getIntent();
		String category=intent.getStringExtra("category");

		//Hiding navbar to fit content in screen
		this.getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

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
		radioGroup=findViewById(R.id.radioGroup);
		questionTimer=findViewById(R.id.questionTimer);

		//Creating DBHelper class object which we'll use for the whole sessions.

		//Setting All the layout elements for first time
		setQuizCategory(category);
		setQuestionNumber();
		setQuestionsList();
		setCurrentQuestion(questionsList.get(currentQuestionNumber));//pass first question from list ini as argument.
		// Now all other functions will use this current object by themselves.
		// No need to pass information to each method individually
		setImg();
		setQuestionDescription();//Sets first question
		setOptions();//set 4 possible options
		//After everthing is set=up we need to start the timer.
		textColorDefaultCd=questionTimer.getTextColors();
		timeLeftInMillis=COUNTDOWN_IN_MILLIS;
		startCountDown();
	}
	public void startCountDown(){
		isTimeOut=false;
		countDownTimer=new CountDownTimer(COUNTDOWN_IN_MILLIS,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				timeLeftInMillis=millisUntilFinished;
				updateTimerTextView();
			}

			@Override
			public void onFinish() {
				timeLeftInMillis=0;
				updateTimerTextView();
				isTimeOut=true;
				SetNextQuestionOnScreen(nextQuestionButton);
			}
		}.start();
	}
	public void updateTimerTextView(){
		int minutes=(int)(timeLeftInMillis/1000)/60;
		int seconds=(int)(timeLeftInMillis/1000)%60;
		String timeFormat=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
		questionTimer.setText(timeFormat);
		if(timeLeftInMillis <10000){
			questionTimer.setTextColor(Color.RED);
		}else{
			questionTimer.setTextColor(Color.GREEN);
		}
	}

	public void setCurrentQuestion(Question question){
		currentQuestion=question;
	}

	public void setQuestionsList(){
		questionsList=dbHelper.GetQuestions(quizCategory.getText().toString());
	}

	public void setQuizCategory(String category){
		quizCategory.setText(category);
	}

	public void setQuestionNumber(){
		String text="Question Number: "+String.valueOf(currentQuestionNumber + 1);//since currentQuestionNumber is being used as index so its always 1 less than original number
		txtQuestionNumber.setText(text);//since starts fro 0, so add 1 to view properly
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
		String uri = "@drawable/"+currentQuestion.getImg_path();  // where myresource (without the extension) is the file
		Log.d("Image",uri);
		int imageResource = getResources().getIdentifier(uri, null, getPackageName());
		Log.d("Image",String.valueOf(imageResource));
		Drawable res = getResources().getDrawable(imageResource);
		img.setImageDrawable(res);	}

	public void updateAnswersStatusList(){
		int selectedOptionID=radioGroup.getCheckedRadioButtonId();
		RadioButton selectedRadioBtn=(RadioButton)findViewById(selectedOptionID);
		if(selectedOptionID==-1){
			answersStatusList[currentQuestionNumber]=false;
			return;
		}
		if(selectedRadioBtn.getText().toString().equals(currentQuestion.getAnswer())){
			countOfCorrectAnswers++;
			answersStatusList[currentQuestionNumber]=true;
		}else{
			answersStatusList[currentQuestionNumber]=false;
		}
	}

	public void SetNextQuestionOnScreen(View view){
		onClickSound();
		if(radioGroup.getCheckedRadioButtonId()==-1 && !isTimeOut){
			Toast.makeText(QuizActivity.this,"Please Select at least one option to proceed",Toast.LENGTH_SHORT).show();
			return;
		}
		countDownTimer.cancel();
		updateAnswersStatusList();
		resetAllOptions();//upchecking all options when new question appears;
		currentQuestionNumber=currentQuestionNumber+1;
		if(currentQuestionNumber==MAX_QUESTIONS-1){//checks if last question has come?
			String text="Finish Quiz";
			nextQuestionButton.setText(text);
		}else if(currentQuestionNumber==MAX_QUESTIONS){
			MoveToResultActivity();
			return;
		}
		setCurrentQuestion(questionsList.get(currentQuestionNumber));
		updateAllTheElements();
		startCountDown();
	}
	public void updateAllTheElements(){
		setImg();
		setQuestionDescription();
		setOptions();
		setQuestionNumber();
	}

	public void resetAllOptions(){
		Log.d("ALC","Radio");
		radioGroup.clearCheck();
		Log.d("ALC","Radio");
	}
	public void MoveToResultActivity(){
		Intent intent=new Intent(this,ResultActivity.class);
		intent.putExtra("answersStatus",answersStatusList);
		intent.putExtra("correctAnsCount",countOfCorrectAnswers);
		startActivity(intent);
	}
	//for playing sound each time a radio button is selected / clicked
	public void onClickSound(){
		nextBtnMediaPlayer=new MediaPlayer();
		nextBtnMediaPlayer=MediaPlayer.create(QuizActivity.this,R.raw.next_btn);
		nextBtnMediaPlayer.start();
		nextBtnMediaPlayer.setLooping(false);
		nextBtnMediaPlayer.setOnCompletionListener(new  MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				nextBtnMediaPlayer.reset();
				nextBtnMediaPlayer.release();
			}
		});
	}


	//for playing sound each time next button is clicked
	public void playSoundOnRadioButtonClick(View view) {
		radioBtnMediaPlayer=MediaPlayer.create(QuizActivity.this,R.raw.radio_btn);
		radioBtnMediaPlayer.start();
		radioBtnMediaPlayer.setLooping(false);
		radioBtnMediaPlayer.setOnCompletionListener(new  MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				radioBtnMediaPlayer.reset();
				radioBtnMediaPlayer.release();
			}
		});
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(countDownTimer!=null){
			countDownTimer.cancel();
		}
	}
	public void AddQuestionsToDatabase(){

	}

}