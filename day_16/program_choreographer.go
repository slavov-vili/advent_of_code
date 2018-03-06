package main

import (
    "fmt"
    "io/ioutil"
    "log"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    var args = os.Args;
    // stores the number of letters which participate in the dance
    const dancer_count = 16;

    // read the input from the file given as an argument
    var input, read_err = ioutil.ReadFile(args[1]);
    if read_err != nil {
        log.Fatal(read_err);
    }   //end if
    // remove the newline symbol
    input = input[:(len(input)-1)];

    // split the input into the separate dance moves
    var moves = strings.Split(string(input), ",");
    // generate the sequence of letters
    var dancers = collect_dancers(dancer_count);


    // PART I
    // for each dance move
    for _, move := range moves {
        dancers = handle_move(move, dancers);
    }   //end for
    fmt.Println(strings.Join(dancers, ""));

    // PART II
}   //end main



// moves the dancers according to the given move
func handle_move(move string, dancers []string) []string {
    // copy the dancers into a new list
    var new_dancers = make([]string, len(dancers));
    copy(new_dancers, dancers);

    var parts = strings.Split(move, "");
    var move_head = parts[0];
    var move_tail = strings.Join(parts[1:], "");
    switch move_head {
    // if the move is a split
    case "s":
        // combine the rest of the move into a string and convert it to an integer
        var spin_count, err = strconv.Atoi(move_tail);
        if err != nil { log.Fatal(err) }

        // store the range of dancers who stay
        var stay = len(new_dancers) - spin_count;
        // perform the spin move
        new_dancers = append(new_dancers[stay:], new_dancers[:stay]...)
    // if the move is a swap by position
    case "x":
        // store the indices of the two elements that need to be swapped
        var indices = get_move_args_int(move_tail);

        // perform the swap move by position
        swap(new_dancers, indices[0], indices[1]);
    // if the move is a swap by name
    case "p":
        // store the names of the two elements that need to be swapped
        var names   = get_move_args(move_tail);
        // store the indices of the elements
        var indices = get_dancer_positions(new_dancers, names);

        // perform the swap move by name
        swap(new_dancers, indices[0], indices[1]);
    }   //end switch
    return new_dancers;
}   //end func



// creates a list of the first X letters of the alphabet
func collect_dancers(count int) (dancers []string) {
    const ascii_value_a = 97;
    for i:=0; i<count; i++ {
        dancers = append(dancers, string(ascii_value_a + i));
    }   //end for
    return;
}   //end func



// finds the positions of the dancers with the given names
func get_dancer_positions(dancers []string, names []string) (positions []int) {
    // for each name in the random list
    for _, name_random := range names {
        // for each name in the dancer list
        for i, name_dancer := range dancers {
            // if the two names are the same
            if name_random == name_dancer {
                // add the dancer's position to the list
                positions = append(positions, i);
            }   //end for
        }   //end
    }   //end for
    return;
}   //end func



// extracts the arguments to the dance move given its tail as a string and converts them to integers
func get_move_args_int(move_tail string) (move_args_int []int) {
    // get the arguments as strings
    for _, arg_string := range get_move_args(move_tail) {
        // convert each argument to an int
        var arg_int, err = strconv.Atoi(arg_string);
        if err != nil { log.Fatal(err) }

        // add the argument to the list
        move_args_int = append(move_args_int, arg_int);
    }   //end for
    return;
}   //end func


// extracts the arguments to the dance move given its tail as a string
func get_move_args(move_tail string) []string {
    return strings.Split(move_tail, "/");
}   //end func



// swaps the two elements at the corresponding indices in the slice
func swap(dancers []string, idx1 int, idx2 int) {
    var temp = dancers[idx1];
    dancers[idx1] = dancers[idx2];
    dancers[idx2] = temp;

    return;
}   //end func
