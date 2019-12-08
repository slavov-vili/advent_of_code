package day02.instructions;

import java.util.ArrayList;
import java.util.List;

import day02.instructions.IntCodeInstruction.ParamMode;
import utils.IntegerUtils;
import utils.ListUtils;

public class IntCodeInstructionUtils {

    public static List<ParamMode> generateParameterModesInOrder(int instructionCode, IntCodeInstruction instruction) {
        List<ParamMode> parameterModesInOrder = new ArrayList<>();
        if (instruction.getParamCount() <= 0)
            return parameterModesInOrder;

        List<Integer> knownModesInt = IntegerUtils.reverse(instructionCode / 100);
        List<Integer> allModesInt = ListUtils.addPaddingTo(knownModesInt,
                instruction.getParamCount() - knownModesInt.size(), 0);
        for (int modeInt : allModesInt)
            parameterModesInOrder.add(getParamModeFromInt(modeInt));
        return parameterModesInOrder;
    }

    protected static ParamMode getParamModeFromInt(int modeIntValue) {
        ParamMode modeValue;
        switch (modeIntValue) {
        case 0: {
            modeValue = ParamMode.POSITION;
            break;
        }
        case 1: {
            modeValue = ParamMode.IMMEDIATE;
            break;
        }
        default:
            throw new UnknownParameterModeException(
                    "The integer value " + modeIntValue + " does not represent any known Parameter Modes!");
        }
        return modeValue;
    }
}
