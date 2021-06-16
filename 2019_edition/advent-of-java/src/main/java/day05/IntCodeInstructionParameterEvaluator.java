package day05;

import day02.IntCodeComputer;

public class IntCodeInstructionParameterEvaluator {

	public static final Long POSITION_MODE = 0L;
	public static final Long IMMEDIATE_MODE = 1L;
	
	public Long handleParameter(IntCodeComputer computer, Long parameter, Long mode, boolean isWriteParam) {
		return (isWriteParam) ?
				handleWriteParameter(computer, parameter, mode) :
				handleReadParameter(computer, parameter, mode);
	}
	
	public Long handleReadParameter(IntCodeComputer computer, Long parameter, Long mode) {
		Long paramValue;
		if (POSITION_MODE.equals(mode))
			paramValue = computer.readFromMemory(parameter);
		else 
			paramValue = parameter;
		
		return paramValue;
	}
	
	public Long handleWriteParameter(IntCodeComputer computer, Long parameter, Long mode) {
		Long paramValue;
		if (POSITION_MODE.equals(mode))
			paramValue = parameter;
		else 
			paramValue = parameter;
		
		return paramValue;
	}
}
