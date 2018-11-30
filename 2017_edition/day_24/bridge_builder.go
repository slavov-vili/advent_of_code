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
    // maps a port to a list of components
    var port2comps = make(map[int][]*Component, 0);
    // the port which starts the bridge
    const start_port = 0
    // stores the index of the last stored component
    var last_comp_idx = -1;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // split the component into its ports
            var parts = strings.Split(input, "/");
            // convert the 2 ports to ints
            var port_a_int,_ = strconv.Atoi(parts[0]);
            var port_b_int,_ = strconv.Atoi(parts[1]);

            // create a new component with the given id and ports
            var comp = NewComponent((last_comp_idx + 1), []int{port_a_int, port_b_int}, 2);
            last_comp_idx++;
            // map the new component to its ports
            port2comps[port_a_int] = append(port2comps[port_a_int], comp);
            port2comps[port_b_int] = append(port2comps[port_b_int], comp);

            continue;
        }   //end if


        // PART I
        var p1_bridge, p1_sum = find_best_bridge(start_port, port2comps, "sum");
        fmt.Println("Strongest bridge:");
        for _, comp := range p1_bridge {
            fmt.Print(*comp, " ");
        }   //end for
        println();
        fmt.Println("Strongest bridge strength:", p1_sum);


        // PART II
        var p2_bridge, p2_sum = find_best_bridge(start_port, port2comps, "length");
        fmt.Println("Longest bridge:");
        for _, comp := range p2_bridge {
            fmt.Print(*comp, " ");
        }   //end for
        println();
        fmt.Println("Longest bridge strength:", p2_sum);


        // clear the instruction list
        port2comps = make(map[int][]*Component, 0);
        last_comp_idx = 0;
    }   //end for
}   //end main



func find_best_bridge(start_port int, port2comps map[int][]*Component, condition string) (bridge []*Component, sum int) {
    var next_comps = port2comps[start_port];
    if len(next_comps) == 0 {
        return;
    }   //end if

    // for each component from the list
    for i,_ := range next_comps {
        // get a copy of the port map
        var port2comps_copy = get_map_copy(port2comps);
        // get the current component from the copy map
        // because values will be changed and we don't want to change the original map
        var comp = port2comps_copy[start_port][i];

        // make a temporary bridge with the given component
        var temp_bridge = []*Component{comp};
        // remove the component from the map of both its ports
        port2comps_copy = remove_from_map(comp, port2comps_copy);
        // create a temporary sum
        var temp_sum = comp.GetPortSum();

        // mark the component's port as busy
        var free_port = comp.BlockPort(start_port);
        // recursively find the rest of the bridge and the sum of the next components
        var bridge_rest, sum_rest = find_best_bridge(free_port, port2comps_copy, condition);

        // append the rest of the bridge and add to the sum
        temp_bridge = append(temp_bridge, bridge_rest...);
        temp_sum += sum_rest;
        
        switch condition {
        case "sum":
            if temp_sum > sum {
                bridge = temp_bridge;
                sum    = temp_sum;
            }   //end if

        case "length":
            if len(temp_bridge) > len(bridge) {
                bridge = temp_bridge;
                sum    = temp_sum;
            } else {
                if (len(temp_bridge) == len(bridge)) && (temp_sum > sum) {
                    bridge = temp_bridge;
                    sum    = temp_sum;
                }   //end if
            }   //end else
        }   //end switch

    }   //end for
    return;
}   //end func



// returns a copy of the given map
func get_map_copy(port2comps map[int][]*Component) map[int][]*Component {
    var port2comps_copy = make(map[int][]*Component, len(port2comps));

    // for each port and components in the old map
    for port, comps := range port2comps {
        var comps_copy = make([]*Component, len(comps));

        for i, comp := range comps {
            comps_copy[i] = NewComponent(comp.id, comp.ports, comp.free_port_idx);
        }   //end for

        port2comps_copy[port] = comps_copy;
    }   //end for
    return port2comps_copy;
}   //end func


// removes the element from the map of its ports
func remove_from_map(comp *Component, port2comps map[int][]*Component) map[int][]*Component {
    var port2comps_copy = get_map_copy(port2comps);

    // for each of the component's ports
    for _, port := range comp.ports {
        // for all other components in the port's list
        for i, comp2 := range port2comps_copy[port] {
            // if they have the same ID
            if comp.id == comp2.id {
                // remove the component from the list
                port2comps_copy[port] = remove_from_slice(i, port2comps_copy[port]);
                break;
            }   //end if
        }   //end for
    }   //end for

    return port2comps_copy;
}   //end func


// removes the element at the given index from the slice
func remove_from_slice(idx int, comps []*Component) []*Component {
    var new_comps = make([]*Component, 0);
    for i, comp := range comps {
        if i != idx {
            new_comps = append(new_comps, NewComponent(comp.id, comp.ports, comp.free_port_idx));
        }   //end if
    }   //end for
    return new_comps;
}   //end func





type Component struct {
    id              int
    ports         []int
    // stores the index of the free port
    // -1 = both ports are TAKEN
    //  0 or 1 = index of free port
    //  2 = both ports are FREE
    free_port_idx   int
}   //end type


func NewComponent(new_id int, new_ports []int, new_free_port_idx int) *Component {
    var new_component = new(Component);

    new_component.id = new_id;
    new_component.ports = make([]int, len(new_ports));
    for i, port := range new_ports {
        new_component.ports[i] = port;
    }   //end for

    new_component.free_port_idx = new_free_port_idx;

    return new_component;
}   //end func


func (comp Component) GetPortSum() (sum int) {
    for _, port := range comp.ports {
        sum += port;
    }   //end for
    return sum;
}   //end func


// blocks the given port of the component and returns the one that is left free
func (comp *Component) BlockPort(blocked_port int) (free_port int) {
    for i, port := range comp.ports {
        if port == blocked_port {
            comp.free_port_idx = len(comp.ports) - i - 1;
        }   //end if
    }   //end for

    free_port = comp.GetFreePorts()[0];
    return;
}   //end func


func (comp Component) GetFreePorts() (free_ports []int) {
    switch comp.free_port_idx {
    case 0:
        free_ports = append(free_ports, comp.ports[0]);

    case 1:
        free_ports = append(free_ports, comp.ports[1]);

    case 2:
        free_ports = append(free_ports, comp.ports[0]);
        free_ports = append(free_ports, comp.ports[1]);
    }   //end switch
    return;
}   //end func
