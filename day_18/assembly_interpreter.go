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
        // store the frequency of the first received sound
        var first_rec_sound_freq = 0;
        // find the frequency of the first received sound
        first_rec_sound_freq = find_first_rec_sound(instructions);
        // print out the frequency
        fmt.Println("Frequency of first received sound:", first_rec_sound_freq);


        // PART II
        // find the number of sends that program '1' made
        var prog_1_send_count = find_prog_1_send_count(instructions);
        // print out the count
        fmt.Println("Program '1' sent", prog_1_send_count, "values !");


        // clear the instruction list
        instructions = make([]*Instruction, 0);
    }   //end for
}   //end main



// runs the instructions and finds the frequency of the first sound which was received
func find_first_rec_sound(instructions []*Instruction) (first_rec_sound_freq int) {
    // create a program to execute the given instructions
    var program = NewProgram(make(map[string]int, 0), 0, false);


    OUTER:
    // while the program hasn't terminated
    for !program.IsTerminated(len(instructions)) {
        // store the current instruction
        var cur_instruction = instructions[program.cur_instruction_idx];


        // handle the instruction
        was_jump, err := program.HandleInstruction(cur_instruction);
        if err != nil {
            switch cur_instruction.name {

            case "snd":
                // store the frequency of the sound
                first_rec_sound_freq = get_arg_value(cur_instruction.args[0], program.registers);

            case "rcv":
                // if the value of the argument is not '0'
                if get_arg_value(cur_instruction.args[0], program.registers) != 0 {
                    // stop iterating over the instructions
                    break OUTER;
                }   //end if

            default:
                log.Fatal(err);
            }   //end switch
        }   //end if


        // increment the index of the current instruction
        program.IncrementInstructionIndex(was_jump);
    }   //end for

    return;
}   //end func



// runs the instructions for two programs and returns how many times one of the programs sent out a value to the other
func find_prog_1_send_count(instructions []*Instruction) (prog_1_send_count int) {
    // create program '0'
    var prog_0 = NewProgram(make(map[string]int, 0), 0, false);
    prog_0.registers["p"] = 0;
    // stores the values to be received by program '0'
    var prog_0_inbox = make([]int, 0);

    // create program '1'
    var prog_1 = NewProgram(make(map[string]int, 0), 0, false);
    prog_1.registers["p"] = 1;
    // stores the values to be received by program '1'
    var prog_1_inbox = make([]int, 0)


    OUTER:
    for {
        // whether either program is terminated or receiving a value
        var prog_0_term_or_rec = prog_0.IsTerminated(len(instructions)) || (prog_0.is_receiving && len(prog_0_inbox) == 0);
        var prog_1_term_or_rec = prog_1.IsTerminated(len(instructions)) || (prog_1.is_receiving && len(prog_1_inbox) == 0);

        // if NEITHER of the programs is capable of executing instructions
        if prog_0_term_or_rec && prog_1_term_or_rec {
            break OUTER;
        }   //end if

        // handle instructions with program '0' until a "receive" instruction is reached and there is nothing to receive
        prog_0_inbox, prog_1_inbox = handle_instructions_until_empty_inbox(prog_0, instructions, prog_0_inbox, prog_1_inbox);

        // store the size of program '0''s inbox
        var prog_0_old_inbox_size = len(prog_0_inbox);
        // handle instructions with program '1' until a "receive" instruction is reached and there is nothing to receive
        prog_1_inbox, prog_0_inbox = handle_instructions_until_empty_inbox(prog_1, instructions, prog_1_inbox, prog_0_inbox);
        // if after handling the instructions, the inbox of program '0' is bigger
        if prog_0_old_inbox_size < len(prog_0_inbox) {
            // increase the count of sent values
            prog_1_send_count += (len(prog_0_inbox) - prog_0_old_inbox_size);
        }   //end if
    }   //end for

    return;
}   //end func


// handles instructions for the given program until a "receive" instruction is reached and there is nothing to receive
func handle_instructions_until_empty_inbox(prog_a *Program, instructions []*Instruction, prog_a_inbox []int, prog_b_inbox []int) (new_prog_a_inbox, new_prog_b_inbox []int) {
        // clone the two inboxes so we can edit them in peace :)
        new_prog_a_inbox = clone_int_array(prog_a_inbox);
        new_prog_b_inbox = clone_int_array(prog_b_inbox);


        OUTER:
        // while program 'a' hasn't terminated yet
        for !prog_a.IsTerminated(len(instructions)) {
            // store the instruction which program 'a' is currently executing
            var cur_instruction = instructions[prog_a.cur_instruction_idx];


            // handle the instruction
            was_jump, err := prog_a.HandleInstruction(cur_instruction);
            if err != nil {
                switch cur_instruction.name {

                case "snd":
                    // add the value to the OTHER program's inbox
                    new_prog_b_inbox = append(new_prog_b_inbox, get_arg_value(cur_instruction.args[0], prog_a.registers));

                case "rcv":
                    // if there is NO value to be received
                    if len(new_prog_a_inbox) == 0 {
                        // set the flag to true
                        prog_a.is_receiving = true;
                        // stop iterating through instructions
                        break OUTER;
                        // if a value CAN be received
                    } else {
                        // set the flag to false
                        prog_a.is_receiving = false;

                        // store the name of the register, where the received value should be stored
                        var reg_name = cur_instruction.args[0];
                        // store the first value in the inbox to the register
                        prog_a.registers[reg_name] = new_prog_a_inbox[0];

                        // remove the value from THIS program's inbox
                        new_prog_a_inbox = new_prog_a_inbox[1:];
                    }   //end else

                default:
                    log.Fatal(err);
                }   //end switch
            }   //end if

            prog_a.IncrementInstructionIndex(was_jump);
        }   //end for

    return;
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

        case "add":
            // increase the respective register's value
            program.registers[instruction.args[0]] += get_arg_value(instruction.args[1], program.registers);

        case "mul":
            // multiply the respective register's value
            program.registers[instruction.args[0]] *= get_arg_value(instruction.args[1], program.registers);

        case "mod":
            // divide the respective register's value
            program.registers[instruction.args[0]] %= get_arg_value(instruction.args[1], program.registers);


        // instruction-related instructions
        case "jgz":
            // if the first argument is greater than '0'
            if get_arg_value(instruction.args[0], program.registers) > 0 {
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
