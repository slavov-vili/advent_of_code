package main

import (
    "bufio"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // stores the function to run the checksum calculator
    // based on which part of the assignment is being run
    var run_checksum_calc func([]string)
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores each row of the matrix
    var input_rows []string

    // convert the very first input to an int and store it
    for (scanner.Scan()) {
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
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if strings.Compare(input, "END") != 0 {
            // add the current input to the array
            input_rows = append(input_rows, input)
            continue;
        }   //end if

        // TODO: convert the input to ints

        run_checksum_calc(input_rows)
    }   //end for
}   //end main


// Runs the checksum calculator
// adding the differences between the min and max numbers in each row
//
// Arguments:
// input - the input to the calculator (each element in the array is a row in the matrix)
func run_checksum_calc_min_max(input []string) {
    // store the sum of the matrix
    var sum = 0;

    // for each row of the input
    for _,row := range input {
        // split the row into its numbers
        var numbers = strings.Fields(row);
        // set the initial min and max
        min,_ := strconv.Atoi(numbers[0]);
        max,_ := strconv.Atoi(numbers[0]);

        // for each number in the list
        for _, num := range numbers {
            // store the current number
            cur_num, _ := strconv.Atoi(num);
            // adjust min and max
            if cur_num < min { min = cur_num }
            if cur_num > max { max = cur_num }
        }   //end for

        // add the difference between this row's max and min
        // to the overall sum
        sum += (max - min)
    }   //end for

    // print the overall sum
    print("Sum: ")
    print(sum)
    println()
}   //end func


// Runs the checksum calculator
// adding the results of the divisions of the two evenly divisible numbers in each row
//
// Arguments:
// input - the input to the calculator (each element in the array is a row in the matrix)
func run_checksum_calc_even_div(input []string) {
    // store the sum of the matrix
    var sum = 0;
    // the result of the division of the two numbers
    var div_res = 0;

    // for each row of the input
    for _,row := range input {
        // split the row into its numbers
        var nums_str = strings.Fields(row);
        var nums_int []int
        // for each number in the list
        for _, num_str := range nums_str {
            // convert the number from string to int
            num_int,_ := strconv.Atoi(num_str);
            // add the number to the list of ints
            nums_int = append(nums_int, num_int);
        }   //end for

        // for each number in the row
        for i,cur_num := range nums_int {

            // check if the current number is divisible (or divides) any numbers, coming after it
            for _,next_num := range nums_int[(i+1):] {

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

    // print the overall sum
    print("Sum: ")
    print(sum)
    println()

}   //end func
