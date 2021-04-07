
package com.umairmaik.assignmentone;

import java.util.ArrayList;
import java.util.List;

public class Question {
	public String QuestionDescription;
	private String category;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String answer;
	private String img_path;

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}


	public String getQuestionDescription() {
		return QuestionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		QuestionDescription = questionDescription;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Question(String category,String questionDescription, String option1, String option2, String option3, String option4, String answer,String imagePath) {
		QuestionDescription = questionDescription;
		this.category = category;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.answer = answer;
		this.img_path=imagePath;
	}

	static public ArrayList<Question> GetAllQuestions() {
		Question q1 = new Question(
				"Islamic", "In which city 'Holy Kaaba' is located?", "Makkah", "Medina", "Riyadh", "Jeddah", "Makkah", "kaaba");
		Question q2 = new Question(
				"Islamic", "Which is the best month mentioned in Quran?", "Ramadan", "Rajab", "Shaban", "Rabi al-awwal", "Ramadan", "holy_quran");
		Question q3 = new Question(
				"Islamic", "What is the calendar which Muslims use?", "Gregorian Calendar", "	Roman Calendar", "	Hijri Calendar", "	Persian Calendar", "	Hijri Calendar", "calendar");
		Question q4 = new Question(
				"Islamic", "Who introduce the Hijri Calendar", "Hazrat Umar(R.A)", "	Hazrat Ali(R.A)", "Hazrat Usman(R.A)", "Hazrat Abu Bakar(R.A)", "Hazrat Umar(R.A)", "hijri_calendar");
		Question q5 = new Question(
				"Islamic", "How many verses of Holy Quran are there?", "6666", "6667", "6665", "5666", "6666", "verses");
		Question q6 = new Question(
				"Islamic", "The term \"Islam\" means?", "Peace", "Gratitude", "Submission", "Fortitude", "Peace", "islam_word");
		Question q7 = new Question(
				"Islamic", "Namaz e Khasoof is offered when_____?", "Lunar Eclipse", "Solar Eclipse", "Earthquake", "Heavy Rain", "Lunar Eclipse", "namaz");
		Question q8 = new Question(
				"Islamic", "'Cave Hira' is situated near____?", "Medina", "Abha", "Makkah", "Jeddah", "Makkah", "cave_hira");
		Question q9 = new Question(
				"Islamic", "Eid prayer is _____?", "Wajib", "Farz", "Sunnah", "Mustahib", "Wajib", "eid");
		Question q10 = new Question(
				"Islamic", "Masjid Qiblatain is in _____ ?", "Makkah", "Medina", "Riyadh", "Jeddah", "Medina", "masjid_qiblatain");
		ArrayList<Question> allQuestionsList=new ArrayList<Question>();
		allQuestionsList.add(q2);
		allQuestionsList.add(q3);
		allQuestionsList.add(q4);
		allQuestionsList.add(q5);
		allQuestionsList.add(q6);
		allQuestionsList.add(q7);
		allQuestionsList.add(q8);
		allQuestionsList.add(q9);
		allQuestionsList.add(q10);
		return allQuestionsList;
	}

}
