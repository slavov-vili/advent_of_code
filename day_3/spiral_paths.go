package main

import (
    "bufio"
    "math"
    "os"
    "strconv"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // convert the current input to int and store it
        input,_ := strconv.Atoi(scanner.Text())

        // run the path counter and print the required number of paths
        var path_count = run_path_counter_manh(input);
        print("Number of Paths: ")
        print(path_count)
        println()
    }   //end for
}   //end func


// Calculates the Manhattan distance required to transport the information to the center of the grid.
// Finds the closest mid-point to the starting number in its particular square
// and then calculates the distance from that mid-point to the center.
//
// Arguments:
// start - the number at which the information starts
//
// Returns:
// the Manhattan distance between the number and the center of the grid
func run_path_counter_manh(start int) int {
    // find out which square the number is in
    var sq_num  = calc_sq_num(start);
    // get the square's side and bounds
    var sq_side = calc_sq_side(sq_num);
    var sq_lower_bound = calc_sq_lower_bound(calc_sq_side(sq_num - 1));
    var sq_upper_bound = calc_sq_upper_bound(sq_side);
    // get the square's mid-points
    var sq_mid_points  = calc_sq_mid_points(sq_side, sq_lower_bound, sq_upper_bound);

    // get the index of the closest mid-point AND
    // the distance between that mid-point and the given number
    var _, closest_mid_point_dist = calc_closest_mid_point(start, sq_mid_points);

    return (closest_mid_point_dist + sq_num);
}   //end func


// Calculates which square of the grid the given number is
// Square indices begin from 0 !
//
// Arguments:
// number - the number, which is being checked
//
// Returns:
// the index of the square, where the number is
func calc_sq_num(number int) int {
    if number == 1 { return 0; }

    var sq_num = 0;
    var sq_upper_bound = 0;

    // loop
    for {
        // calculate the new upper bound
        sq_upper_bound = calc_sq_upper_bound(calc_sq_side(sq_num));
        // if the number outside that bound
        if number > sq_upper_bound {
            // go to the next square
            sq_num++;
        // if the number is within that bound
        // (meaning it is in this square)
        } else {
            break;
        }   //end if
    }   //end for

    return sq_num;
}   //end func


// Calculates the length of the side of the square with index "sq_num"
// Square indices start from 0 !
// The length of the side of a square = the number of elements on that side !
//
// Arguments:
// sq_num - the index of the square in the grid
//
// Returns:
// the number of elements on the side of the square
func calc_sq_side(sq_num int) int {
    if sq_num < 0 {
        return (-1);
    } else if sq_num == 0 {
        return 1;
    } else {
        return ((2*sq_num) + 1);
    }   //end if
}   //end func


// Calculates the lower bound of this square
// lower bound = the first element in the square !
//
// Arguments:
// prev_sq_side - the length of the side of the previous square
//
// Returns:
// the lower bound of the square
func calc_sq_lower_bound(prev_sq_side int) int {
    // if we're dealing with the 0-th square
    if prev_sq_side == (-1) { return 1; }

    return (int(math.Pow(float64(prev_sq_side), 2)) + 1);
}   //end func


// Calculates the upper bound of this square
// upper bound = the last  element in the square !
//
// Arguments:
// this_sq_side - the length of the side of this square
//
// Returns:
// the upper bound of the square
func calc_sq_upper_bound(this_sq_side int) int {
    // if we're dealing with the 0-th square
    if this_sq_side == 1 { return 1; }

    return int(math.Pow(float64(this_sq_side), 2));
}   //end func


// Calculates the mid points of a square
// mid-points are the fastest way to reach 1 !
//
// Arguments:
// sq_side        - the length of the side of this square (needed to calculate consecutive mid-points)
// sq_lower_bound - the first number in this square       (needed to calculate the first mid-point)
// sq_upper_bound - the last  number in this square       (needed to know when the mid-point calculation should stop)
//
// Returns:
// the 4 mid-points of this square
func calc_sq_mid_points(sq_side, sq_lower_bound, sq_upper_bound int) (mid_points []int) {
    if (sq_side == 1) { return []int{1, 1, 1, 1}; }

    // the offset is the distance between each mid-point
    var sq_offset = sq_side - 1;
    var mid_prev int;
    // get the first mid-point
    var mid_next = sq_lower_bound + (int(sq_side/2) - 1);

    // while the next mid-point is within this square
    for mid_next < sq_upper_bound {
        // add the mid-point to the list
        mid_points = append(mid_points, mid_next);
        // this mid-point becomes previous
        mid_prev = mid_next;
        // calculate the next mid-point
        mid_next = mid_prev + sq_offset;
    }   //end for

    return;
}   //end func


// Calculates the index of the mid-point closest to the given number
//
// Arguments:
// number     - the number which is being checked
// mid_points - the list of mid-points to check
//
// Returns:
// the index of the closest mid-point
func calc_closest_mid_point(number int, mid_points []int) (closest_idx, closest_dist int) {
    // the initial closest point is point '0'
    closest_idx  = 0;
    // the initial closest distance is the distance between the number and the initial point
    closest_dist = int(math.Abs(float64(number - mid_points[closest_idx])));

    // for each point in the list
    // (skipping 0, because that's the default)
    for i,this_point := range mid_points[1:] {
        // get the distance between the number and the next point
        var this_dist = int(math.Abs(float64(number - this_point)));

        // if the new distance is smaller than the previous closest distance
        if this_dist < closest_dist {
            // the new distance is the closest one
            closest_dist = this_dist;
            // and the new closest point is with index 'i'
            closest_idx = i;
        }   //end if
    }   //end for

    return closest_idx, closest_dist;
}   //end func
