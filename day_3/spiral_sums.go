package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);

    // while input is being received
    println("Input:");
    for scanner.Scan() {
        // convert the current input to int and store it
        input,_ := strconv.Atoi(scanner.Text());

        // create the initial matrix
        var matrix = [][]int{[]int{1}};
        // where the spiraling begins when the matrix is extended
        var new_start Coord;
        // stores the first number, bigger than the input
        var first_bigger int;

        // while the matrix is not empty
        for (len(matrix) != 0) {
            new_start = Coord{len(matrix), (len(matrix[0]) + 1)};
            matrix, first_bigger = extend_matrix(matrix, new_start, input);
        }   //end for

        print("First cell, bigger than '");
        print(input);
        print("' = ");
        print(first_bigger);
        println();


    }   //end for
}   //end func



// Extends and fills in a 2D matrix until a number if found which is bigger than a specific number
//
// Arguments:
// old_matr - the matrix to be extended
// start    - where the filling in of the matrix should start
// number   - an integer
//
// Returns:
// the extended matrix and -1 OR
// an empty matrix and the first value from the matrix which is > than a specific number
func extend_matrix(old_matr [][]int, start Coord, number int) (new_matr [][]int, bigger int) {
    // expand the matrix
    new_matr = expand_matrix(old_matr);
    // stores the coordinates of all cells in the current level of the spiral
    // in the order, in which they are supposed to be filled in
    var my_coords []Coord;
    // stores the last cell, which was updated
    var last_cell = start;


    // add all the cells from the start to the top of the rightmost column
    for i := last_cell.x; i >= 0; i-- {
        last_cell = Coord{i, last_cell.y};
        my_coords = append(my_coords, last_cell);
    }   //end for
    // add all the cells from the last cell to the beginning of the uppermost row
    for i := (last_cell.y - 1); i >= 0; i-- {
        last_cell = Coord{last_cell.x, i};
        my_coords = append(my_coords, last_cell);
    }   //end for
    // add all the cells from the last cell to the bottom of the leftmost column
    for i := (last_cell.x + 1); i < len(new_matr); i++ {
        last_cell = Coord{i, last_cell.y};
        my_coords = append(my_coords, last_cell);
    }   //end for
    // add all the cells from the last cell to the end of the lowermost row
    for i := (last_cell.y + 1); i < len(new_matr[0]); i++ {
        last_cell = Coord{last_cell.x, i};
        my_coords = append(my_coords, last_cell);
    }   //end for

    // for each coordinate in the list
    for _,cell_coord := range my_coords {
        // calculate the cell's value
        var value = calc_cell_value(new_matr, cell_coord);
        if value > number {
            // return an empty matrix and the value
            return [][]int{}, value;
        } else {
            // fill in the cell
            new_matr[cell_coord.x][cell_coord.y] = value;
        }   //end if
    }   //end for

    // return the expanded matrix
    return new_matr, (-1);
}   //end func



// Expands a 2D matrix by adding 2 more rows and 2 more columns
//
// Arguments:
// old_matr - the matrix to be extended
//
// Returns:
// the expanded matrix
func expand_matrix(old_matr [][]int) [][]int {
    // create a new matrix
    var new_matr_row_count    = len(old_matr)    + 2;
    var new_matr_column_count = len(old_matr[0]) + 2;
    var new_matr = make([][]int, new_matr_row_count);

    // set the first row
    var new_empty_row = make([]int, new_matr_column_count)
    new_matr[0] = new_empty_row;
    // set the last row
    new_empty_row = make([]int, new_matr_column_count)
    new_matr[len(new_matr)-1] = new_empty_row;
    // NEED TO MAKE 2, because 'make' returns a slice (which is a pointer)

    // extend each row from the old matrix
    for i, old_row := range old_matr {
        // prepend a cell to the old row
        var new_row = append([]int{0}, old_row...);
        // append a cell to the old row
        new_row = append(new_row, 0);
        // add the extended row to the new matrix
        new_matr[i+1] = new_row;
    }   //end for

    return new_matr
}   //end func



// Calculates the value of a cell in the matrix based on its neighbors
//
// Arguments:
// matrix - the matrix, where the cell is found
// cell   - the cell to be filled in
//
// Returns:
// the new value of the cell
func calc_cell_value(matrix [][]int, cell Coord) (cell_value int) {
    var neighbor_values = get_neighbor_values(matrix, cell);
    // sum up the values of all neighbors
    // uncalculated neighbors will have value 0 and will not influence the sum
    for _,neighbor_value := range neighbor_values {
        cell_value += neighbor_value;
    }   //end for
    return;
}   //end func



// Looks for the values of all neighbor cells to the given cell
//
// Arguments:
// matrix - the matrix, where the cells are found
// cell   - the cell, whose neighbors are being sought
//
// Returns:
// a list of the values of all neighbors
func get_neighbor_values(matrix [][]int, cell Coord) (neighbor_values []int) {
    // for each x coordinate around the cell
    for x := (cell.x - 1); x <= (cell.x + 1); x++ {
        // for each y coordinate around the cell
        for y := (cell.y - 1); y <= (cell.y + 1); y++ {
            // if the coordinates are out of the matrix, or x and y point to the target cell
            if ((x < 0) || (y < 0))    ||
               ((x >= len(matrix))     ||
               ( y >= len(matrix[0]))) ||
               ((x == cell.x) && (y == cell.y)) {
                continue;
            } else {
                neighbor_values = append(neighbor_values, matrix[x][y]);
            }   //end if
        }   //end for
    }   //end for

    return;
}   //end func



// prints a 2D matrix prettier
func print_pretty_matrix(matrix [][]int) {
    // for each row
    for _,row := range matrix {
        fmt.Println(row);
    }
    println()
}   //end func





// a structure, which represents a coordinate in the matrix
type Coord struct {
    x int
    y int
}   //end type
