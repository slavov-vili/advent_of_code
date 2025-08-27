package day24;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public abstract class AbstractALU<T> {
    public List<String> lines;
    public Map<String, T> memory;
    public Map<String, Consumer<String[]>> operations;
    public int i;

    public AbstractALU(List<String> lines) {
        this.lines = lines;
        this.memory = new HashMap<>();
        this.operations = getOperations(this.memory);
        this.i = -1;
    }

    public void reset() {
        this.memory.replaceAll((n, v) -> this.getDefault(n));
        this.i = -1;
    }

    public abstract Map<String, Consumer<String[]>> getOperations(Map<String, T> memory);

    public abstract String getInput();

    public abstract T getDefault(String arg);

    public abstract T parseArg(String arg);

    public T process() {
        for (String line : this.lines) {
            String[] parts = line.split(" ");
            String op = parts[0];
            String[] args = new String[2];
            args[0] = parts[1];

            if ("inp".equals(op)) {
                args[1] = getInput();
                this.i++;
            } else {
                args[1] = parts[2];
            }

            operationAction(op, args);
        }
        return this.getValueOf("z");
    }

    public void operationAction(String op, String[] args) {
        this.operations.get(op).accept(args);
    }

    public T getValueOf(String arg) {
        if (isVariable(arg))
            return this.memory.getOrDefault(arg, getDefault(arg));
        else
            return parseArg(arg);
    }

    public boolean isVariable(String arg) {
        return IntStream.range(0, arg.length()).allMatch(i -> Character.isAlphabetic(arg.charAt(i)));
    }


    public enum AluOperations {
        INPUT("inp"),
        ADD("add"),
        MULTIPLY("mul"),
        DIVIDE("div"),
        MODULO("mod"),
        EQUALS("eql");

        public final String id;

        AluOperations(String id) {
            this.id = id;
        }
    }
}