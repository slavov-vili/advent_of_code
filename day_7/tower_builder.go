package main

import (
    "bufio"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // maps the name of each program from the input to a pointer to the actual program
    var input = make(map[string]*Program);

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input line
        var input_line = scanner.Text();

        // if the input line is NOT "END"
        if (input_line != "END") {
            // stores the names of programs being held by the current one
            var holding []string;

            // separate the name and weight from the list of programs being held
            var parts = strings.Split(input_line, "->");

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

            // add the new input line to the map
            input[name] = NewProgram(name, weight_int, holding);
            continue;
        }   //end if

        // run the tower builder
        var bottom = run_tower_builder(input);

        // print the name of the bottom program
        print("Bottom: ")
        print(bottom.name)
        println()

        // maps a program name to its total weight (own weight + weights of all children)
        var total_weights  = make(map[string]int);
        // run the tower stabilizer to find the name of th tower with wrong weight and its correct total weight
        var wrong_name, correct_weight = run_tower_stabilizer(total_weights, input, bottom);

        // for each tower being held by the wrong tower
        for _, held_name := range input[wrong_name].holding {
            correct_weight -= total_weights[held_name];
        }   //end for

        // print the correct weight for the wrong node
        print("The correct weight for tower '")
        print(wrong_name)
        print("' is:")
        print(correct_weight)
        println()

        // clean the input lines
        input = make(map[string]*Program);
    }   //end for
}   //end main



// Runs the tower stabilizer
// the stabilizer recursively calculates the weights of programs (own weight + weight of all children)
// and checks whether the programs are stable (whether the weights of all children are the same)
//
// Arguments:
// total_weights - the total weights of programs (own weight + weights of all children)
// input         - the data of program names mapped to the respective program
// node          - the node, where the stabilizer starts
//
// Returns:
// if the tower is stable - the name of the node, whose weight was added to the total list
// else - the name of the program, whose weight is wrong + the correct weight
func run_tower_stabilizer(total_weights map[string]int, input map[string]*Program, cur_prog *Program)  (name string, correct_weight int) {
    // if the node is a leaf node
    if len(cur_prog.holding) == 0 {
        // add the node's weight to the map of total weights
        total_weights[cur_prog.name] = cur_prog.weight;
        //println("returning: ")
        //print(cur_prog.weight)
        // return its weight and say it is stable
        return cur_prog.name, cur_prog.weight;
    }   //end if

    // maps a tower weight to the names of towers who have this weight
    var held_weights = make(map[int][]string);
    // for each program being held by the current one
    for _, held_name := range cur_prog.holding {
        // recursively run the stabilizer
        _, correct_weight := run_tower_stabilizer(total_weights, input, input[held_name]);

        // if the held tower is NOT stable
        if total_weights[held_name] != correct_weight {
            // return the name of the program which is unstable
            return held_name, correct_weight;
        // if it IS stable
        } else {
            // map the program's weight to its name
            var held_weight = total_weights[held_name];
            held_weights[held_weight] = append(held_weights[held_weight], held_name);
        }   //end else
    }   //end for

    // for each weight and its list of program names
    for held_weight, held_programs := range held_weights {
        switch len(held_programs) {
        // if a weight has been encountered three times
        // then the current tower is stable
        case 3: {
            var cur_prog_total_weight = (3 * held_weight) + cur_prog.weight;
            // add the current program's total weight to the map
            total_weights[cur_prog.name] = cur_prog_total_weight;
            // set the name to be the name of the current program
            name = cur_prog.name;
            // set the program as stable
            correct_weight = cur_prog_total_weight;
        }   //end case
        // if a weight has been encountered twice
        // then the current tower is NOT stable and the correct weight of all towers being held should be
        case 2: {
            correct_weight = held_weight;
        }   //end case
        // if a weight has been encountered once
        // then the current tower is NOT stable and this weight is wrong
        case 1: {
            // set the name to be the name of the program, whose weight is wrong
            name = held_weights[held_weight][0];
        }   //end case
        }   //end switch
    }   //end for

    return;
}   //end func



// Runs the tower builder and finds the name of the bottom of the tower
//
// Arguments:
// input - a map between program name and the program information
//
// Returns:
// the name of the bottom of the tower
func run_tower_builder(input map[string]*Program) *Program {
    var bottom *Program;
    // maps the name of a Program to:
    //  1 - if it IS NOT being held by any other program
    //  0 - if it hasn't been seen yet
    // -1 - if it IS being held by another program
    var potential_bottoms = make(map[string]int);

    // for each program in the input
    for prog_name,prog := range input {
        // if a program isn't holding anything then it cannot be at the bottom
        if len(prog.holding) == 0 { continue; }

        // if the program hasn't been seen before
        if potential_bottoms[prog_name] == 0 {
            // add the current program as a possible bottom
            potential_bottoms[prog_name] = 1;
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
            bottom = input[key];
        }   //end if
    }   //end for
    return bottom;
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
