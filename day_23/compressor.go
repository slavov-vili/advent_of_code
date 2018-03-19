package main

import (
    "bufio"
    "errors"
    "fmt"
    "log"
    "os"
    "regexp"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores a list of all instructions read as input
    var instructions = make([]*Instruction, 0);
    // stores the maximum number of arguments that an instruction can receive
    const max_args = 2;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // split the instruction into its parts
            var parts = strings.Fields(input);
            // add a new instruction with the given name and arguments to the list
            instructions = append(instructions, NewInstruction(parts[0], parts[1:], max_args));
            continue;
        }   //end if


        // PART I
        var program_1 = NewProgram(make(map[string]int, 0), 0, false);
        fmt.Println("The 'mul' instruction was called", count_muls(program_1, instructions), "times!");

        // PART II
        var program_2_regs = make(map[string]int, 0);
        program_2_regs["a"] = 1;
        var program_2 = NewProgram(program_2_regs, 0, false);
        fmt.Println("End value for register h:", get_end_value_of_h(program_2, instructions));


        // clear the instruction list
        instructions = make([]*Instruction, 0);
    }   //end for
}   //end main

// TODO: if find a cycle of instructions - break


// counts the number of times the 'mul' instruction was called
func count_muls(program *Program, instructions []*Instruction) (mul_count int) {
    // while the program hasn't terminated
    for !program.IsTerminated(len(instructions)) {
        // store the current instruction
        var cur_instruction = instructions[program.cur_instruction_idx];
        if cur_instruction.name == "mul" { mul_count++ }

        // handle the instruction
        was_jump, err := program.HandleInstruction(cur_instruction);
        if err != nil {
            log.Fatal(err);
        }   //end if

        // increment the index of the current instruction
        program.IncrementInstructionIndex(was_jump);
    }   //end for

    return;
}   //end func



// runs the program until the value in register 'h' stops changing
func get_end_value_of_h(program *Program, instructions []*Instruction) (end_value int) {

    return program.registers["h"];
}   //end func





type Instruction struct {
    name   string
    args []string
}   //end type


func NewInstruction(new_name string, new_args []string, max_args int) *Instruction {
    // create a new instruction
    var new_instruction = new(Instruction);

    // set the new instruction's member variables
    new_instruction.name = new_name;
    new_instruction.args = make([]string, max_args);
    for i, new_arg := range new_args {
        new_instruction.args[i] = new_arg;
    }   //end for

    // return the pointer to the new instruction
    return new_instruction;
}   //end func





type Program struct {
    registers           map[string]int
    cur_instruction_idx int
    is_receiving        bool
}   //end type


func NewProgram(new_registers map[string]int, new_instruction_idx int, receiving_status bool) *Program {
    // create the new program
    var new_program = new(Program);

    // set its member variables
    new_program.registers             = make(map[string]int, len(new_registers));
    for key, value := range new_registers {
        new_program.registers[key] = value;
    }   //end for
    new_program.cur_instruction_idx   = new_instruction_idx;
    new_program.is_receiving          = receiving_status;

    // return the pointer to the new program
    return new_program;
}   //end func


// shows whether the program can execute its current instruction
func (program *Program) IsTerminated(instruction_count int) bool {
    return ((program.cur_instruction_idx < 0) || (program.cur_instruction_idx >= instruction_count));
}   //end func


func (program *Program) HandleInstruction(instruction *Instruction) (was_jump bool, err error) {
        switch instruction.name {

        // register-related instructions
        case "set":
            // set the respective register's value
            program.registers[instruction.args[0]]  = get_arg_value(instruction.args[1], program.registers);

        case "sub":
            // increase the respective register's value
            program.registers[instruction.args[0]] -= get_arg_value(instruction.args[1], program.registers);

        case "mul":
            // multiply the respective register's value
            program.registers[instruction.args[0]] *= get_arg_value(instruction.args[1], program.registers);


        // instruction-related instructions
        case "jnz":
            // if the first argument is greater than '0'
            if get_arg_value(instruction.args[0], program.registers) != 0 {
                // set the flag to true
                was_jump = true;
                // add the offset to the current instruction index
                program.cur_instruction_idx += get_arg_value(instruction.args[1], program.registers);
            }   //end if

        // any other instruction
        default:
            err = errors.New("Program doesn't support instruction " + instruction.name);
        }   //end switch
        return;
}   //end func


func (program *Program) IncrementInstructionIndex(last_was_jump bool) int {
    // if the last executed instruction WASN'T a "jump"
    if !last_was_jump {
        program.cur_instruction_idx++;
    }   //end else

    return program.cur_instruction_idx;
}   //end func





// gets the value of an instruction argument
func get_arg_value(argument string, registers map[string]int) (arg_value int) {
    var isNumber = regexp.MustCompile("\\d");

    // if the argument IS a number
    if isNumber.MatchString(argument) {
        // the value is the argument itself
        arg_value, _ = strconv.Atoi(argument);
    } else {
        // the value is the value of the given register name
        arg_value = registers[argument];
    }   //end if

    return;
}   //end func


// returns a clone of the given int array
func clone_int_array(array_to_clone []int) (cloned_array []int) {
    // make an array of the same length
    cloned_array = make([]int, len(array_to_clone));

    // transfer all elements from the initial array
    for i, value := range array_to_clone {
        cloned_array[i] = value;
    }   //end for

    return;
}   //end func
