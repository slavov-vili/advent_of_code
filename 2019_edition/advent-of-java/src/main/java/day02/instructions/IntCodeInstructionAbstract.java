package day02.instructions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.IntegerUtils;
import utils.ListUtils;

public abstract class IntCodeInstructionAbstract implements IntCodeInstruction {

    private int opCode;
    private int paramCount;
    private Map<Integer, ParamMode> paramIndexToMode;

    public IntCodeInstructionAbstract(int instructionCode, int paramCount) {
        this.opCode = instructionCode % 100;
        this.paramCount = paramCount;
        this.paramIndexToMode = mapParamIndexToMode(instructionCode, paramCount);
    }
    
    protected Map<Integer, ParamMode> mapParamIndexToMode(int instructionCode, int paramCount) {
        Map<Integer, ParamMode> paramIndexToMode = new HashMap<>();
        List<Integer> knownModesInt = IntegerUtils.reverse(instructionCode / 100);
        List<Integer> allModesInt = ListUtils.addPaddingTo(knownModesInt, paramCount-knownModesInt.size(), 0);
        for (int i = 0; i < allModesInt.size(); i++)
            paramIndexToMode.put(i, getParamModeFromInt(allModesInt.get(i)));
        return paramIndexToMode;
    }
    
    protected ParamMode getParamModeFromInt(int modeIntValue) {
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
    
    @Override
    public ParamMode getModeOfParam(int paramIndex) {
    	if (!this.paramIndexToMode.containsKey(paramIndex))
    		throw new NoSuchParameterException("A parameter with index " + paramIndex + " does not exist!");
        return this.paramIndexToMode.get(paramIndex);
    }
    
    @Override
    public boolean paramModeIs(int paramIndex, ParamMode paramModeToCheck) {
        return this.getModeOfParam(paramIndex).equals(paramModeToCheck);
    }

    @Override
    public int getCode() {
        return this.opCode;
    }

    @Override
    public int getParamCount() {
        return this.paramCount;
    }

}
