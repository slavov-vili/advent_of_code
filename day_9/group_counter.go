package main

import (
    "bufio"
    //"fmt"
    "os"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the input
        var input_chars = strings.Split(scanner.Text(), "");

        // run the group counter and get the total score
        var score = run_group_counter(input_chars);
        print("Total score: ")
        print(score)
        println()
    }   //end for
}   //end main



func run_group_counter(input []string) (score int) {
        // counts how many groups have been opened
        var groups_started = 0;
        var trash_has_started  = false;

        // for each character in the input
        for i, char := range input {
            switch {
            // if the character is the beginning of trash
            // and it is outside of trash
            case (char == "<") && (trash_has_started == false): {
                // tell the program, that trash content has started
                trash_has_started = true;
            }   //end case
            // if the character is the un-escaped end of trash
            case (char == ">") && (is_escaped(input, i) == false): {
                // tell the program, that trash content has ended
                trash_has_started = false;
            }   //end case
            // if the character is the beginning of a group
            // and it is outside trash
            case (char == "{") && (trash_has_started == false): {
                // save the starting index of the group
                groups_started++;
            }   //end case
            // if the character is the end of a group
            // and it is outside trash
            case (char == "}") && (trash_has_started == false): {
                score += groups_started;
                // close the last opened group
                groups_started--;
            }   //end case
            }   //end switch
        }   //end for
    //}   //end if
    return;
}   //end func



// checks whether the character with index 'idx' in the contents is escaped
func is_escaped(input []string, idx int) (result bool) {
    var prev_idx = idx -1;
    // while the previous character is the escape character
    for input[prev_idx] == "!" {
        result = !result;
        prev_idx -= 1;
    }   //end for
    return;
}   //end func
