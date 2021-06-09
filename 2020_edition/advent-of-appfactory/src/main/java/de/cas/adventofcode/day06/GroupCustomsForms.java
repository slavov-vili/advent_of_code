package de.cas.adventofcode.day06;

import java.util.HashMap;
import java.util.Map;

public class GroupCustomsForms {
	private int groupSize;
	private Map<Character, Integer> questionToYesCount;
	
	public GroupCustomsForms() {
		this.groupSize = 0;
		this.questionToYesCount = new HashMap<>();
	}
	
	public int addAnswersForPerson(char[] yesQuestions) {
		for (Character question : yesQuestions) {
			Integer nextCount;
			if (!this.questionToYesCount.containsKey(question))
				nextCount = 1;
			else {
				Integer prevCount = this.questionToYesCount.get(question);
				nextCount = prevCount+1;	
			}
			this.questionToYesCount.put(question, nextCount);
		}
		
		this.groupSize += 1;
		return this.groupSize;
	}
	
	public int getYesQuestions() {
		return this.questionToYesCount.size();
	}
	
	public int countQuestionsAllYes() {
		return (int) this.questionToYesCount.values().stream().filter(x -> x==this.groupSize).count();
	}
}
