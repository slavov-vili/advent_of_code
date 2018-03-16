package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // TODO: store the input
            continue;
        }   //end if


        // PART I


        // PART II

        // TODO: clear the input container
    }   //end for
}   //end main
