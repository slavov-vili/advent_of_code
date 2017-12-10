package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // stores the function to run the redistribution algorithm
    // based on which part of the assignment is being run
    var redistribute_memory func([]int) int
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // convert the very first input to an int and store it
    println("Testing Part: ")
    for scanner.Scan() {
        part,_ := strconv.Atoi(scanner.Text())
        // if an invalid part is given
        if (part < 1) || (part > 2) {
            println("Invalid part of the assignment !")
            println("Try again: ")
            continue;
        }   //end if
        // store the correct function for the particular part of the assignment
        switch part {
        case 1: redistribute_memory = redistribute_memory_next_inc;
        //case 2: calc_idx = calc_mid_idx;
        }   //end switch
        break;
    }   //end for

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the input as strings
        var input_str = strings.Fields(scanner.Text());
        // store the input as ints
        var input_int = make([]int, len(input_str));
        
        // convert the input from strings to ints
        // and add them to the list
        for i,num_str := range input_str {
            num_int,_ := strconv.Atoi(num_str);
            input_int[i] = num_int;
        }   //end for

        // run the redistribution algorithm and print the needed number of redistributions
        var redistributions_required = redistribute_memory(input_int);
        print("Ballance can be restored in ")
        print(redistributions_required)
        print(" redistribution cycles")
        println()
    }   //end for
}   //end main


// Redistributes the memory blocks in the array 
//
// Arguments:
// memory - the array, whose blocks need redistribution
//
// Returns:
// the number of redistributions required until an infinite loop is reached
func redistribute_memory_next_inc(memory []int) (count int) {
    // stores the memory sequences which were already seen
    var seen = make(map[string]bool, 0);

    for {
        // get the maximum value in the array
        var max_i = get_max_index(memory);

        // run 1 cycle of redistribution
        memory = run_redistribution_cycle(memory, max_i);
        count++;
        // convert the memory to a string
        var memory_str_array = make([]string, 0);
        for _,block_int := range memory {
            var block_str = strconv.Itoa(block_int);
            memory_str_array = append(memory_str_array, block_str);
        }   //end for
        var memory_str = strings.Join(memory_str_array, "");

        // if this memory allocation HAS already been seen
        if seen[memory_str] == true {
            break;
        // if it HASN'T been seen
        } else {
            seen[memory_str] = true;
        }   //end if
    }   //end for
    return;
}   //end func


// Runs one cycle of memory redistribution
//
// Arguments:
// memory - the memory which needs to be redistributed
// max_i  - the index of the max element in the array
//
// Returns:
// the newly redistributed memory
func run_redistribution_cycle(memory []int, max_i int) []int {
    // store the memory which needs to be redistributed
    var free_memory = memory[max_i];
    var next_idx = max_i;
    // clear the memory block, which was taken
    memory[max_i] = 0;

    // while there is memory to go around
    for free_memory > 0 {
        // calculate the index of the next cell to be incremented
        next_idx = (next_idx + 1) % len(memory);
        // increment the next cell
        memory[next_idx]++;
        // decrement the free memory
        free_memory--;
    }   //end for
    return memory;
}   //end func


// returns the index of the max number from an array
// in the case of a tie, 
func get_max_index(arr []int) int {
    // store the temp maximum value and its index
    var max_i = 0;
    var max = arr[max_i];

    // for each value in the array
    for i,val := range arr {
        // if the new value is higher than the previous max
        if (val > max) {
            max_i = i;
            max = val;
        }   //end if
    }   //end for
    return max_i;
}   //end func
