package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores each row of the input
    var input_lines []*Program;
    // stores which part of the assignment is being tested
    var part int

    // convert the very first input to an int and store it
    println("Testing Part: ")
    for scanner.Scan() {
        part,_ = strconv.Atoi(scanner.Text())
        // if an invalid part is given
        if (part < 1) || (part > 2) {
            println("Invalid part of the assignment !")
            println("Try again: ")
            continue;
        }   //end if
        break;
    }   //end for

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // stores the list of programs being held by the current one
            var holding []string;
            // separate the name and weight from the list of programs being held
            var parts = strings.Split(input, "->");
            // separate the name from the weight
            var name_weight = strings.Fields(parts[0]);
            var name        = strings.TrimSpace(name_weight[0]);
            // convert the weight to int and store it
            var weight_str = strings.TrimSpace(name_weight[1]);
            weight_str = strings.TrimPrefix(weight_str, "(");
            weight_str = strings.TrimSuffix(weight_str, ")");
            var weight_int,_    = strconv.Atoi(weight_str);

            // if nothing is being held
            if len(parts) == 1 {
                holding = []string{};
            // if other programs are being held
            } else {
                // make a list of them
                holding = strings.Split(parts[1], ", ");
                // trim the first and last names just in case
                holding[0] = strings.TrimSpace(holding[0]);
                holding[len(holding)-1] = strings.TrimSpace(holding[len(holding)-1]);
            }   //end if

            // add the new input to the list
            input_lines = append(input_lines, NewProgram(name, weight_int, holding));
            continue;
        }   //end if

        // run the program depending on which part is being tested
        switch part {
        case 1: {
            // run the tower builder and print the name of the bottom program
            var bottom_name = run_tower_builder_find_bottom(input_lines);
            print("Bottom: ")
            print(bottom_name)
            println()
        }   //end case
        //case 2: {
        
        //}   //end case
        }   //end switch
        // clean the input lines
        input_lines = []*Program{};
    }   //end for
}   //end main



// Runs the tower builder and finds the name of the bottom of the tower
//
// Arguments:
// input - the lines of the input (the information about each program)
//
// Returns:
// the name of the bottom of the tower
func run_tower_builder_find_bottom(input []*Program) string {
    var bottom_name string;
    // maps the name of a Program to:
    //  1 - if it IS NOT being held by any other program
    //  0 - if it hasn't been seen yet
    // -1 - if it IS being held by another program
    var potential_bottoms = make(map[string]int);

    // for each program in the input
    for _,prog := range input {
        // if a program isn't holding anything then it cannot be at the bottom
        if len(prog.holding) == 0 { continue; }

        // if the program hasn't been seen before
        if potential_bottoms[prog.name] == 0 {
            // add the current program as a possible bottom
            potential_bottoms[prog.name] = 1;
        }   //end if

        // for each program being held by this one
        for _,held_name := range prog.holding {
            // reject it as a potential bottom
            potential_bottoms[held_name] = -1;
        }   //end for
    }   //end for

    // find the program name in the map, whose value remained 1 (potential bottom)
    for key,val := range potential_bottoms {
        if val == 1 {
            bottom_name = key;
        }   //end if
    }   //end for
    return bottom_name;
}   //end func





// A structure to represent a program from the task
type Program struct {
    name string
    weight int
    holding []string
}   //end type


// creates a new Program and returns a pointer to it
func NewProgram(new_name string, new_weight int, new_holding []string) *Program {
    var new_program = new(Program);
    new_program.name    = new_name;
    new_program.weight  = new_weight;
    new_program.holding = new_holding;
    return new_program;
}   //end func
