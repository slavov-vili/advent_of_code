package main

import (
    "io/ioutil"
    "os"
)   //end imports


func main() {
    var args = os.Args;

    // read the input from the file given as an argument
    // !!! HAD TO USE FILE, BECAUSE INPUT IS > DEFAULT STDIN BUFFER SIZE (4096) !!!
    var input,read_err = ioutil.ReadFile(args[1]);
    if read_err != nil {
        println("ERROR: ", read_err.Error())
    }   //end if

    // run the trash counter and get the total number of trash characters in the input
    var trash_count = run_trash_counter(string(input));
    print("There are [")
    print(trash_count)
    print("] trash characters in this stream.")
    println()
}   //end main



// iterates over the input and counts all unescaped trash characters
func run_trash_counter(input string) (count int) {
    var i = 0;
    // while the character index is within the input
    for i < len(input) {
        var char = input[i];
        // if the character opens trash
        if char == '<' {
            // iterate over the trash
            for char != '>' {
                i++;
                char = input[i];

                // if the next character is the escape character
                if char == '!' {
                    // skip the next character
                    i++;
                // if its any other character
                } else if char != '>' {
                    count++;
                }   //end if
            }   //end for
        }   //end if

        i++;
    }   //end for
    return;
}   //end func
