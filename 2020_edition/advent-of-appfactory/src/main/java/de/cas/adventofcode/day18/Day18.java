package de.cas.adventofcode.day18;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.cas.adventofcode.shared.Day;

public class Day18 extends Day<Long> {

	private static final Pattern BRACKET_EXPRESSION_PATTERN = Pattern.compile("\\(([\\d+* ]+)\\)");
	private static final Pattern PLUS_EXPRESSION_PATTERN = Pattern.compile("(\\d+ \\+ \\d+)");
	public static final String OPERATION_REGEX = "[+*]";

	protected Day18() {
		super(18);
	}

	public static void main(final String[] args) {
		new Day18().run();
	}

	@Override
	public Long solvePart1(List<String> input) {
		return input.stream()
				.mapToLong(line -> evaluateExpression(line, this::evaluateLeftToRight))
				.sum();
	}

	@Override
	public Long solvePart2(List<String> input) {
		return input.stream()
				.mapToLong(line -> evaluateExpression(line, this::evaluatePlusesFirst))
				.sum();
	}
	
	private long evaluateExpression(String expression, Function<String, Long> evaluator) {
		String expressionWithoutBrackets = applyOperationWhileMatch(expression,
				BRACKET_EXPRESSION_PATTERN::matcher, evaluator);
		return evaluator.apply(expressionWithoutBrackets);
	}

	private long evaluateLeftToRight(String expression) {
		String[] trimmedExpression = expression.split(" ");
		long result = 0;
		String curOperator = "+";

		for (String value : trimmedExpression) {
			if (value.matches(OPERATION_REGEX))
				curOperator = value;
			else
				result = applyOperator(result, Long.parseLong(value), curOperator);
		}

		return result;
	}

	private long evaluatePlusesFirst(String expression) {
		String expressionWithResolvedPluses = applyOperationWhileMatch(expression,
				PLUS_EXPRESSION_PATTERN::matcher, this::evaluateLeftToRight); 
		return this.evaluateLeftToRight(expressionWithResolvedPluses);
	}

	private String applyOperationWhileMatch(String expression,
			Function<String, Matcher> matcherCreator,
			Function<String, Long> operation) {
		String newExpression = expression;
		Matcher matcher = matcherCreator.apply(newExpression);

		while(matcher.find()) {
			long result = operation.apply(matcher.group(1));
			newExpression = matcher.replaceFirst(String.valueOf(result));
			matcher = matcherCreator.apply(newExpression);
		}

		return newExpression;
	}

	private long applyOperator(long operand1, long operand2, String curOperator) {
		if ("+".equals(curOperator))
			return operand1 + operand2;
		else
			return operand1 * operand2;
	}

}
