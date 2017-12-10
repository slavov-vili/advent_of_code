package main;

import (
    "bufio"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // stores the function to run the passphrase check
    // based on which part of the assignment is being run
    var run_passphrase_check func([]string)int;
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores all lines from the input
    var input_lines []string;

    // convert the very first input to an int and store it
    println("Testing Part: ")
    for scanner.Scan() {
        part,_ := strconv.Atoi(scanner.Text())
        // if an invalid part is given
        if (part < 1) || (part > 2) {
            println("Invalid part of the assignment !")
            println("Try again: ")
            continue;
        }   //end if
        // store the correct function for the particular part of the assignment
        switch part {
        case 1: run_passphrase_check = run_passphrase_check_same;
        //case 2: run_passphrase_check = run_passphrase_check_same;
        }   //end switch
        break;
    }   //end for

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // stores each line of input
            input_lines = append(input_lines, input);
            continue;
        }   //end if

        // get the number of valid passphrases from the input
        var valid_phrases_count = run_passphrase_check(input_lines);
        print("There are ")
        print(valid_phrases_count)
        print(" valid passphrases")
        println()
        // clean the input list
        input_lines = []string{};
    }   //end for
}   //end main



// Checks how many passphrases from the input are valid
//
// Arguments:
// input - a list of passphrases
//
// Returns:
// the number of valid passphrases (ones without repetition)
func run_passphrase_check_same(input []string) (count int) {
    OUTER:
    // for each line of the input
    for _, line := range input {
        // get all the words from the line
        var words = strings.Fields(line);
        // stores the words which were already seen in this line
        var seen = make(map[string]bool, len(words));
        // for each word in the line
        for _,word := range words {
            // if the word DOESN'T exist in the line already
            if seen[word] != true {
                // add it to the seen map
                seen[word] = true;
            } else {
                continue OUTER;
            }   //end if
        }   //end for
        count++;
    }   //end for

    return;
}   //end func
