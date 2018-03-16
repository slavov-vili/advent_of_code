package main

import (
    "bufio"
    "fmt"
    "log"
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


        // PART II


        // clear the input map
        rules = make(map[string]string, 0);
    }   //end for
}   //end main



// applies the rule which matches the given pattern (in any form) and returns the result
func match_rule_by_pattern(pattern *Pattern, rules map[string]string) (result string) {
    // stores all the possible forms in which the given pattern can occur
    var possible_rules = make([]string, 0);

    // add the pattern itself
    possible_rules = append(possible_rules, RuleFromPattern(pattern));

    // add the flipped versions of the pattern
    possible_rules = append(possible_rules, RuleFromPattern(pattern.GetFlipX()));
    possible_rules = append(possible_rules, RuleFromPattern(pattern.GetFlipY()));

    // add the rotated versions of the pattern
    possible_rules = append(possible_rules, RuleFromPattern(pattern.GetRotate90()));
    possible_rules = append(possible_rules, RuleFromPattern(pattern.GetRotate90().GetRotate90()));
    possible_rules = append(possible_rules, RuleFromPattern(pattern.GetRotate90().GetRotate90().GetRotate90()));

    // stores whether a rule is found in the map
    var in_map = false;
    // for each possible rule form of the pattern
    for _, rule := range possible_rules {
        // try to get the result of applying a rule to the pattern
        result, in_map = rules[rule];
        if in_map {
            break;
        }   //end if
    }   //end for

    // if no form of the pattern was found in the rule book, complain
    if !in_map {
        log.Fatal("Pattern doesn't exist in rulebook under any form !");
    }   //end if

    return;
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


// returns the result of rotating the given pattern by 90 degrees
func (pattern Pattern) GetRotate90() *Pattern {
    var pattern_size = len(pattern);
    var pattern_rotated = NewPattern(pattern_size);

    // for each line in the pattern
    for i, line := range pattern {
        // for each character in the line
        for j, char := range line {
            (*pattern_rotated)[j][pattern_size-i-1] = char;
        }   //end for
    }   //end for

    return pattern_rotated;
}   //end func


// splits the pattern into equal squares
func (pattern Pattern) GetSubPatterns() (sub_patterns []*Pattern) {
    var pattern_size     = len(pattern);
    var sub_pattern_size = 0;
    if (pattern_size % 2) == 0 {
        sub_pattern_size = 2;
    } else {
        sub_pattern_size = 3;
    }   //end else

    // iterate over the lines of the pattern in steps = to 1 sub-pattern
    for start_row := 0; start_row < (pattern_size - 1); start_row += sub_pattern_size {
        // iterate over the columns of the pattern in steps = to 1 sub-pattern
        for start_column := 0; start_column < (pattern_size - 1); start_column += sub_pattern_size {
            var new_sub_pattern = NewPattern(0);

            // for each line, belonging to the current sub-pattern
            for i := start_row; i < (start_row + sub_pattern_size); i++ {
                var new_sub_line = make([]string, 0);

                // for each column, belonging to the current sub-pattern
                for j := start_column; j < (start_column + sub_pattern_size); j++ {
                    // append the character at this position to the sub-pattern's line
                    new_sub_line = append(new_sub_line, pattern[i][j]);
                }   //end for

                // append the sub-pattern's line 
                *new_sub_pattern = append(*new_sub_pattern, new_sub_line);
            }   //end for

            // add the sub-pattern to the list
            sub_patterns = append(sub_patterns, new_sub_pattern);
        }   //end for
    }   //end for

    return;
}   //end func


func (pattern Pattern) String() (pretty_pattern string) {
    // for each line in the pattern
    for _, line := range pattern {
        pretty_pattern += fmt.Sprintf("%v\n", strings.Join(line, ""));
    }   //end for
    return;
}   //end func
