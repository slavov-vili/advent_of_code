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

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the input
        var input_str = strings.Split(scanner.Text(), ",");
        var input_int = make([]int, len(input_str));
        // convert the input to integers
        for i, str := range input_str {
            input_int[i],_ = strconv.Atoi(str);
        }   //end for

        var rope_size = 256;

        // run the calculator and print the sum
        var rope = run_knot_tyer(input_int, rope_size);
        fmt.Println(rope);
        print("1st * 2nd = ")
        print(rope[0] * rope[1])
        println()
    }   //end for
}   //end main



// runs the know tyer on a list with the numbers [0-255] and returns the new list
func run_knot_tyer(lengths []int, rope_size int) (rope []int) {
    var skip_size = 0;
    var cur_pos   = 0;

    // fill in the list of numbers
    for i := 0; i < rope_size; i++ {
        rope = append(rope, i);
    }   //end for

    // for each of the lengths
    for _, length := range lengths {
        // reverse the next 'length' elements in the array
        reverse_next_n(rope, cur_pos, length);

        // calculate the new current position
        // if its bigger than the size of the rope, it will wrap around
        cur_pos = (cur_pos + length + skip_size) % len(rope);
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

    fmt.Println("to reverse: ", reverse_indices);
    fmt.Println("unreversed: ",  reverse_values);

    // reverse the selected values
    for i := 0; i < (len(reverse_values)/2); i++ {
        var temp = reverse_values[i];
        reverse_values[i] = reverse_values[len(reverse_values) - 1 - i];
        reverse_values[len(reverse_values) - 1 - i] = temp;
    }

    fmt.Println("reversed: ",  reverse_values);

    // swap the values at the selected indices in the real array
    // with the reversed values
    for i, idx := range reverse_indices {
        arr[idx] = reverse_values[i];
    }   //end for
    return;
}   //end func
