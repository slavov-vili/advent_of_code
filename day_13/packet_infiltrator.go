package main

import (
    "bufio"
    //"fmt"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores all the layers
    var layers  = make([]*Layer, 0);

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // split the layer id from its size
            var parts = strings.Split(input, ":");

            // store the layer's ID
            var id, _   = strconv.Atoi(parts[0]);
            // trim the spaces of the size
            var size, _ = strconv.Atoi(strings.TrimSpace(parts[1]));

            var next_id = len(layers);
            // if the layer ID is NOT the next one on the list
            if id != next_id {
                // fill in the missing layers before this one
                for next_id < id {
                    layers = append(layers, NewLayer(next_id, -1, 0, "zero"));
                
                    // update the next id
                    next_id++;
                }   //end for
            }   //end if

            // create a new layer and add the layer to the list
            layers = append(layers, NewLayer(id, 0, size, "right"));
            continue;
        }   //end if

        // PART I
        // try to escape through the layers and get the score
        var escape_score = try_to_escape(layers);
        println("Escape score:", escape_score)

        // clear the layers list
        layers = make([]*Layer, 0);
    }   //end for
}   //end main



// moves through the layers updating their scanners and calculating the escape score
// (how many times it was detected)
func try_to_escape(layers []*Layer) (score int) {
    // !! THE PACKAGE ALWAYS MOVES ALONG THE BOTTOM OF THE LAYER !!!
    const bottom = 0;

    // move the package along the layers
    for _, layer := range layers {
        // if the scanner was at the bottom before the package was moved
        if layer.scanner_at == bottom {
            println("Detected at", layer.id)
            // !!! WE WERE DETECTED !!!
            // update the score
            score += (layer.id * layer.size);
        }   //end if

        // move the scanner for all layers
        for _, layer2 := range layers {
            layer2.move_scanner();
        }   //end for
    }   //end for

    return;
}   //end func



// a structure to represent a layer
type Layer struct {
    id         int       // the layer's ID
    scanner_at int       // the index at which the scanner is found
    size       int       // the size of the layer (how deep it is)
    direction  string    // the direction in which the scanner should move
}   //end type

func NewLayer(init_id int, init_scanner_idx int, init_size int, init_direction string) *Layer {
    var new_layer = new(Layer);
    new_layer.id         = init_id;
    new_layer.scanner_at = init_scanner_idx;
    new_layer.size       = init_size;
    new_layer.direction  = init_direction;
    return new_layer
}   //end func

// advances the scanner to the next position in the layer
// based on the direction in which it should move
// once the scanner reaches the end, the position changes
func (layer *Layer) move_scanner() (new_position int) {
    switch layer.direction {
        case "right": {
            // if the scanner is already at the end of the layer
            if layer.scanner_at == (layer.size - 1) {
                // change the direction in which the scanner should move
                layer.direction = "left";
                new_position = layer.scanner_at - 1;
            // if the scanner hasn't reached the end
            } else {
                new_position = layer.scanner_at + 1;
            }   //end else
        }   //end case
        case "left": {
            // if the scanner is already at the beginning of the layer
            if layer.scanner_at == 0 {
                // change the direction in which the scanner should move
                layer.direction = "right";
                new_position = layer.scanner_at + 1;
            // if the scanner hasn't reached the beginning
            } else {
                new_position = layer.scanner_at - 1;
            }   //end else
        }   //end case
        default: new_position = (-1);
    }   //end switch
    layer.scanner_at = new_position;
    return;
}   //end func
