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
        var active_bit_count = 0;
        // for each row in the matrix
        for _, row := range hash_matrix {
            // for each element of that row
            for _, el := range row {
                if el == 1 { active_bit_count++; };
            }   //end for
        }   //end for
        println("There are", active_bit_count, "active bits!");

        
        // PART II
        //
    }   //end for
}   //end main



// hashes the input string for each row of the matrix and fills in the row
func hash_each_row(input string, matrix_row_count int) (hash_matrix [][]int) {
    hash_matrix = make([][]int, matrix_row_count);
    println("Hashing ...")
    // for each row of the matrix
    for i := 0; i < len(hash_matrix); i++ {
        // convert the row index to a string
        var row_idx = strconv.Itoa(i);
        // run knot hash and get a hash for this specific row
        var hashed_row = hasher.Get_knot_hash(strings.Join([]string{input, "-", row_idx}, ""), 256);

        // convert the hashed row from hexadecimal to binary
        hashed_row = hex_hash_to_binary(hashed_row);

        // for each bit in the binary representation of the hash
        for _, bit := range strings.Split(hashed_row, "") {
            // convert the bit to an integer
            var bit_int, _ = strconv.Atoi(bit);
            // append the bit to the current row of the matrix
            hash_matrix[i] = append(hash_matrix[i], bit_int);
        }   //end for
    }   //end for
    println("Done Hashing!")
    return;
}   //end func



// converts a hash from hexadecimal to binary
func hex_hash_to_binary(hex_hash string) (bin_hash string) {
    // for each character in the hex string
    for _, char := range strings.Split(hex_hash, "") {
        // parse the character as a hexadecimal digit
        var int_hex, _ = strconv.ParseInt(char, 16, 0);
        // convert the hexadecimal digit to a binary one
        var int_bin = fmt.Sprintf("%04b", int_hex);

        // append the converted digit to binary hash
        bin_hash = strings.Join([]string{bin_hash, int_bin}, "");
    }   //end for
    return;
}   //end func
