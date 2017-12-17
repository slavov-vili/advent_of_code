package main

import (
    "fmt"
    //"io/ioutil"
    "log"
    //"os"
    "strings"
)   //end imports


func main() {
    //var args = os.Args;

    //// read the input from the file given as an argument
    //var input, read_err = ioutil.ReadFile(args[1]);
    //if read_err != nil {
        //log.Fatal(read_err);
    //}   //end if

    // store the initial path through the grid
    //var path    = strings.Split(string(input), ",");
    var path    = strings.Split("ne,ne,s,s", ",");
    var changes = -1;

    // while changes were made during path optimization
    for changes != 0 {
        fmt.Println("Path: ", path)
        path, changes = optimize_path(path);
    }   //end for
    print("Total moves needed: ")
    print(len(path))
    println()
}   //end main



// tries to optimize the given path
// returns the optimized path AND the number of changes made
func optimize_path(path []string) (path_optimized []string, changes int) {
    var cur_pos = 0;
    // iterate over the path
    for cur_pos < len(path) {

        if cur_pos == (len(path) - 1) {
            // append the last step to the optimized path
            path_optimized = append(path_optimized, path[len(path) - 1]);
            break;
        }   //end if

        // attempt to reduce the two steps
        var steps_reduced = reduce_steps(path[cur_pos], path[cur_pos + 1]);

        // if the steps were reduced to nothing (moving in opposite directions)
        if len(steps_reduced) == 0 {
            changes++;
            cur_pos +=2;
            continue;
        // if the reduction of the steps failed (its impossible to reduce the 2 steps to 1)
        } else if len(steps_reduced) == 1 {
            changes++;
            cur_pos += 2;
        // if the two steps were successfully reduced to 1
        } else if len(steps_reduced) == 2 {
            cur_pos += 1;
        } else {
            log.Fatal("Error in step reduction");
        }   //end if

        // add the first of the reduced steps to the optimized path
        // if the steps were optimized, then only 1 step would be in the list
        // if it was one, the first one is added and the second one is used for the next iteration
        path_optimized = append(path_optimized, steps_reduced[0]);
    }   //end for

    fmt.Println("Optimized path: ", path_optimized)
    return;
}   //end func



// attempts to reduce the 2 steps into 1
func reduce_steps(step1, step2 string) (new_steps []string) {
    switch {
        case (step1 == "n")  && (step2 == "se"): new_steps = append(new_steps, "ne");
        case (step1 == "n")  && (step2 == "s"):  ;
        case (step1 == "n")  && (step2 == "sw"): new_steps = append(new_steps, "nw");

        case (step1 == "ne") && (step2 == "s"):  new_steps = append(new_steps, "se");
        case (step1 == "ne") && (step2 == "sw"): ;
        case (step1 == "ne") && (step2 == "nw"): new_steps = append(new_steps, "n");

        case (step1 == "se") && (step2 == "sw"): new_steps = append(new_steps, "s");
        case (step1 == "se") && (step2 == "nw"): ;
        case (step1 == "se") && (step2 == "n"):  new_steps = append(new_steps, "ne");

        case (step1 == "s")  && (step2 == "nw"): new_steps = append(new_steps, "sw");
        case (step1 == "s")  && (step2 == "n"):  ;
        case (step1 == "s")  && (step2 == "ne"): new_steps = append(new_steps, "se");

        case (step1 == "sw") && (step2 == "n"):  new_steps = append(new_steps, "nw");
        case (step1 == "sw") && (step2 == "ne"): ;
        case (step1 == "sw") && (step2 == "se"): new_steps = append(new_steps, "s");

        case (step1 == "nw") && (step2 == "ne"): new_steps = append(new_steps, "n");
        case (step1 == "nw") && (step2 == "se"): ;
        case (step1 == "nw") && (step2 == "s"):  new_steps = append(new_steps, "sw");

        // if its none of the above (the steps cannot be reduced, return the input steps)
        default: new_steps = append(new_steps, step1, step2);
    }   //end switch
    fmt.Println("Reduced '", step1, "' and '", step2, "' to: ", new_steps)
    return;
}   //end func
