package day05;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import day02.IntCodeComputer;

public class IntCodeInstructionParameterModeHandler {

	private Map<Long, BiFunction<IntCodeComputer, Long, Long>> modeHandlerMap;

	public IntCodeInstructionParameterModeHandler() {
		this.modeHandlerMap = new HashMap<>();
	}
	
	public Long handleParameter(IntCodeComputer computer, Long parameter, Long mode) {
		return modeHandlerMap.get(mode).apply(computer, parameter);
	}
	
	public void addModeHandler(Long modeId, BiFunction<IntCodeComputer, Long, Long> modeHandler) {
		this.modeHandlerMap.put(modeId, modeHandler);
	}
}
