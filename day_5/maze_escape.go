package main

import (
    "bufio"
    "os"
    "strconv"
)   //end imports


func main() {
    // stores the function to run the escape algorithm
    // based on which part of the assignment is being run
    var run_out_of_maze func([]int)int;
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores the maze which needs to be navigated
    var maze []int;

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
        case 1: run_out_of_maze = run_out_of_maze_inc;
        //case 2: run_out_of_maze = run_out_of_maze_inc;
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
            // conver the input to int
            input_int,_ := strconv.Atoi(input);
            // add the input to the maze
            maze = append(maze, input_int);
            continue;
        }   //end if

        // run the escape algorithm and print the required number of steps to escape
        var steps_to_freedom = run_out_of_maze(maze);
        print("Steps needed to escape from the maze: ")
        print(steps_to_freedom)
        println()
        // clear the maze
        maze = []int{};
    }   //end for
}   //end main


// Calculates the number of moves required to escape from the int maze
func run_out_of_maze_inc(maze []int) (count int) {
    // stores the index of the current position in the maze
    var cur_idx = 0;
    // while the next position is in the maze
    for {
        // calculate the next index depending on the value in the maze
        var next_idx = cur_idx + maze[cur_idx];
        // increment the step counter
        count++;
        // if the next index is OUTSIDE the maze
        if (next_idx < 0) || (next_idx >= len(maze)) {
            break;
        // if it is INSIDE the maze
        } else {
            maze[cur_idx]++;
            cur_idx = next_idx;
        }   //end if
    }   //end for
    return;
}   //end func
