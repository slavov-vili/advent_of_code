package main

import (
    "bufio"
    "fmt"
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


        // clear the instruction list
        instructions = make([]*Instruction, 0);
    }   //end for
}   //end main



// runs the instructions and finds the frequency of the first sound which was received
func find_first_rec_sound(instructions []*Instruction) (first_rec_sound_freq int) {
    // stores the index of the current instruction being executed
    var i = 0;
    // maps the register names to their values
    var registers = make(map[string]int, 0);

    // TODO: make function to handle register-related instructions and skips
    OUTER:
    // while the index of the current instruction is within boundaries
    for (i >= 0) && (i < len(instructions)) {
        // store the current instruction
        var cur_instruction = instructions[i];


        // handle the instruction
        switch cur_instruction.name {
        case "snd":
            // store the frequency of the sound
            first_rec_sound_freq = get_arg_value(cur_instruction.args[0], registers);

        case "set":
            // set the respective register's value
            registers[cur_instruction.args[0]] = get_arg_value(cur_instruction.args[1], registers);

        case "add":
            // increase the respective register's value
            registers[cur_instruction.args[0]] += get_arg_value(cur_instruction.args[1], registers);

        case "mul":
            // multiply the respective register's value
            registers[cur_instruction.args[0]] *= get_arg_value(cur_instruction.args[1], registers);

        case "mod":
            // divide the respective register's value
            registers[cur_instruction.args[0]] %= get_arg_value(cur_instruction.args[1], registers);

        case "rcv":
            // if the value of the argument is not '0'
            if get_arg_value(cur_instruction.args[0], registers) != 0 {
                // stop iterating over the instructions
                break OUTER;
            }   //end if

        case "jgz":
            // if the first argument is greater than '0'
            if get_arg_value(cur_instruction.args[0], registers) > 0 {
                // add the offset to the current index
                i += get_arg_value(cur_instruction.args[1], registers);
                // continue iterating over the instructions
                continue OUTER;
            }   //end if
        }   //end switch

        // increment the index of the current instruction
        i++;
    }   //end for

    return;
}   //end func



// gets the value of an instruction argument
func get_arg_value(argument string, registers map[string]int) (arg_value int) {
    var isNumber = regexp.MustCompile("\\d");

    // if the argument is not a number
    if isNumber.MatchString(argument) {
        // the value is the argument itself
        arg_value, _ = strconv.Atoi(argument);
    } else {
        // the value is the value of the given register name
        arg_value = registers[argument];
    }   //end if

    return;
}   //end func





type Instruction struct {
    name   string
    args []string
}   //end func


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
