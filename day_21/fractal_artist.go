package main

import (
    "bufio"
    "fmt"
    "os"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // maps the input of a rule to its output
    var rules = make(map[string]string, 0);

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // split the line of input into its part
            var parts = strings.Split(input, " => ");
            // add the rule to the map
            rules[parts[0]] = parts[1];
            continue;
        }   //end if

        // create the starting pattern of the art
        var starting_pattern = PatternFromRule(".#./..#/###");


        // PART I
        fmt.Println("Starting Pattern:");
        fmt.Println(starting_pattern);
        fmt.Println("Starting Rule:")
        fmt.Println(RuleFromPattern(starting_pattern));


        // PART II

        // clear the input map
        rules = make(map[string]string, 0);
    }   //end for
}   //end main





// a type representing a pattern in 2D space
type Pattern [][]string


func (pattern Pattern) String() (pretty_pattern string) {
    // for each line in the pattern
    for _, line := range pattern {
        pretty_pattern += fmt.Sprintf("%v\n", strings.Join(line, ""));
    }   //end for
    return;
}   //end func


// return a pattern, created from the given rule
func PatternFromRule(rule string) *Pattern {
    var pattern = new(Pattern);

    // split the rule into the lines of the pattern
    var lines = strings.Split(rule, "/");
    for _, line := range lines {
        // split the line into its characters and add it to the pattern
        *pattern = append(*pattern, strings.Split(line, ""));
    }   //end for
    return pattern;
}   //end func


// returns the rule, matching the given pattern
func RuleFromPattern(pattern *Pattern) (rule string) {
    for i, line := range *pattern {
        var format_string = "%v";

        // don't add a slash after the last line
        if i != (len(*pattern) - 1) {
            format_string += "/";
        }   //end if
        rule += fmt.Sprintf(format_string, strings.Join(line, ""));
    }   //end for
    return;
}   //end func
