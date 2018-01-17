package main

import (
    hasher "../day_10"
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // the number of rows in the the hashed matrix
    const matrix_rows = 128;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();


        // PART I
        // hash the input and insert it in each row
        var hash_matrix = hash_each_row(input, matrix_rows);

        
        // PART II
        //
    }   //end for
}   //end main



// hashes the input string for each row of the matrix and fills in the row
func hash_each_row(input string, matrix_row_count int) (hash_matrix [][]int) {
        println("Hashing ...")
        // for each row of the matrix
        for i := 0; i < matrix_row_count; i++ {
            // convert the row index to a string
            var row_idx = strconv.Itoa(i);
            // run knot hash and get a hash for this specific row
            var hashed_row = hasher.Get_knot_hash(strings.Join([]string{input, row_idx}, "-"), 256);

            // convert the hashed row from hexadecimal to binary
            hashed_row = hex_hash_to_binary(hashed_row);
        }   //end for
        println("Done Hashing!")
        //TODO: finish whatever needs to be done
}   //end func



// converts a hash from hexadecimal to binary
func hex_hash_to_binary(hex_hash string) (bin_hash string) {
    // for each character in the hex string
    for _, char := range strings.Split(hex_hash, "") {
        // convert the character to a hexadecimal digit
        var int_hex,_ = strconv.ParseInt(char, 16,0);
        // convert the hexadecimal digit to a binary one
        var int_bin = fmt.Sprintf("%04b", int_hex);

        //TODO: finish with whatever has to be done
    }   //end for
}   //end func
