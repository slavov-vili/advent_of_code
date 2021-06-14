package day05;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import day02.IntCodeComputer;

public class IntCodeInstructionParameterModeHandler<T extends IntCodeComputer> {

	private Map<Integer, BiFunction<T, Integer, Integer>> modeHandlerMap;

	public IntCodeInstructionParameterModeHandler() {
		this.modeHandlerMap = new HashMap<>();
	}
	
	public Integer handleParameter(T computer, Integer parameter, Integer mode) {
		return modeHandlerMap.get(mode).apply(computer, parameter);
	}
	
	public void addModeHandler(Integer modeId, BiFunction<T, Integer, Integer> modeHandler) {
		this.modeHandlerMap.put(modeId, modeHandler);
	}
}
