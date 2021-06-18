package day09;

import day02.IntCodeComputer;
import day05.IntCodeInstructionParameterEvaluator;

public class IntCodeInstructionParameterEvaluator9 extends IntCodeInstructionParameterEvaluator {
	
	public static final Long RELATIVE_MODE = 2L;
	
	@Override
	public Long handleReadParameter(IntCodeComputer computer, Long parameter, Long mode) {
		Long paramValue;
		if (RELATIVE_MODE.equals(mode)) {
			IntCodeComputer9 computer9 = (IntCodeComputer9) computer;
			paramValue = computer9.readFromMemory(calcRelativeParameter(computer9, parameter));
		} else 
			paramValue = super.handleReadParameter(computer, parameter, mode);
		
		return paramValue;
	}
	
	@Override
	public Long handleWriteParameter(IntCodeComputer computer, Long parameter, Long mode) {
		Long paramValue;
		if (RELATIVE_MODE.equals(mode)) {
			IntCodeComputer9 computer9 = (IntCodeComputer9) computer;
			paramValue = calcRelativeParameter(computer9, parameter);
		} else 
			paramValue = super.handleWriteParameter(computer, parameter, mode);
		
		return paramValue;
	}
	
	private Long calcRelativeParameter(IntCodeComputer9 computer, Long parameter) {
		return computer.getRelativeBase() + parameter;
	}
}
