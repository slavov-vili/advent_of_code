//package main
package day_10

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
        var input = scanner.Text();

        // make the rope, where the knots will be made
        var rope_size = 256;

        switch part {
            // if part 1 is being run
            case 1: {
                var rope = make([]int, rope_size);
                // fill in the list of numbers
                for i := 0; i < rope_size; i++ {
                    rope[i] = i;
                }   //end for

                var cur_pos = 0;
                var skip_size = 0;
                var input_split = strings.Split(input, ",");

                // convert the input to integers
                var input_int = make([]int, len(input_split));
                for i, str := range input_split {
                    input_int[i],_ = strconv.Atoi(str);
                }   //end for
                
                // run the knot tyer and print the hashed rope's information
                rope, cur_pos, skip_size = run_knot_tyer(rope, input_int, cur_pos, skip_size);
                fmt.Println("Rope: ", rope);
                println("1st * 2nd = ",       (rope[0] * rope[1]))
                println("current position: ", cur_pos)
                println("skip size: ",        skip_size)
            }   //end case
            // if part 2 is being run
            case 2: {
                var knot_hash = Get_knot_hash(input, rope_size);

                fmt.Println("Knot hash: ", knot_hash)
            }   //end case
        }   //end switch
    }   //end for
}   //end main



// runs the knot hash algorithm on the input and returns the result
func Get_knot_hash(input string, rope_size int) (knot_hash string) {
    var rope = make([]int, rope_size);
    // fill in the list of numbers
    for i := 0; i < rope_size; i++ {
        rope[i] = i;
    }   //end for

    // the size of the blocks used when making the hash denser
    var block_size = 16;
    var cur_pos = 0;
    var skip_size = 0;
    var length_suffix = []int{17, 31, 73, 47, 23};

    // convert the input to integers based on ASCII codes
    var input_int = make([]int, len(input));
    // for each character in the input
    for i, char := range input {
        // convert the character to its ASCII code
        input_int[i] = int(char);
    }   //end for

    // after the input is done, add the standard length suffix
    input_int = append(input_int, length_suffix...);

    // run 64 rounds of the hashing
    for i:= 0; i<64; i++ {
        rope, cur_pos, skip_size = run_knot_tyer(rope, input_int, cur_pos, skip_size);
    }   //end for

    // make the hash denser
    rope = densen_hash_xor(rope, block_size);

    // for all numbers in the array
    for _, num_int := range rope {
        // get the hexadecimal representation of the number as a STRING
        var num_hex = fmt.Sprintf("%x", num_int);
        // if the result is a single value
        if len(num_hex) < 2 {
            // prepend a 0 to the hexadecimal code
            num_hex = "0" + num_hex;
        }   //end if

        knot_hash += num_hex;
    }   //end for
    return;
}   //end func



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



// makes a sparse hash denser using bitwise XOR on 'block_size'-long blocks
func densen_hash_xor(sparse_hash []int, block_size int) (dense_hash []int) {
    // stores the starting position of the current block
    var block_start = 0;

    // for each block in the sparse hash
    for block_start < len(sparse_hash) {
        var block_end = block_start + block_size;
        var cur_block []int;
        // stores the value of the XOR'd block
        var block_xor   = 0;

        // if the block ends inside the slice
        if block_end < len(sparse_hash) {
            cur_block = sparse_hash[block_start : block_end];
        } else {
            cur_block = sparse_hash[block_start:];
        }   //end if

        // for each value in the current block
        for _, val := range cur_block {
            block_xor ^= val;
        }   //end for

        dense_hash = append(dense_hash, block_xor)
        block_start += block_size;
    }   //end for

    return;
}   //end func
