package day24;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SymbolicALU extends AbstractALU<String> {

    public SymbolicALU(List<String> lines) {
        super(lines);
        this.memory.put("z", "z0");
    }

    @Override
    public Map<String, Consumer<String[]>> getOperations(Map<String, String> memory) {
        Map<String, Consumer<String[]>> operations = new HashMap<>();
        operations.put(AluOperations.INPUT.id, args -> {
            for (String key : memory.keySet())
                memory.put(key, getDefault(key));
            memory.put(args[0], getValueOf(args[1]));
        });
        operations.put(AluOperations.ADD.id, args -> {
            memory.put(args[0], String.format("(%s + %s)", this.getValueOf(args[0]), this.getValueOf(args[1])));
        });
        operations.put(AluOperations.MULTIPLY.id, args -> {
            String newValue = args[0];
            if (!args[1].equals("0"))
                newValue = String.format("(%s * %s)", this.getValueOf(args[0]), this.getValueOf(args[1]));
            memory.put(args[0], newValue);
        });
        operations.put(AluOperations.DIVIDE.id, args -> {
            memory.put(args[0], String.format("(%s / %s)", this.getValueOf(args[0]), this.getValueOf(args[1])));
        });
        operations.put(AluOperations.MODULO.id, args -> {
            memory.put(args[0], String.format("(%s %% %s)", this.getValueOf(args[0]), this.getValueOf(args[1])));
        });
        operations.put(AluOperations.EQUALS.id, args -> memory.put(args[0],
                String.format("(%s == %s)", getValueOf(args[0]), getValueOf(args[1]))));
        return operations;
    }

    @Override
    public String getInput() {
        return "w" + super.i;
    }

    @Override
    public String getDefault(String arg) {
        return arg;
    }

    @Override
    public String parseArg(String arg) { return arg; }

    @Override
    public void operationAction(String op, String[] args) {
        super.operationAction(op, args);
        if ("add".equals(op) && "z".equals(args[0]) && "y".equals(args[1])) {
            System.out.println("z = " + super.getValueOf("z"));
            this.memory.put("z", "z" + super.i);
        }
    }

    public enum AluOperations {
        INPUT("inp"),
        ADD("add"),
        MULTIPLY("mul"),
        DIVIDE("div"),
        MODULO("mod"),
        EQUALS("eql");

        public String id;

        AluOperations(String id) {
            this.id = id;
        }
    }
}
