package de.cas.adventofcode.day06;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import de.cas.adventofcode.shared.Day;

public class Day06Velislav extends Day<Integer> {
	protected Day06Velislav() {
		super(6);
	}

	public static void main(final String[] args) {
		new Day06Velislav().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		List<GroupCustomsForms> forms = parseCustomsForms(input);
		return forms.stream().mapToInt(GroupCustomsForms::getYesQuestions).sum();
	}

	@Override
	public Integer solvePart2(List<String> input) {
		List<GroupCustomsForms> forms = parseCustomsForms(input);
		return forms.stream().mapToInt(GroupCustomsForms::countQuestionsAllYes).sum();
	}
	
	private List<GroupCustomsForms> parseCustomsForms(final List<String> input) {
        final List<GroupCustomsForms> forms = new ArrayList<>();
        GroupCustomsForms form = new GroupCustomsForms();

        for (final String line : input) {
            if (Strings.isNullOrEmpty(line)) {
                forms.add(form);
                form = new GroupCustomsForms();
            } else {
               form.addAnswersForPerson(line.toCharArray());
            }
        }

        forms.add(form);
        return forms;
    }

}
