package main

import (
    "bufio"
    "fmt"
    "log"
    "math"
    "os"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // maps the input of a rule to its output
    var rules = make(map[string]string, 0);
    // stores how many times the program should iterate in part I
    const part_1_iters = 5;

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
        var start_pattern = PatternFromRule(".#./..#/###");


        // PART I
        var final_pattern = expand_pattern(start_pattern, rules, part_1_iters);
        fmt.Printf("%v\n%v", "Final Pattern:", final_pattern)
        fmt.Println("Set bits after", part_1_iters, "expansions:", final_pattern.GetSetBits());

        // PART II


        // clear the input map
        rules = make(map[string]string, 0);
    }   //end for
}   //end main



// expands the given starting pattern 'count' times given a set of rules
func expand_pattern(start_pattern *Pattern, rules map[string]string, count int) *Pattern {
    // set the starting pattern to be the current pattern
    var cur_pattern = PatternFromPattern(start_pattern);

    // iterate the given amount of times
    for i := 0; i < count; i++ {
        // divide the pattern into sub-patterns
        var sub_patterns = cur_pattern.GetSubPatterns();

        // for each sub-pattern
        for j, sub_pattern := range sub_patterns {
            // apply the appropriate rule to the sub-pattern
            var sub_rule_match = match_rule_by_pattern(sub_pattern, rules);

            // replace the sub-pattern with the extended version
            sub_patterns[j] = PatternFromRule(sub_rule_match);
        }   //end for

        if len(sub_patterns) == 1 {
            cur_pattern = sub_patterns[0];
        } else {
            // join the list of sub-patterns
            cur_pattern = join_patterns(sub_patterns);
        }   //end if
    }   //end for

    return cur_pattern;
}   //end func



// joins the given pattern list
func join_patterns(patterns []*Pattern) *Pattern {
    var sub_pattern_size = len(*patterns[0]);
    // calculate how many patterns should be put in 1 row when joining them
    var patterns_per_row = int(math.Sqrt(float64(len(patterns))));
    var new_pattern = NewPattern(patterns_per_row * sub_pattern_size);

    // for each pattern in the list
    for pattern_idx, cur_pattern := range patterns {
        var start_row    = safe_division(pattern_idx, patterns_per_row) * sub_pattern_size;
        var start_column = safe_modulo(pattern_idx, patterns_per_row) * sub_pattern_size;

        //fmt.Println("Pattern", pattern_idx, "starts at row:", start_row, "and column:", start_column)

        // for each line in the current pattern
        for i, line := range *cur_pattern {
            // for each character in the line
            for j, char := range line {
                // set the corresponding character in the joined pattern
                (*new_pattern)[start_row + i][start_column + j] = char;
            }   //end for
        }   //end for
    }   //end for
    return new_pattern;
}   //end func



// applies the rule which matches the given pattern (in any form) and returns the result
func match_rule_by_pattern(pattern *Pattern, rules map[string]string) (result string) {
    var cur_pattern = PatternFromPattern(pattern);
    // stores if the pattern is found in the rulebook
    var in_map = false;
    // stores how many times the pattern has been rotated
    var rotate_count = 0;

    for rotate_count < 4 {
        // try to find the current pattern in the rulebook
        result, in_map = rules[RuleFromPattern(cur_pattern)];
        if in_map {
            break;
        }   //end if

        // try to find the flipped versions of the pattern in the rulebook
        result, in_map = rules[RuleFromPattern(cur_pattern.GetFlipX())];
        if in_map {
            break;
        }   //end if
        result, in_map = rules[RuleFromPattern(cur_pattern.GetFlipY())];
        if in_map {
            break;
        }   //end if

        // rotate the pattern
        cur_pattern = cur_pattern.GetRotate90();
        rotate_count++;
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


// returns a pattern, created from the given pattern
func PatternFromPattern(pattern *Pattern) *Pattern {
    var new_pattern = NewPattern(len(*pattern));

    // for each line in the pattern
    for i, line := range *pattern {
        // for each character in the line
        for j, char := range line {
            (*new_pattern)[i][j] = char;
        }   //end for
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


// returns the number of set bits in the pattern
func (pattern Pattern) GetSetBits() (count int) {
    for _, line := range pattern {
        for _, char := range line {
            if char == "#" {
                count++;
            }   //end if
        }   //end for
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
    var sub_pattern_size = get_divisor(pattern_size);

    // iterate over the lines of the pattern in steps = to 1 sub-pattern
    for start_row := 0; start_row < pattern_size; start_row += sub_pattern_size {
        // iterate over the columns of the pattern in steps = to 1 sub-pattern
        for start_column := 0; start_column < pattern_size; start_column += sub_pattern_size {
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



// returns whether the number is divisible by 2, if not - then it is divisible by 3
func get_divisor(number int) (divisor int) {
    if (number % 2) == 0 {
        divisor = 2;
    } else {
        divisor = 3;
    }   //end else
    return;
}   //end func


func safe_division(x, y int) int {
    if x == 0 {
        return 0;
    }   //end if
    return (x / y);
}   //end func


func safe_modulo(x, y int) int {
    if x == 0 {
        return 0;
    }   //end if
    return (x % y);
}   //end func
