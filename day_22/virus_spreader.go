package main

import (
    "bufio"
    "errors"
    "fmt"
    "os"
    "strings"
)   //end imports


// define constants to represent directions
type Direction int
const (
    North Direction = iota;
    East  
    South 
    West  
    DirectionCount  = 4;
)   //end const


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores the input matrix
    var nodes = make([][]string, 0);
    // stores how many times the program should iterate in each part of the assignment
    const part_1_iters = 10000;
    const part_2_iters = 10000000;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // split the line of input into its characters and add them to the node map
            nodes = append(nodes, strings.Split(input, ""));
            continue;
        }   //end if


        // calculate the row and column, where the virus carrier starts
        var start_row    = len(nodes) / 2;
        var start_column = len(nodes[0]) / 2;


        // PART I
        var carrier_1 = NewVirusCarrier(North, start_row, start_column);
        var infecting_burst_count_1 = iterate_to_infect(carrier_1, VirusCarrier.DoWork, nodes, part_1_iters);
        fmt.Println(infecting_burst_count_1, "bursts caused an infection!");


        // PART II
        var carrier_2 = NewVirusCarrier(North, start_row, start_column);
        var infecting_burst_count_2 = iterate_to_infect(carrier_2, VirusCarrier.DoAdvancedWork, nodes, part_2_iters);
        fmt.Println(infecting_burst_count_2, "bursts caused an infection!");


        // clear the input map
        nodes = make([][]string, 0);
    }   //end for
}   //end main



// moves the virus carrier until an "infect" operation is performed
func iterate_to_infect(vc *VirusCarrier, do_work func(VirusCarrier, [][]string) string, nodes [][]string, iter_count int) (count int) {
    var nodes_copy = copy_2d_array(nodes);

    // iterate until stopped
    for i := 0; i < iter_count; i++ {
        // turn the virus carrier
        vc.Turn(nodes_copy);

        // do work on the current node
        var new_node_status = do_work(*vc, nodes_copy);
        // if the node was infected
        if new_node_status == "#" {
            count++;
        }   //end if

        // move the carrier
        _, err := vc.Move(nodes_copy);
        // if the carrier is trying to move to a node outside the grid
        if err != nil {
            // expand the grid by 2 rows and 2 columns
            nodes_copy = expand_grid(nodes_copy);
            // update the current position
            vc.pos.x++;
            vc.pos.y++;
        }   //end if
    }   //end for
    return;
}   //end func



// returns a copy of the given array
func copy_2d_array(arr [][]string) [][]string {
    var row_count    = len(arr);
    var column_count = len(arr[0])

    var arr_copy = make([][]string, row_count);
    // for each row in the array
    for i := range arr_copy {
        arr_copy[i] = make([]string, column_count);
    }   //end for

    for i, row := range arr {
        for j, char := range row {
            arr_copy[i][j] = char;
        }   //end for
    }   //end for
    return arr_copy;
}   //end func


// expands a 2D grid by adding 2 rows and 2 columns
func expand_grid(grid [][]string) [][]string {
    // store the sizes of the new grid
    var new_column_count = len(grid[0]) + 2;
    // make a new grid
    var new_grid = make([][]string, 0);

    // make two empty rows for the first and last row
    var empty_row_first = make([]string,  new_column_count);
    var empty_row_last  = make([]string,  new_column_count);
    for i := range empty_row_first {
        empty_row_first[i] = ".";
        empty_row_last[i]  = ".";
    }   //end for

    // append the first empty row
    new_grid = append(new_grid, empty_row_first);
    // for each row in the old grid
    for _, row := range grid {
        // create a new row for the new grid
        var new_row = make([]string, 0);

        // fill in the new row
        new_row = append(new_row, ".");
        for _, char := range row {
            new_row = append(new_row, char);
        }   //end for
        new_row = append(new_row, ".");

        // add the new row to the new grid
        new_grid = append(new_grid, new_row);
    }   //end for
    // append the last empty row
    new_grid = append(new_grid, empty_row_last);
    return new_grid;
}   //end func





type VirusCarrier struct {
    pos       *Pos
    direction  Direction
}   //end type


func NewVirusCarrier(new_direction Direction, new_row, new_column int) *VirusCarrier {
    var new_virus_carrier = new(VirusCarrier);
    new_virus_carrier.direction =  new_direction;
    new_virus_carrier.pos       = &Pos{new_row, new_column};

    return new_virus_carrier;
}   //end func


// turns based on the status of the current node
func (vc *VirusCarrier) Turn(nodes [][]string) Direction {
    var cur_node_status = nodes[vc.pos.x][vc.pos.y];

    switch cur_node_status {
    // if the current node is INFECTED
    case "#":
        // turn right
        vc.direction = (vc.direction + 1) % DirectionCount;
    
    // if the current node is CLEAN
    case ".":
        // turn left
        vc.direction = ((vc.direction - 1) + DirectionCount) % DirectionCount;

    // if the current node is FLAGGED
    case "F":
        // turn around
        vc.direction = (vc.direction + (DirectionCount / 2)) % DirectionCount;
    }   //end switch

    return vc.direction;
}   //end func


// moves the virus carrier along the grid based on its direction
func (vc *VirusCarrier) Move(nodes [][]string) (new_pos *Pos, err error) {
    switch vc.direction {
    case West:
        new_pos = &Pos{vc.pos.x, (vc.pos.y - 1)};

    case East:
        new_pos = &Pos{vc.pos.x, (vc.pos.y + 1)};

    case North:
        new_pos = &Pos{(vc.pos.x - 1), vc.pos.y};
        
    case South:
        new_pos = &Pos{(vc.pos.x + 1), vc.pos.y};
    }   //end switch

    vc.pos = new_pos;

    if !new_pos.IsWithinGrid(nodes) {
        err = errors.New(fmt.Sprintf("%v {%v, %v} %v", "Position", new_pos.x, new_pos.y, "is not in grid !!!"));
    }   //end if

    return;
}   //end func


// performs an action based on the status of the current node in the grid
func (vc VirusCarrier) DoWork(nodes [][]string) (node_status string) {
    // store the initial status of the node
    node_status = nodes[vc.pos.x][vc.pos.y];

    switch node_status {
    // if the node is CLEAN
    case ".":
        // infect it
        node_status = "#";

    // if the node is INFECTED
    case "#":
        // clean it
        node_status = "."
    }   //end switch

    // set the new status of the node
    nodes[vc.pos.x][vc.pos.y] = node_status;
    return;
}   //end func


// performs an ADVANCED action based on the status of the current node in the grid
func (vc VirusCarrier) DoAdvancedWork(nodes [][]string) (node_status string) {
    // store the initial status of the node
    node_status = nodes[vc.pos.x][vc.pos.y];

    switch node_status {
    // if the node is CLEAN
    case ".":
        // weaken it
        node_status = "W";

    // if the node is WEAKEND
    case "W":
        // infect it
        node_status = "#";

    // if the node is INFECTED
    case "#":
        // flag it
        node_status = "F";

    // if the node is FLAGGED
    case "F":
        // clean it
        node_status = "."
    }   //end switch

    // set the new status of the node
    nodes[vc.pos.x][vc.pos.y] = node_status;
    return;
}   //end func



type Pos struct {
    x int
    y int
}   //end type


func (pos Pos) IsWithinGrid(grid [][]string) (is_valid bool) {
    if (pos.x < 0) || (pos.x >= len(grid)) {
        is_valid = false;
    } else if (pos.y < 0) || (pos.y >= len(grid[0])) {
        is_valid = false;
    } else {
        is_valid = true;
    }   //end else

    return;
}   //end func
