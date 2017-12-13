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
    // maps the name of a registries to its value
    var registries = make(map[string]int);
    // stores the max value in the registries map
    var max_value_end  = 0;
    // stores the max value found during the updating of the registries
    var max_value_ever = 0;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input line
        var input_line = scanner.Text();

        // if the input line is NOT "END"
        if (input_line != "END") {

            // split the registry name and the action from the condition
            var parts = strings.Split(input_line, "if");
            // store the registry name, the action and the argument
            var reg_name   = strings.Fields(parts[0])[0];
            var action     = strings.Fields(parts[0])[1];
            var argument,_ = strconv.Atoi(strings.Fields(parts[0])[2]);
            // store the operator and two operands of the condition
            var operand1   = strings.Fields(parts[1])[0];
            var log_op     = strings.Fields(parts[1])[1];
            var operand2,_ = strconv.Atoi(strings.Fields(parts[1])[2]);

            // if the condition is true
            if check_condition(registries, operand1, log_op, operand2) == true {
                // update the registry value
                var new_reg_value = calc_new_reg_value(registries[reg_name], action, argument);
                registries[reg_name] = new_reg_value;
                if new_reg_value > max_value_ever {
                    max_value_ever = new_reg_value;
                }   //end if
            }   //end if
            continue;
        }   //end if

        // for each value in each registry
        for _, reg_value := range registries {
            // find the max value
            if reg_value > max_value_end {
                max_value_end = reg_value;
            }   //end if
        }   //end for

        fmt.Println(registries)

        // print the maximum value in the registries
        print("Max value at the end: ")
        print(max_value_end)
        println()
        print("Max value ever: ")
        print(max_value_ever)
        println()

        // clean the input lines
        registries = make(map[string]int);
        max_value_end = 0;
        max_value_ever = 0;
    }   //end for
}   //end main



// Checks whether the given condition is true
// (usually compares the value of a registry to a given value)
//
// Arguments:
// input    - the data of all registries and their values
// reg_name - the name of the registry, whose value is being compared
// log_op   - the logical operator that governs the condition
// value    - the value to which the registry should be compared
//
// Returns:
// whether the logical operator holds between the two values
func check_condition(registries map[string]int, reg_name string, log_op string, value int) (result bool) {
    var reg_value = registries[reg_name];
    switch log_op {
    case "==": result = (reg_value == value);
    case "!=": result = (reg_value != value);
    case ">=": result = (reg_value >= value);
    case "<=": result = (reg_value <= value);
    case ">":  result = (reg_value > value);
    case "<":  result = (reg_value < value);
    }   //end switch
    return;
}   //end func



// Updates the given registry based on the provided action and its argument
//
// Arguments:
// old_reg_value - the value in the registry before the update
// action        - the action to be performed when calculating the new registry value
// argument      - the argument given to the action
//
// Returns:
// the new value of the registry
func calc_new_reg_value(old_reg_value int, action string, argument int) (new_reg_value int) {
    switch action {
    case "inc": new_reg_value = old_reg_value + argument;
    case "dec": new_reg_value = old_reg_value - argument;
    }   //end switch
    return;
}   //end func
