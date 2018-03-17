package main

import (
    "bufio"
    "fmt"
    "log"
    "math"
    "os"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores the input matrix
    var nodes = make([][]string, 0);

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // split the line of input into its characters and add them to the node map
            nodes = append(nodes, strings.Split(input, ""));
            continue;
        }   //end if


        // PART I


        // PART II


        // clear the input map
        nodes = make([][]string, 0);
    }   //end for
}   //end main





type 
