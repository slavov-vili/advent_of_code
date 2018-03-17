package main

import (
    "bufio"
    "errors"
    "fmt"
    "log"
    "os"
    "strings"
)   //end imports


// define constants to represent directions
type Direction int
const (
    North Direction = iota;
    West  
    South 
    East  
    DirectionCount  = 4;
)   //end const


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores the input matrix
    var nodes = make([][]string, 0);
    // stores how many times the program should iterate in part 1
    const part_1_iters = 10000;

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

        // TODO: figure out why position is out of grid


        // calculate the row and column, where the virus carrier starts
        var start_row    = (len(nodes) / 2) + 1;
        var start_column = (len(nodes[0]) / 2) + 1;


        // PART I
        var carrier_1 = NewVirusCarrier(North, start_row, start_column);
        var infecting_burst_count = iterate_to_infect(carrier_1, nodes, part_1_iters);
        fmt.Println(infecting_burst_count, "burst caused an infection!");


        // PART II


        // clear the input map
        nodes = make([][]string, 0);
    }   //end for
}   //end main



// moves the virus carrier until an "infect" operation is performed
func iterate_to_infect(vc *VirusCarrier, nodes [][]string, iter_count int) (count int) {
    var nodes_copy = copy_2d_array(nodes);

    // iterate until stopped
    for i := 0; i < iter_count; i++ {
        //fmt.Println("Cur-Dir:", vc.direction)
        //fmt.Println("Node status:", nodes[vc.pos.x][vc.pos.y])
        //fmt.Println("Cur-Pos:", *vc.pos)

        // turn the virus carrier
        vc.Turn(nodes_copy);
        //fmt.Println("New-Dir:", vc.direction)

        // do work on the current node
        var new_node_status = vc.DoWork(nodes_copy);
        //fmt.Println("New Node status:", nodes[vc.pos.x][vc.pos.y])
        // if the node was infected
        if new_node_status == "#" {
            count++;
        }   //end if

        // move the carrier
        _, err := vc.Move(nodes_copy);
        if err != nil {
            log.Fatal(err);
        }   //end if
        //fmt.Println("New-Pos:", *vc.pos)
        //println()
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
    return arr_copy;
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

    // if the current node IS infected
    if cur_node_status == "#" {
        // turn right
        vc.direction = (vc.direction + 1) % DirectionCount;
    // if the current node is NOT infected
    } else {
        // turn left
        vc.direction = ((vc.direction - 1) + DirectionCount) % DirectionCount;
    }   //end if
    return vc.direction;
}   //end func


// moves the virus carrier along the grid based on its direction
func (vc *VirusCarrier) Move(nodes [][]string) (new_pos *Pos, err error) {
    switch vc.direction {
    case East:
        new_pos = &Pos{vc.pos.x, (vc.pos.y - 1)};
        
    case West:
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

    // if the node is infected
    if node_status == "#" {
        // clean it
        node_status = ".";
    // if the node is clean
    } else {
        // infect it
        node_status = "#";
    }   // end else

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
