package main

import (
    "bufio"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // stores the function to calculate the next relevant index
    // based on which part of the assignment is being run
    var calc_idx func(int, int) int
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // convert the very first input to an int and store it
    for scanner.Scan() {
        part,_ := strconv.Atoi(scanner.Text())
        // if an invalid part is given
        if (part < 1) || (part > 2) {
            println("Invalid part of the assignment !")
            println("Try again: ")
            continue;
        }   //end if
        // store the correct function for the particular part of the assignment
        if part == 1 { calc_idx = calc_next_idx }
        if part == 2 { calc_idx = calc_mid_idx  }
        break;
    }   //end if

    // while input is being received
    for scanner.Scan() {
        // store the input
        var input = scanner.Text();
        run_captcha_calc(input, calc_idx)
    }   //end for
}   //end main



// Run the captcha calculator, taking into account which part of the assignment is being tested
//
// Arguments:
// input - the input to the calculator
// calc_idx - the function, which calculates the index of the next number to be consideted
//
// Returns:
// the sum of equal numbers based on the criterion
func run_captcha_calc(input string, calc_idx func(int, int)int) {
        var sum = 0;
        // split the input into characters, because strings are weird in Go
        var input_chars = strings.Split(input, "");
        var input_len   = len(input_chars);

        // for each character in the input
        for i:=0; i<input_len; i++ {
            // calculate the index of the next number to be considered
            var y = calc_idx(i, input_len);
            // if this number and the next one are equal, add it to the sum

            if strings.Compare(input_chars[i], input_chars[y]) == 0 {
                i_int, err := strconv.Atoi(input_chars[i]);
                if err != nil {
                    println(err);
                }   //end if
                sum += i_int;
            }   //end if
        }   //end for

        print("Sum: ");
        print(sum);
        println()
}   //end func



// Calculates the index of the next number in the captcha.
//
// Arguments:
// cur_idx - the index of the current number in the captcha
// length  - the length of the captcha
//
// Returns:
// the index of the next number to be considered in the captcha
func calc_next_idx(cur_idx, length int) int {
    return ((cur_idx + 1) % length);
}   //end func


// Calculates the index of the number n/2 steps away from the current one.
//
// Arguments:
// cur_idx - the index of the current number in the captcha
// length  - the length of the captcha
//
// Returns:
// the index of the number n/2 steps further in the captcha
func calc_mid_idx(cur_idx, length int) int {
    return ((cur_idx + (length/2)) % length)
}   //end func
