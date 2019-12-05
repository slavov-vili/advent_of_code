package day02.instructions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import day02.instructions.IntCodeInstruction.ParamMode;
import utils.IntegerUtils;
import utils.ListUtils;

public class IntCodeInstructionUtils {
    
    public static Map<Integer, ParamMode> mapParamIndexToMode(int instructionCode, int paramCount) {
        Map<Integer, ParamMode> paramIndexToMode = new HashMap<>();
        List<Integer> knownModesInt = IntegerUtils.reverse(instructionCode / 100);
        List<Integer> allModesInt = ListUtils.addPaddingTo(knownModesInt, paramCount-knownModesInt.size(), 0);
        int outputParamIndex = paramCount-1; 
        for (int i = 0; i <= outputParamIndex; i++)
            paramIndexToMode.put(i, getParamModeFromInt(allModesInt.get(i)));
        return paramIndexToMode;
    }
    
    protected static ParamMode getParamModeFromInt(int modeIntValue) {
        ParamMode modeValue;
        switch(modeIntValue) {
        case 0: {
            modeValue = ParamMode.POSITION;
            break;
        }
        case 1: {
            modeValue = ParamMode.IMMEDIATE;
            break;
        }
        default:
            throw new UnknownParameterModeException("The integer value " + modeIntValue + " does not represent any known Parameter Modes!");
        }
        return modeValue;
    }
}
