package main

import (
    hasher "../day_10"
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // the number of rows in the the hashed matrix
    const matrix_rows = 128;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();


        // PART I
        // hash the input and fill in each row of the matrix
        var hash_matrix = hash_each_row(input, matrix_rows);
        var active_bit_count = 0;
        // for each row in the matrix
        for _, row := range hash_matrix {
            // for each element of that row
            for _, el := range row {
                if el == true { active_bit_count++; };
            }   //end for
        }   //end for
        println("There are", active_bit_count, "active bits!")

        
        // PART II
        // traverse the matrix and find all regions (sequences of adjacent set bits)
        var region_count = count_regions(hash_matrix);
        println("There are", region_count, "regions in the matrix!")
    }   //end for
}   //end main



// hashes the input string for each row of the matrix and fills in the row
func hash_each_row(input string, matrix_row_count int) (hash_matrix [][]bool) {
    hash_matrix = make([][]bool, matrix_row_count);
    println("Hashing ...")
    // for each row of the matrix
    for i := 0; i < len(hash_matrix); i++ {
        // convert the row index to a string
        var row_idx = strconv.Itoa(i);
        // run knot hash and get a hash for this specific row
        var hashed_row = hasher.Get_knot_hash(strings.Join([]string{input, "-", row_idx}, ""), 256);

        // convert the hashed row from hexadecimal to binary
        hashed_row = hex_hash_to_binary(hashed_row);

        // for each bit in the binary representation of the hash
        for _, bit := range strings.Split(hashed_row, "") {
            switch bit {
                case "0": hash_matrix[i] = append(hash_matrix[i], false);
                case "1": hash_matrix[i] = append(hash_matrix[i], true);
            }   //end switch
        }   //end for
    }   //end for
    println("Done Hashing!")
    return;
}   //end func



// counts all the regions from the matrix
func count_regions(matrix [][]bool) (region_count int) {
    // make a copy of the input matrix
    var tmp_matrix = make([][]bool, len(matrix));
    // for each row of the input matrix
    for i, row := range matrix {
        // for each element in the row
        for _, value := range row {
            tmp_matrix[i] = append(tmp_matrix[i], value);
        }   //end for
    }   //end for

    // for each row of the temp matrix
    for i, row := range tmp_matrix {
        // for each element in that row
        for y, value := range row {
            // if the element is a set bit
            if value == true {
                // recursively enclose the region starting at the current position
                enclose_region_recursively(tmp_matrix, []Coord{ Coord{i, y} });
                // increase the region count
                region_count++;
            }   //end if
        }   //end for
    }   //end for

    return;
}   //end func



// converts a hash from hexadecimal to binary
func hex_hash_to_binary(hex_hash string) (bin_hash string) {
    // for each character in the hex string
    for _, char := range strings.Split(hex_hash, "") {
        // parse the character as a hexadecimal digit
        var int_hex, _ = strconv.ParseInt(char, 16, 0);
        // convert the hexadecimal digit to a binary one
        var int_bin = fmt.Sprintf("%04b", int_hex);

        // append the converted digit to binary hash
        bin_hash = strings.Join([]string{bin_hash, int_bin}, "");
    }   //end for
    return;
}   //end func



// encloses the region which contains all the bits from the given coordinates
// enclosing simply sets all the bits from the region to 0, showing that they have been visited
func enclose_region_recursively(matrix [][]bool, coords []Coord) (region_size int) {
    if len(coords) == 0 {
        region_size = 0;
    } else {
        // for each coordinate in the list
        for _, coord := range coords {
            // unset the bit at that coordinate
            matrix[coord.x][coord.y] = false;
            region_size++;

            // stores the coordinates of adjacent bits which are also set
            var new_coords = get_adjacent_set_bits(matrix, coord);
            // recursively enclose the region
            region_size += enclose_region_recursively(matrix, new_coords);
        }   //end for
    }   //end else
    return;
}  //end func



// extracts the coordinates of all bits from the matrix which are adjacent to the given one and are also set
func get_adjacent_set_bits(matrix [][]bool, coord Coord) (set_adjacents []Coord) {
    // if there is a bit above this one
    if (coord.x - 1) >= 0 {
        // if that bit is set
        if matrix[coord.x - 1][coord.y] == true {
            // add it to the list
            set_adjacents = append(set_adjacents, Coord{(coord.x - 1), coord.y});
        }   //end if
    }   //end if


    // if there is a bit to the right of this one
    if (coord.y + 1) < len(matrix[0]) {
        // if that bit is set
        if matrix[coord.x][coord.y + 1] == true {
            // add it to the list
            set_adjacents = append(set_adjacents, Coord{coord.x, (coord.y + 1)});
        }   //end if
    }   //end if


    // if there is a bit below this one
    if (coord.x + 1) < len(matrix) {
        // if that bit is set
        if matrix[coord.x + 1][coord.y] == true {
            // add it to the list
            set_adjacents = append(set_adjacents, Coord{(coord.x + 1), coord.y});
        }   //end if
    }   //end if


    // if there is a bit to the left of this one
    if (coord.y - 1) >= 0 {
        // if that bit is set
        if matrix[coord.x][coord.y - 1] == true {
            // add it to the list
            set_adjacents = append(set_adjacents, Coord{coord.x, (coord.y - 1)});
        }   //end if
    }   //end if


    return;
}   //end func





// a coordinate from a matrix
type Coord struct {
    x int
    y int
}   //end type
