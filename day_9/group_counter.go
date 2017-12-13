package main

import (
    "bufio"
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
        // pass the contents of the first group as argument
        var score = run_group_counter(get_group_contents(input_chars), 1);
        print("Total score: ")
        print(score)
        println()
    }   //end for
}   //end main



func run_group_counter(group_contents []string, cur_group_base_score int) (total_score int) {
    total_score = cur_group_base_score;

    // if the group only contains trash (or contains nothing)
    if (len(group_contents) == 0) || (is_trash(group_contents) == true) {
        return;
    } else {
        var (
            // stores the indices of characters, which begin groups
            group_starts = make([]int, 0);
            trash_started = false;
        )   //end var

        // for each character in the contents
        for i, char := range group_contents {
        switch {
        // if the character is the beginning of trash
        // and it is outside of trash
        case (char == "<") && (trash_started == false): {
            // tell the program, that trash content has started
            trash_started = true;
        }   //end case
        // if the character is the un-escaped end of trash
        case (char == ">") &&
             (is_escaped(group_contents, i) == false): {
            // tell the program, that trash content has ended
            trash_started = false;
        }   //end case
        // if the character is the beginning of a group
        // and it is outside trash
        case (char == "{") && (trash_started == false): {
            // save the starting index of the group
            group_starts = append(group_starts, i);
        }   //end case
        // if the character is the end of a group
        // and it is outside trash
        case (char == "}") && (trash_started == false): {
            // if only one group has been started
            if len(group_starts) == 1 {
                var last_group_start_idx = group_starts[len(group_starts) - 1];
                // recurse down the group's contents
                // get the group's total score and add it to the total
                total_score += run_group_counter(group_contents[(last_group_start_idx + 1) : i],
                                                 (cur_group_base_score + 1));
            }  //end if

            // close the last opened group
            group_starts = group_starts[:(len(group_starts)-1)];
        }   //end case
        }   //end switch
        }   //end for
    }   //end if
    return;
}   //end func



// takes as input the contents of a groups and checks if it is trash
func is_trash(contents []string) (result bool) {
    // if the first character is opening angular bracket
    // and the last character is an un-escaped closing angular bracket
    if contents[0] == "<" &&
       (contents[len(contents)-1] == ">" &&
       is_escaped(contents, (len(contents) - 1))) == false {
        result = true;
    } else {
        result = false;
    }   //end if
    return;
}   //end func



// checks whether the character with index 'idx' in the contents is escaped
func is_escaped(contents []string, idx int) (result bool) {
    if idx == 0 {
        result = true;
    } else {
        var prev_idx = idx -1;
        // while the previous character is the escape character
        for contents[prev_idx] == "!" {
            result = !result;
            prev_idx = prev_idx - 1;
        }   //end for
    }   //end if
    return;
}   //end func



// takes as input a group and returns its contents (everything between '{' and '}')
// a group = slice of strings, which begins with '{' and ends with '}'
func get_group_contents(group []string) []string {
    return group[1 : (len(group)-1)];
}   //end func
