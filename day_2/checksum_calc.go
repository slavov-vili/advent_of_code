package main

import (
    "bufio"
    "math"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // stores the function to run the checksum calculator
    // based on which part of the assignment is being run
    var run_checksum_calc func([][]int)int;
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores each row of the matrix
    var input_matrix [][]int;

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
        if part == 1 { run_checksum_calc = run_checksum_calc_min_max }
        if part == 2 { run_checksum_calc = run_checksum_calc_even_div }
        break;
    }   //end if

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if strings.Compare(input, "END") != 0 {
            // stores each line of input after it has been converted to an int
            var row_ints []int;
            // for each number in the input strings
            for _,num_str := range strings.Fields(input) {
                // convert the number to an int
                num_int,_ := strconv.Atoi(num_str);
                // add it to the list
                row_ints = append(row_ints, num_int);
            }   //end for

            // add the new row to the matrix
            input_matrix = append(input_matrix, row_ints);
            continue;
        }   //end if

        // run the calculator and print the sum
        var sum = run_checksum_calc(input_matrix);
        print("Sum: ")
        print(sum)
        println()
    }   //end for
}   //end main


// Runs the checksum calculator
// adding the differences between the min and max numbers in each row
//
// Arguments:
// input - the input to the calculator (each element in the array is a row in the matrix)
//
// Returns:
// the sum of the differences between the max and the min in each row
func run_checksum_calc_min_max(input_matrix [][]int) int {
    // store the sum of the matrix
    var sum = 0;

    // for each row of the input
    for _,row := range input_matrix {
        // set the initial min and max
        var min = row[0];
        var max = row[0];

        // for each number in the row
        for _, cur_num := range row {
            // adjust min and max
            if cur_num < min { min = cur_num }
            if cur_num > max { max = cur_num }
        }   //end for

        // add the difference between this row's max and min
        // to the overall sum
        sum += (max - min)
    }   //end for

    return sum;
}   //end func


// Runs the checksum calculator
// adding the results of the divisions of the two evenly divisible numbers in each row
//
// Arguments:
// input - the input to the calculator (each element in the array is a row in the matrix)
//
// Returns:
// the sum of the divisions between the two equally divisible numbers in each row
func run_checksum_calc_even_div(input_matrix [][]int) int {
    // store the sum of the matrix
    var sum = 0;
    // the result of the division of the two numbers
    var div_res = 0;

    // for each row of the input
    for _,row := range input_matrix {
        // for each number in the row
        for i,cur_num := range row {

            // check if the current number is divisible (or divides) any numbers, coming after it
            for _,next_num := range row[(i+1):] {

                // if the current number DIVIDES the next one
                if (next_num % cur_num) == 0 {
                    div_res = next_num / cur_num;
                // if the current number IS DIVISIBLE BY the next one
                } else if (cur_num % next_num) == 0 {
                    div_res = cur_num / next_num;
                }   //end if
            }   //end for
        }   //end for

        // add the division of the two divisible numbers from THIS row
        // to the overall sum
        sum += div_res;
    }   //end for

    return sum;
}   //end func
