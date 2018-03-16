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
        fmt.Println("Pattern flipped X:");
        fmt.Println(starting_pattern.GetFlipX());
        fmt.Println("Pattern flipped Y:");
        fmt.Println(starting_pattern.GetFlipY());


        // PART II

        // clear the input map
        rules = make(map[string]string, 0);
    }   //end for
}   //end main


// TODO: implement function to find the rule which matches the given pattern
// applies the rule which matches the given pattern and returns the result
func match_rule_by_pattern(pattern *Pattern, rules map[string]string) string {
    return "";
}   //end func





// a type representing a pattern in 2D space
type Pattern [][]string


func NewPattern(size int) *Pattern {
    var new_pattern = new(Pattern);
    *new_pattern = make([][]string, size);

    // for each line in the new pattern
    for i,_ := range *new_pattern {
        (*new_pattern)[i] = make([]string, size);
    }   //end for
    return new_pattern;
}   //end func


// return a pattern, created from the given rule
func PatternFromRule(rule string) *Pattern {
    // split the rule into the lines of the pattern
    var lines = strings.Split(rule, "/");
    // create a new pattern
    var pattern = NewPattern(len(lines));

    for i, line := range lines {
        // split the line into its characters and add it to the pattern
        (*pattern)[i] = strings.Split(line, "");
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


// returns the result of flipping the pattern along the X axis
func (pattern Pattern) GetFlipX() *Pattern {
    var pattern_size = len(pattern);
    var pattern_flipped = NewPattern(pattern_size);

    // for each line in the pattern
    for i, line := range pattern {
        // for each character in the line
        for j, char := range line {
            (*pattern_flipped)[pattern_size-i-1][j] = char;
        }   //end for
    }   //end for

    return pattern_flipped;
}   //end func


// returns the result of flipping the pattern along the Y axis
func (pattern Pattern) GetFlipY() *Pattern {
    var pattern_size = len(pattern);
    var pattern_flipped = NewPattern(pattern_size);

    // for each line of the pattern
    for i, line := range pattern {
        // for each character in the line
        for j, char := range line {
            (*pattern_flipped)[i][pattern_size-j-1] = char;
        }   //end for
    }   //end for

    return pattern_flipped;
}   //end func


// returns the result of rotating the give pattern by 90 degrees
func (pattern Pattern) GetRotate90() *Pattern {
    var pattern_size = len(pattern);
    var pattern_rotated = NewPattern(pattern_size);



    return pattern_rotated;
}   //end func


// TODO: implement function to split Pattern in smaller chunks


func (pattern Pattern) String() (pretty_pattern string) {
    // for each line in the pattern
    for _, line := range pattern {
        pretty_pattern += fmt.Sprintf("%v\n", strings.Join(line, ""));
    }   //end for
    return;
}   //end func
