package main

import (
    //"fmt"
    "io/ioutil"
    "log"
    "os"
    "strings"
)   //end imports


func main() {
    var args = os.Args;

    //// read the input from the file given as an argument
    var input, read_err = ioutil.ReadFile(args[1]);
    if read_err != nil {
        log.Fatal(read_err);
    }   //end if

    // store the initial path through the grid (remove the 'newline' symbol)
    var path    = strings.Split(string(input[:(len(input) - 1)]), ",");
    //var path    = strings.Split("n,ne,s", ",");
    var prev_path_len = 0;
    var cur_path_len  = len(path);

    // while differences are being made to the paths
    for prev_path_len != cur_path_len {
        // optimize the path
        path = optimize_path(path);
        //fmt.Println("Path: ", path)
        // reset the lengths of the path
        prev_path_len = cur_path_len;
        cur_path_len = len(path);
    }   //end for
    //fmt.Println("Final Path: ", path)

    print("Total moves needed: ")
    print(len(path))
    println()
}   //end main



// tries to optimize the given path
// returns the optimized path
func optimize_path(path []string) (path_optimized []string) {
    // iterate over the path
    for i := 0; i < len(path); i++ {
        var cur_step = path[i]

        // if the step at the current position hasn't been used for a reduction
        if cur_step != "_" {

            // if we're at the end of the path
            if i == (len(path) - 1) {
                path_optimized = append(path_optimized, cur_step);
            } else {
                // get all steps after this one
                var next_steps = path[(i + 1):];
                // if there are NO more unreduced steps after this one
                if get_real_length(next_steps) == 0 {
                    // add the current step the the optimized path (can't reduce when the rest is empty)
                    path_optimized = append(path_optimized, cur_step);
                } else {
                    // for each step after the current one
                    for j := 0; j < len(next_steps); j++ {
                        var next_step = next_steps[j];

                        if next_step != "_" {
                            // attempt to reduce the two steps
                            var steps_reduced = reduce_steps(cur_step, next_step);

                            // if the steps were reduced to nothing (moving in opposite directions)
                            if len(steps_reduced) == 0 {
                                // mark that the two steps were used for a reduction
                                path[i]       = "_";
                                next_steps[j] = "_";
                                break;
                            // if the two steps were successfully reduced to 1
                            } else if len(steps_reduced) == 1 {
                                // add the result of the step reduction to the optimized path
                                path_optimized = append(path_optimized, steps_reduced[0]);
                                // mark that the two steps were used for a reduction
                                path[i]       = "_";
                                next_steps[j] = "_";
                                break;
                            // if the reduction was impossible
                            } else if len(steps_reduced) == 2 {
                                // if this is the last non-reduced step in the list of next steps
                                if get_real_length(next_steps[j:]) == 1 {
                                    // add the first step to the optimized path list
                                    // (next step gets added automatically later)
                                    path_optimized = append(path_optimized, steps_reduced[0]);
                                    break;
                                } else {
                                    continue;
                                }   //end else
                            } else {
                                log.Fatal("Error in step reduction");
                            }   //end else
                        }   //end if
                    }   //end for
                }   //end else
            }   //end else
        }   // end if
    }   //end for

    //fmt.Println("Optimized path: ", path_optimized)
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
    //fmt.Println("Reduced '", step1, "' and '", step2, "' to: ", new_steps)
    return;
}   //end func



// gets the length of the path, excluding reduced steps
func get_real_length(path []string) int {
    var real_length = 0;
    for _, step := range path {
        if step != "_" {
            real_length++;
        }   //end if
    }   //end for
    return real_length;
}   //end func
