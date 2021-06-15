package day05;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import day02.IntCodeComputer;

public class IntCodeInstructionParameterModeHandler {

	private Map<Integer, BiFunction<IntCodeComputer, Integer, Integer>> modeHandlerMap;

	public IntCodeInstructionParameterModeHandler() {
		this.modeHandlerMap = new HashMap<>();
	}
	
	public Integer handleParameter(IntCodeComputer computer, Integer parameter, Integer mode) {
		return modeHandlerMap.get(mode).apply(computer, parameter);
	}
	
	public void addModeHandler(Integer modeId, BiFunction<IntCodeComputer, Integer, Integer> modeHandler) {
		this.modeHandlerMap.put(modeId, modeHandler);
	}
}
