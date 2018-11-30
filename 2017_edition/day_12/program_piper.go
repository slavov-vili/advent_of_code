package main

import (
    "bufio"
    //"fmt"
    "os"
    "sort"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // maps a program ID to the IDs of all programs it communicates with
    var pipes  = make(map[int][]int, 0);

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // split the main program ID from the IDs of the ones it communicates with
            var parts = strings.Split(input, "<->");
            // trim the spaces of the main id and conver it to int
            var main_id,_ = strconv.Atoi(strings.TrimSpace(parts[0]));
            // for each program id in the list
            // !!! assumes that the ids are sorted !!!
            for _, id_str := range strings.Split(strings.TrimSpace(parts[1]), ", ") {
                // convert the id to an int
                id_int,_ := strconv.Atoi(id_str);
                // add it to the list
                pipes[main_id] = append(pipes[main_id], id_int);
            }   //end for
            continue;
        }   //end if


        // PART I
        // get the IDs of all programs, reacheable from the one with ID '0'
        var reacheable_from_0 = make([]int, 0);
        reacheable_from_0 = get_pipe_span_of(pipes, 0, reacheable_from_0);
        print("Reacheable: [")
        for _, id := range reacheable_from_0 {
            print(id)
        }   //end for
        println("]")
        print("Count: ")
        println(len(reacheable_from_0))


        // PART II
        // stores all groups of programs found in the pipeline
        var groups = append([][]int{}, reacheable_from_0);

        // for each program id in the pipes
        for id, _ := range pipes {
            var is_in_a_group = false;
            // for each group
            for _, group := range groups {
                // search for the program id in the group
                var idx = sort.SearchInts(group, id);

                // if the id IS in the group
                if (idx < len(group)) && (id == group[idx]) {
                    is_in_a_group = true;
                    break;
                }   //end if
            }   //end for

            // if the program CANNOT be found in any of the groups
            if is_in_a_group == false {
                    // generate the group of the program
                    groups = append(groups, get_pipe_span_of(pipes, id, []int{}));
            }   //end if
        }   //end for

        print("There are '", len(groups), "' groups")


        // clean the pipes map
        pipes = make(map[int][]int, 0);
    }   //end for
}   //end main



// recursively compiles a list of program IDs, reacheable from the starting one
func get_pipe_span_of(pipes map[int][]int, start_id int, old_pipe_span []int) (new_pipe_span []int) {
    new_pipe_span = make([]int, len(old_pipe_span));
    copy(new_pipe_span, old_pipe_span);

    // stores the IDs of programs directly reacheable from the starting one
    var reacheable_ids = pipes[start_id];

    // for each program directly recheable from the starting one
    for _, reacheable_id := range reacheable_ids {
        // find the program's position in the list (because we want to keep it sorted)
        var insert_idx = sort.SearchInts(new_pipe_span, reacheable_id);

        // if the ID is NOT in the list
        // BUT should be added at the end
        if insert_idx == len(new_pipe_span) {
            new_pipe_span = append(new_pipe_span, reacheable_id);

            // recusively add the target program's directly reacheable IDs
            new_pipe_span = add_to_sorted(new_pipe_span, get_pipe_span_of(pipes, reacheable_id, new_pipe_span)...);
        // if the ID is NOT in the list
        // BUT should still be added
        } else if reacheable_id != new_pipe_span[insert_idx] {
            // add the program ID to the list
            new_pipe_span = add_to_sorted(new_pipe_span, reacheable_id);

            // recusively add the target program's directly reacheable IDs
            new_pipe_span = add_to_sorted(new_pipe_span, get_pipe_span_of(pipes, reacheable_id, new_pipe_span)...);
        }   //end if
    }   //end for
    return;
}   //end func

// adds all elements to their correct positions in a sorted slice
func add_to_sorted(old_slice []int, to_add ...int) (new_slice []int) {
    new_slice = make([]int, len(old_slice));
    copy(new_slice, old_slice);

    // for each element in the list
    for _, element := range to_add {
        var insert_idx = sort.SearchInts(new_slice, element);

        // if the element should only be appended
        if insert_idx == len(new_slice) {
            new_slice = append(new_slice, element);
        // if the element is NOT in the list
        } else if element != new_slice[insert_idx] {
            var temp_slice = make([]int, 0);

            // add all elements BEFORE the one to be inserted
            temp_slice = append(temp_slice, new_slice[:insert_idx]...);

            // add the element itself
            temp_slice = append(temp_slice, element);

            // add all elements AFTER the one to be inserted
            temp_slice = append(temp_slice, new_slice[insert_idx:]...);
    
            new_slice = temp_slice;
        }   //end else
    }   //end for
    return;
}   //end func
