package day24;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ExecutingALU extends AbstractALU<Long> {
    public final static long modelMask = 10000000000000L;
    public long modelNumber;

    public ExecutingALU(List<String> lines, long modelNumber) {
        super(lines);
        this.setModelNumber(modelNumber);
    }

    public void reset(Optional<Long> modelNumber) {
        super.reset();
        long newModelNumber = modelNumber.orElseGet(() -> this.modelNumber);
        this.setModelNumber(newModelNumber);
    }

    @Override
    public Map<String, Consumer<String[]>> getOperations(Map<String, Long> memory) {
        Map<String, Consumer<String[]>> operations = new HashMap<>();
        operations.put(AluOperations.INPUT.id, args -> memory.put(args[0],
                getValueOf(args[1])));
        operations.put(AluOperations.ADD.id, args -> memory.put(args[0],
                getValueOf(args[0]) + getValueOf(args[1])));
        operations.put(AluOperations.MULTIPLY.id, args -> memory.put(args[0],
                getValueOf(args[0]) * getValueOf(args[1])));
        operations.put(AluOperations.DIVIDE.id, args -> memory.put(args[0],
                getValueOf(args[0]) / getValueOf(args[1])));
        operations.put(AluOperations.MODULO.id, args -> memory.put(args[0],
                getValueOf(args[0]) % getValueOf(args[1])));
        operations.put(AluOperations.EQUALS.id, args -> memory.put(args[0],
                getValueOf(args[0]).equals(getValueOf(args[1])) ? 1L : 0L));
        return operations;
    }

    @Override
    public String getInput() {
        long factor = (long) (modelMask / Math.pow(10, super.i));
        long input = (this.modelNumber / factor) % 10;
        return Long.toString(input);
    }

    @Override
    public Long getDefault(String arg) {
        return 0L;
    }

    @Override
    public Long parseArg(String arg) {
        return Long.parseLong(arg);
    }

    public void setModelNumber(long newModelNumber) {
        this.modelNumber = newModelNumber;
    }
}
