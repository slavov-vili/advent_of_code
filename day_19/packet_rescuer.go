package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "strings"
)   //end imports


func main() {
    var args = os.Args;
    // stores the matrix representing the path of the packet
    var packet_path = make([][]string, 0);

    // open the file given as an argument
    file, err := os.Open(args[1]);
    if err != nil {
        log.Fatal(err)
    }
    defer file.Close()

    // create a scanner for reading the file
    scanner := bufio.NewScanner(file)
    // for each line in the file
    for scanner.Scan() {
        // split the line into its characters and store them into the matrix
        packet_path = append(packet_path, strings.Split(scanner.Text(), ""));
    }   //end for

    // after the matrix has been filled in, store its sizes for ease of access
    var path_x = len(packet_path);
    var path_y = len(packet_path[0]);


    // PART I

}   //end main





type Pair struct {
    x int
    y int
}   //end type


func NewPair(new_x, new_y int) *Pair {
    var new_pair = new(Pair);

    new_pair.x = new_x;
    new_pair.y = new_y;
    return new_pair;
}   //end func


// checks whether the pair is a valid coordinate of the given matrix
func (pair *Pair) is_valid_coord(row_count, column_count int) (is_valid bool) {
    var valid_x = (pair.x >= 0) && (pair.x < row_count);
    var valid_y = (pair.y >= 0) && (pair.y < column_count);
    return (valid_x && valid_y);
}   //end func
