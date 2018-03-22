package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // maps the name of a state to its struct
    var states = make(map[string]*State, 0);
    // stores the name of the state which should begin
    var start_state_name string;
    // stores how many steps should be made before the checksum is performed
    var checksum_after int;

    // USED TO HANDLE INPUT
    // stores the state currently being built
    var cur_state_input *State;
    // stores the current value when parsing the input
    var cur_value_input int;
    // stores any error
    var err error;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {

            // if the input is an empty line
            if input == "" {
                if cur_state_input != nil {
                    states[cur_state_input.name] = cur_state_input;
                }   //end if
                cur_state_input = NewState("", []*Action{}, []*Action{});

            } else {
                // split the line into its words
                var words = strings.Fields(input);
                // remove the punctuation before the first word (if any)
                var first_word = words[0];
                if first_word == "-" {
                    first_word = words[1]
                }   //end if
                // remove the punctuation from the last word
                var last_word = words[len(words)-1];
                last_word = strings.TrimSuffix(last_word, ".");
                last_word = strings.TrimSuffix(last_word, ":");

                // consider the first word
                switch first_word {
                case "Begin":
                    // set the name of the starting state
                    start_state_name = last_word;

                case "Perform":
                    // store the number of steps before checksum should be performed
                    checksum_after, err = strconv.Atoi(words[len(words) - 2]);
                    if err != nil { log.Fatal(err) }

                case "In":
                    // set the current state's name
                    cur_state_input.name = last_word;

                case "If":
                    // store the current value
                    cur_value_input, err = strconv.Atoi(last_word);
                    if err != nil { log.Fatal(err) }

                default:
                    // append a new action to the list
                    cur_state_input.AppendAction(cur_value_input, NewAction(first_word, last_word));
                }   //end switch
            }   //end else

            continue;
        }   //end if
        // add the last state to the map
        states[cur_state_input.name] = cur_state_input;

        // represents the tape as a map of position and its value (0 or 1)
        var tape = make(map[int]int, 0);

        fmt.Println("Start state:",   start_state_name);
        fmt.Println("Checksum after", checksum_after, "steps!");
        fmt.Println("Tape:",          tape);
        for name, state := range states {
            fmt.Println(name, ":", state);
        }   //end for


        // PART I
        var cur_state_name = start_state_name;
        var cur_idx        = 0;
        for i := 0; i < checksum_after; i++ {
            cur_state_name, cur_idx = do_actions(cur_state_name, states, cur_idx, tape);
        }   //end for

        var checksum = 0;
        for _, val := range tape {
            checksum += val;
        }   //end for
        fmt.Println("Checksum:", checksum);

        // PART II


        // clear the states map and the tape
        states = make(map[string]*State, 0);
        tape = make(map[int]int, 0);
    }   //end for
}   //end main



// performs the appropriate actions given a current state and position on the tape
// returns the name of the next state and the next position on the tape
func do_actions(cur_state_name string, states map[string]*State, cur_idx int, tape map[int]int) (next_state_name string, next_idx int) {
    // store the current state
    var cur_state = states[cur_state_name];
    // store the current value
    var cur_value = tape[cur_idx];
    // stores the actions which need to be performed
    var actions []*Action;


    // store the actions based on the current value
    if cur_value == 0 {
        actions = cur_state.actions_0;
    } else {
        actions = cur_state.actions_1;
    }   //end else


    // for each action
    for _, action := range actions {
        switch action.name {
        case "Write":
            // convert the action's argument to an int
            var new_value, err = strconv.Atoi(action.argument);
            if err != nil { log.Fatal(err) }
            tape[cur_idx] = new_value;

        case "Move":
            if action.argument == "right" {
                next_idx = cur_idx + 1;
            } else {
                next_idx = cur_idx - 1;
            }   //end else

        case "Continue":
            next_state_name = action.argument;
        }   //end switch
    }   //end for

    return;
}   //end func





type State struct {
    name         string
    actions_0 []*Action
    actions_1 []*Action
}   //end type


func NewState(new_name string, new_actions_0, new_actions_1 []*Action) *State {
    var new_state = new(State);

    // set the state's name and slices
    new_state.name = new_name;
    new_state.actions_0 = make([]*Action, 0);
    new_state.actions_1 = make([]*Action, 0);

    // add all the actions in their corresponding lists
    for _, new_action := range new_actions_0 {
        new_state.actions_0 = append(new_state.actions_0, new_action);
    }   //end for
    for _, new_action := range new_actions_1 {
        new_state.actions_1 = append(new_state.actions_1, new_action);
    }   //end for
    return new_state;
}   //end func


// appends the given action to the corresponding list and returns the new list
func (state *State) AppendAction(cur_value int, action *Action) (new_actions []*Action) {
    if cur_value == 0 {
        state.actions_0 = append(state.actions_0, action);
        new_actions = make([]*Action, len(state.actions_0));
        copy(new_actions, state.actions_0);
    } else {
        state.actions_1 = append(state.actions_1, action);
        new_actions = make([]*Action, len(state.actions_1));
        copy(new_actions, state.actions_1);
    }   //end else
    return;
}   //end func


func (state State) String() string {
    var actions_0 = " ";
    var actions_1 = " ";

    for _, action := range state.actions_0 {
        actions_0 += action.String();
        actions_0 += " ";
    }   //end for
    for _, action := range state.actions_1 {
        actions_1 += action.String();
        actions_1 += " ";
    }   //end for

    return fmt.Sprintf("{%v [%v] [%v]}", state.name, actions_0, actions_1);
}   //end func





type Action struct {
    name     string
    argument string
}   //end type


func NewAction(new_name, new_arg string) *Action {
    var new_action = new(Action);
    new_action.name     = new_name;
    new_action.argument = new_arg;
    return new_action;
}   //end func


func (action Action) String() string {
    return fmt.Sprintf("{%v %v}", action.name, action.argument);
}   //end func
