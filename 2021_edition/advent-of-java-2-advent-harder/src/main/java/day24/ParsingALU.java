package day24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;



// Tips:
// x = z
// x = x mod NUM
// x = x add NUM
// z = z div NUM
// x = x eql wi ? 1 : 0
// y = 25 * x + 1
// z = z mul y
// y = w + NUM
// y = y mul x
// z = z add y

// Formula:
// x = z % NUM1 + NUM2
// z = z / NUM3
// if x != wi
//     z = z * 26 + w + NUM4
public class ParsingALU extends ExecutingALU {
    private long[] restZ = new long[14];
    private long[] offsetX = new long[14];
    private long[] divZ = new long[14];
    private long[] offsetY = new long[14];

    public ParsingALU(List<String> lines, long modelNumber) {
        super(lines, modelNumber);
        super.process();
    }

    @Override
    public Map<String, Consumer<String[]>> getOperations(Map<String, Long> memory) {
        Map<String, Consumer<String[]>> operations = new HashMap<>();
        operations.put(AluOperations.INPUT.id, args -> {});
        operations.put(AluOperations.ADD.id, args -> {
            if (!this.isVariable(args[1])) {
                if ("x".equals(args[0]))
                    this.offsetX[this.i] = getValueOf(args[1]);
                else if ("y".equals(args[0]))
                    this.offsetY[this.i] = getValueOf(args[1]);
            }
        });
        operations.put(AluOperations.MULTIPLY.id, args -> {});
        operations.put(AluOperations.DIVIDE.id, args -> {
            if ("z".equals(args[0]) && !this.isVariable(args[1])) {
                this.divZ[this.i] = getValueOf(args[1]);
            }
        });
        operations.put(AluOperations.MODULO.id, args -> {
            if ("x".equals(args[0]) && !this.isVariable(args[1])) {
                this.restZ[this.i] = getValueOf(args[1]);
            }
        });
        operations.put(AluOperations.EQUALS.id, args -> {});
        return operations;
    }

    @Override
    public Long process() {
        long lastResult = this.getDefault("z");
        for (int i = 0; i < Long.toString(this.modelNumber).length(); i++) {
            this.i = i;
            lastResult = this.calcResult(lastResult);
        }
        return lastResult;
    }

    // TODO: figure out why this takes so long, need to figure out something.
    private long calcResult(long lastResult) {
        long w = this.parseArg(this.getInput());
        long x = lastResult % this.restZ[this.i] + this.offsetX[this.i];
        long z = lastResult / this.divZ[this.i];
        if (x != w)
            z = z * 26 + w + this.offsetY[this.i];
        return z;
    }

}
