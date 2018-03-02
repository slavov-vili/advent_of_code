package main

import (
    "bufio"
    "fmt"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // factors used when multiplying the previous number for each generator
    const gen_A_factor     = 16807;
    const gen_B_factor     = 48271;
    // number to use when dividing after multiplication
    const gen_both_divisor = 2147483647;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // split the input in 2
        var input_parts   = strings.Fields(scanner.Text());
        // store the start values of both generators
        var gen_A_start,_ = strconv.Atoi(input_parts[0]);
        var gen_B_start,_ = strconv.Atoi(input_parts[1]);

        // make the 2 generators
        var gen_A = Generator{gen_A_start, gen_A_start, gen_A_factor, gen_both_divisor};
        var gen_B = Generator{gen_B_start, gen_B_start, gen_B_factor, gen_both_divisor};

        // PART I
        var low_16_equal_count = judge_lowest_16_bits(gen_A, gen_B, 40000000);
        fmt.Println("The 2 generators have", low_16_equal_count, "numbers in common");

        // PART II

    }   //end for
}   //end main



// advance the generators in each epoch and compare the numbers
func judge_lowest_16_bits(gen_A Generator, gen_B Generator, epochs int) (count int) {
    // for each epoch
    for i := 0; i<epochs; i++ {
        // generate the numbers from both generators
        var gen_A_number = gen_A.Generate_value();
        var gen_B_number = gen_B.Generate_value();


        // if the lowest 16 bits of both numbers are the same
        var num_A_bits = int16(gen_A_number);
        var num_B_bits = int16(gen_B_number);

        if num_A_bits == num_B_bits { count++; }
    }   //end for
    return;
}   //end func



type Generator struct {
    start_value int
    prev_value  int
    factor      int
    divisor     int
}   //end type

// produces and returns the next value in the generator
func (gen *Generator) Generate_value() int {
    gen.prev_value = ((gen.prev_value * gen.factor) % gen.divisor);

    return gen.prev_value;
}   //end func
