package main

import (
    "bufio"
    "fmt"
    "os"
    //"sort"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores which part of the assignment is being run
    var part int;

    // convert the very first input to an int and store it
    println("Testing Part: ")
    for scanner.Scan() {
        part,_ = strconv.Atoi(scanner.Text());
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
        // store the input
        var input = strings.Split(scanner.Text(), ",");

        // make the rope, where the knots will be made
        var rope_size = 256;
        var rope = make([]int, rope_size);
        // fill in the list of numbers
        for i := 0; i < rope_size; i++ {
            rope[i] = i;
        }   //end for

        switch part {
            // if part 1 is being run
            case 1: {
                var cur_pos = 0;
                var skip_size = 0;

                // convert the input to integers
                var input_int = make([]int, len(input));
                for i, str := range input {
                    input_int[i],_ = strconv.Atoi(str);
                }   //end for
                
                // run the knot tyer and print the hashed rope's information
                rope, cur_pos, skip_size = run_knot_tyer(rope, input_int, cur_pos, skip_size);
                fmt.Println("Rope: ", rope);
                println("1st * 2nd = ",       (rope[0] * rope[1]))
                println("current position: ", cur_pos)
                println("skip size: ",        skip_size)
            }   //end case
            case 2: {
                //var cur_pos = 0;
                //var skip_size = 0;
            
            }   //end case
        }   //end switch
    }   //end for
}   //end main



// runs the know tyer on the list of numbers and returns the new list
func run_knot_tyer(rope []int, lengths []int,
                   start_pos int, init_skip_size int) (rope_hashed[]int, cur_pos int, skip_size int) {
    // make a copy of the argument array, because we don't want to change the argument
    rope_hashed = make([]int, len(rope));
    copy(rope_hashed, rope);
    // set the initial cur_pos and skip_size
    cur_pos   = start_pos;
    skip_size = init_skip_size;

    // for each of the lengths
    for _, length := range lengths {
        // reverse the next 'length' elements in the array
        reverse_next_n(rope_hashed, cur_pos, length);

        // calculate the new current position
        // if its bigger than the size of the rope, it will wrap around
        cur_pos = (cur_pos + length + skip_size) % len(rope_hashed);
        // increase the skip size
        skip_size++;
    }   //end for
    return;
}   //end func



// reverses the next n elements in the array
// if the index is outside the array, it wraps around
func reverse_next_n(arr []int, cur_pos int, n int) {
    // stores the indices of the next n elements
    var reverse_indices []int;
    // stores the values of the next n elements
    var reverse_values  []int;

    // get all the indices
    for i := 0; i < n; i++ {
        reverse_indices = append(reverse_indices, ((cur_pos + i) % len(arr)));
    }   //end for

    // get all the values
    for _, idx := range reverse_indices {
        reverse_values = append(reverse_values, arr[idx]);
    }    //end for

    //fmt.Println("to reverse: ", reverse_indices);
    //fmt.Println("unreversed: ",  reverse_values);

    // reverse the selected values
    for i := 0; i < (len(reverse_values)/2); i++ {
        var temp = reverse_values[i];
        reverse_values[i] = reverse_values[len(reverse_values) - 1 - i];
        reverse_values[len(reverse_values) - 1 - i] = temp;
    }

    //fmt.Println("reversed: ",  reverse_values);

    // swap the values at the selected indices in the real array
    // with the reversed values
    for i, idx := range reverse_indices {
        arr[idx] = reverse_values[i];
    }   //end for
    return;
}   //end func
