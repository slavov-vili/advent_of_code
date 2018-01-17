package main

import (
    "../day_10"
    "bufio"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores the hash matrix
    var hash_matrix;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // initialize the matrix with 128 rows
        hash_matrix = make([][]int, 128);
        // store the current input
        var input = scanner.Text();


        // PART I
        // hash the input and insert it in each row
        println("Hashing ...")
        for 
        println("Done Hashing!")

        
        // PART II
        //
    }   //end for
}   //end main
