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

    // run the group counter and get the total score
    var score = run_group_counter(string(input));
    print("Total score: ")
    print(score)
    println()
}   //end main



func run_group_counter(input string) (score int) {
        // counts how many groups have been opened
        var total_groups_started, total_groups_closed int;
        var total_trashes_started, total_trashes_closed int;
        var groups_started int;
        var trash_has_started bool;

        // for each character in the input
        for i, char := range input {
            switch {
            // if the character is the beginning of trash
            // and it is outside of trash
            case (char == '<') && (trash_has_started == false): {
                // tell the program, that trash content has started
                trash_has_started = true;
                total_trashes_started++;
            }   //end case
            // if the character is the un-escaped end of trash
            case (char == '>') && (is_escaped(input, i) == false): {
                // tell the program, that trash content has ended
                trash_has_started = false;
                total_trashes_closed++;
            }   //end case
            // if the character is the beginning of a group
            // and it is outside trash
            case (char == '{') && (trash_has_started == false): {
                total_groups_started++;
                // save the starting index of the group
                groups_started++;
            }   //end case
            // if the character is the end of a group
            // and it is outside trash
            case (char == '}') && (trash_has_started == false): {
                total_groups_closed++;
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
func is_escaped(input string, idx int) (result bool) {
    var prev_idx = idx - 1;
    // while the previous character is the escape character
    for input[prev_idx] == '!' {
        result = !result;
        prev_idx -= 1;
    }   //end for
    return;
}   //end func
