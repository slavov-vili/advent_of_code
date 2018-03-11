package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "regexp"
    "strings"
)   //end imports


func main() {
    var args = os.Args;
    // stores the matrix representing the path of the packet
    var packet_path = make([][]string, 0);
    // stores the starting position of the path
    var start_pos = 0;

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

    // find the starting position
    // for each character in the first line
    for i, char := range packet_path[0] {
        if char == "|" {
            start_pos = i;
            break;
        }   //end if
    }   //end for
    
    // create a packet carrier for the current path
    var packet_carrier = NewPacketCarrier(NewPair(0, start_pos), "south")


    // stores all the letters encountered while going through the path
    var letters string;
    // stores the number of moves made by the package
    var move_count int;
    // stores the character in the position in the path after the move
    var next_char string;
    // stores whether the packet has reached the end of the path
    var at_end bool;
    // a regexp to check if a string is a letter
    var isLetter = regexp.MustCompile("[a-zA-Z]");

    // while the packet hasn't reached the end of the path
    for !at_end {
        // move the packet one step and get the character at that position
        next_char, at_end = packet_carrier.Move(packet_path);

        // if the next character is a letter, store it to the result
        if isLetter.MatchString(next_char) {
            letters += next_char;
        }   //end if

        // increment the move count
        move_count++;
    }   //end for


    // PART I
    fmt.Println("Letters found:", letters);


    // PART II
    fmt.Println("Move count:", move_count);
}   //end main





type PacketCarrier struct {
    direction  string
    cur_pos        *Pair
}   //end type


func NewPacketCarrier(new_pos *Pair, new_direction string) *PacketCarrier {
    var new_packet_carrier = new(PacketCarrier);

    new_packet_carrier.direction = new_direction;
    new_packet_carrier.cur_pos = NewPair(new_pos.x, new_pos.y);
    return new_packet_carrier;
}   //end func


// attempts to move the packet carrier in the given direction
func (packet_carrier *PacketCarrier) Move(path [][]string) (next_char string, at_end bool) {
    // store the character at the current position of the carrier
    var cur_char = path[packet_carrier.cur_pos.x][packet_carrier.cur_pos.y];
    //fmt.Println("Moving from:", cur_char)


    // stores whether the carrier is currently standing at a crossroad
    var at_crossroad bool;
    if cur_char == "+" {
        at_crossroad = true;
    } else {
        at_crossroad = false;
    }   //end else


    // get the new position and the new direction of the carrier after the move
    var new_pos, new_direction = get_new_pos(packet_carrier.cur_pos, packet_carrier.direction, at_crossroad, path);


    // if we have reached the end of the path
    if new_direction == "" {
        at_end = true;
    } else {
        // move   to the new position
        packet_carrier.cur_pos   = new_pos;
        // adjust to the new direction
        packet_carrier.direction = new_direction;

        // store the character at the new position
        next_char = path[new_pos.x][new_pos.y];
    }   //end else
    return;
}   //end func



// gets the new position and the new direction of the carrier
func get_new_pos(cur_pos *Pair, cur_direction string, at_crossroad bool, path [][]string) (new_pos *Pair, new_direction string) {
    // get a direction, which presents a possible road to continue
    new_direction = get_next_dir(cur_pos, cur_direction, at_crossroad, path);

    // if the direction exists (means we are NOT at the end of the path)
    if new_direction != "" {
        // calculate the next position based on the direction
        new_pos = calc_new_pos(cur_pos, new_direction);
    }   //end else
    return;
}   //end func



// returns the direction in which the next road is found
func get_next_dir(cur_pos *Pair, cur_direction string, at_crossroad bool, path [][]string) (new_direction string) {
    // if we're NOT at a crossroad
    if !at_crossroad {
        // store the next position when going in the same direction
        var new_pos = calc_new_pos(cur_pos, cur_direction);
        // if the new position is within the path
        if new_pos.is_valid_coord(len(path), len(path[0])) {
            // if the character at the new position is not empty space
            if path[new_pos.x][new_pos.y] != " " {
                // continue in the same direction
                new_direction = cur_direction;
            }   //end if
        }   //end if

    // if we ARE at a crossroad
    } else {
        // store a list of all possible directions
        var all_directions = []string{"north","east","south","west"};

        // for each direction in the list
        for _, dir := range all_directions {
            // if the direction is NOT the one where we came from
            if dir != get_opposite_direction(cur_direction) {
                // calculate the next position in that direction
                var new_pos = calc_new_pos(cur_pos, dir);

                // if the position is a valid coordinate in the path
                if new_pos.is_valid_coord(len(path), len(path[0])) {
                    // if the character at that position is NOT an empty space
                    // there must be a road there
                    if path[new_pos.x][new_pos.y] != " " {
                        // store the direction
                        new_direction = dir;
                        break;
                    }   //end if
                }   //end if
            }   //end if
        }   //end for
    }   //end else

    return;
}   //end func



// calculates the next position in a given direction
func calc_new_pos(cur_pos *Pair, direction string) (new_pos *Pair) {
    switch direction {
    // if we are moving north
    case "north":
        new_pos = NewPair((cur_pos.x - 1), cur_pos.y);

    // if we are moving east
    case "east":
        new_pos = NewPair(cur_pos.x, (cur_pos.y + 1));

    // if we are moving south
    case "south":
        new_pos = NewPair((cur_pos.x + 1), cur_pos.y);

    // if we are moving west
    case "west":
        new_pos = NewPair(cur_pos.x, (cur_pos.y - 1));
    }   //end switch
    return;
}   //end func



// returns the opposite direction of the given one
func get_opposite_direction(cur_direction string) (opposite_direction string) {
    switch cur_direction {
    case "north":
        opposite_direction = "south";

    case "east":
        opposite_direction = "west";

    case "south":
        opposite_direction = "north";

    case "west":
        opposite_direction = "east";
    }   //end switch
    return;
}   //end func





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
