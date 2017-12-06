package main

import (
    "bufio"
    "os"
    "strings"
    "strconv"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);

    // while input is being received
    for (scanner.Scan()) {
        var sum = 0;
        // store the input
        var input = scanner.Text();
        // split the input into characters, because strings are weird in Go
        var input_chars = strings.Split(input, "");
        var input_len   = len(input_chars);

        // for each character in the input
        for i:=0; i<input_len; i++ {
            // calculate the index of the next number to be considered
            var y = calc_next_idx(i, input_len);
            // if this number and the next one are equal, add it to the sum

            if (strings.Compare(input_chars[i], input_chars[y]) == 0) {
                i_int, err := strconv.Atoi(input_chars[i]);
                if (err != nil) {
                    println(err);
                }   //end if
                sum += i_int;
            }   //end if
        }   //end for

        print("Sum: ");
        print(sum);
        printl()
    }   //end for
}   //end main


// Calculates the index of the next number in the captcha.
// The captcha is just a sequence of integers.
//
// Arguments:
// cur_idx - the index of the current number in the captcha
// length  - the length of the captcha
//
// Returns:
// the index of the next number to be considered in the captcha
func calc_next_idx(cur_idx, length int) int {
    return (cur_idx + 1) % length;
}   //end func

