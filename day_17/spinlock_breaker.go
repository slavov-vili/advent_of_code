package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner  = bufio.NewScanner(os.Stdin);
    // store the spinlock
    var spinlock *Spinlock;
    // store how many items should be added
    const add_count_1 = 2017;
    const add_count_2 = 50000000;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the input
        var steps, _ = strconv.Atoi(scanner.Text());

        // create the initial spinlock
        spinlock = NewSpinlock(steps);

        // PART I
        for i:=1; i<=add_count_1; i++ {
            spinlock.Add(i);
        }   //end for
        fmt.Println("Item after last added:", spinlock.buffer[spinlock.cur_pos + 1]);

        // PART II
        // store the buffer size
        var buffer_size = 1;
        // store the current position
        var cur_pos = 0;
        // store value after 0
        var after_0 = 0;

        for i:=1; i<=add_count_2; i++ {
            // calculate the position where an element should be added
            cur_pos = calc_insert_idx(buffer_size, cur_pos, steps);
            // increase the buffer size
            buffer_size++;

            // if the element should be added at index 1
            if cur_pos == 1 {
                // store the value
                after_0 = i;
            }   //end if
        }   //end for
        fmt.Println("Item after 0:", after_0);

    }   //end for
}   //end main





type Spinlock struct {
    buffer  []int
    cur_pos   int
    steps     int
}   //end type


// creates a new spinlock with an initial buffer and the given number of steps
// the number of steps is used when doing an addition
func NewSpinlock(new_steps int) *Spinlock {
    var new_spinlock = new(Spinlock);
    // initialize the new spinlocks buffer to only contain 1 element (a 0)
    new_spinlock.buffer  = make([]int, 1);
    new_spinlock.cur_pos = 0;
    new_spinlock.steps = new_steps;
    return new_spinlock;
}   //end func


// adds an element to the spinlock and returns the new buffer
func (spinlock *Spinlock) Add(new_element int) []int {
    // calculate the index where the new element should be inserted
    var insert_idx = calc_insert_idx(len(spinlock.buffer), spinlock.cur_pos, spinlock.steps);

    // insert the given element in the spinlock's buffer
    spinlock.buffer = insert_element(spinlock.buffer, new_element, insert_idx);
    // change the current position
    spinlock.cur_pos = insert_idx;

    return spinlock.buffer;
}   //end func


// calculates the index in an array (given its size) based on the current position and the number of steps
func calc_insert_idx(buffer_size, cur_pos, steps int) int {
    // calculate the index, reached after the steps have been made
    var next_pos = (cur_pos + steps) % buffer_size;

    // the next element should be inserted at the position after the steps have been made
    return (next_pos + 1);
}   //end func


// inserts an element at the given position in the slice and returns the new slice
func insert_element(old_buffer []int, new_element int, insert_idx int) (new_buffer []int) {
    // copy the elements before the insert index
    new_buffer = append(new_buffer, old_buffer[:insert_idx]...);

    // add the element which needs to be inserted
    new_buffer = append(new_buffer, new_element);

    // copy the rest of the elements
    new_buffer = append(new_buffer, old_buffer[insert_idx:]...);
    return;
}   //end func
