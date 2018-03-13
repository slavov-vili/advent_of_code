package main

// TODO: finish String functions and fix "NewFeature" and update calls to "NewFeature"

import (
    "bufio"
    "fmt"
    "log"
    "math"
    "os"
    "strconv"
    "strings"
)   //end imports


func main() {
    // create a scanner to read the input
    var scanner = bufio.NewScanner(os.Stdin);
    // stores the ID of the last seen particle
    var last_particle_id = 0;
    // stores the parsed particle from the input
    var new_particle *Particle;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            new_particle, last_particle_id = parse_input_line(input, last_particle_id);
            fmt.Println(*new_particle)
            continue;
        }   //end if


        // PART I


        // PART II


        // TODO: clear the particle list
    }   //end for
}   //end main



// parses a line of the input and returns a new particle storing all the data and its id
func parse_input_line(line string, last_particle_id int) (*Particle, int) {
    // store the different features
    var feature_pos *Feature;
    var feature_vel *Feature;
    var feature_acc *Feature;
    // split the different features of the line
    var features_str = strings.Split(line, ", ");

    // for each feature in the line
    for _, feature_str := range features_str {
        // split the name of the feature from its values
        var parts = strings.Split(feature_str, "=");
        // remove the opening and closing angle brackets
        parts[1] = strings.TrimPrefix(parts[1], "<");
        parts[1] = strings.TrimSuffix(parts[1], ">");

        // split the values from one another
        var values_str = strings.Split(parts[1], ",");

        // convert the different values into ints
        value_x, err := strconv.Atoi(values_str[0]);
        if err != nil { log.Fatal(err) };
        value_y, err := strconv.Atoi(values_str[1]);
        if err != nil { log.Fatal(err) };
        value_z, err := strconv.Atoi(values_str[2]);
        if err != nil { log.Fatal(err) };

        // create a new feature with the given values
        var feature_struct = NewFeature(value_x, value_y, value_z);

        // based on the feature's name - store it in the correct variable
        switch parts[0] {
        case "p":
            feature_pos = feature_struct;

        case "v":
            feature_vel = feature_struct;

        case "a":
            feature_acc = feature_struct;
        }   //end switch
    }   //end for

    // create a new particle with an incremented ID and the given features
    var new_particle = NewParticle((last_particle_id + 1), feature_pos, feature_vel, feature_acc);

    return new_particle, new_particle.id;
}   //end func



// return the Manhattan distance between the two positions
func calc_manh_dist(pos_a, pos_b *Feature) (manh_dist int) {
    return int(math.Abs(float64(pos_a.x - pos_b.x)) +
               math.Abs(float64(pos_a.y - pos_b.y)) +
               math.Abs(float64(pos_a.z - pos_b.z)));
}   //end func





type Particle struct {
    id int
    pos *Feature
    vel *Feature
    acc *Feature
}   //end type


func NewParticle(new_id int, new_pos, new_vel, new_acc *Feature) *Particle {
    var particle = new(Particle);

    particle.id  = new_id;
    particle.pos = new_pos;
    particle.vel = new_vel;
    particle.acc = new_acc;

    return particle;
}   //end func


// updates the values in the particle and then return the new position
func (particle Particle) Update() (new_pos *Feature) {
    // increase velocity
    particle.vel.x += particle.acc.x;
    particle.vel.y += particle.acc.y;
    particle.vel.z += particle.acc.z;

    // increase position
    particle.pos.x += particle.vel.x;
    particle.pos.y += particle.vel.y;
    particle.pos.z += particle.vel.z;

    return particle.pos;
}   //end func


// returns the Manhattan distance between this particle and the center of the 3D space
func (particle Particle) DistFromZero(other_particle *Particle) (manh_dist_from_0 int) {
    var zero_feature = NewFeature(0, 0, 0);
    return particle.DistFrom(NewParticle(-1, zero_feature, zero_feature, zero_feature));
}   //end func


// returns the Manhattan distance between this particle and another particle
func (particle Particle) DistFrom(other_particle *Particle) (manh_dist int) {
    return calc_manh_dist(particle.pos, other_particle.pos);
}   //end func


func (particle Particle) String() string {
    return fmt.Sprintf("%v:{%v, %v, %v}", particle.id, particle.pos, particle.vel, particle.acc);
}   //end func





type Feature struct {
    name string
    x    int
    y    int
    z    int
}   //end type


func NewFeature(new_x, new_y, new_z int) *Feature {
    var feature = new(Feature);

    feature.x = new_x;
    feature.y = new_y;
    feature.z = new_z;

    return feature;
}   //end func


func (feature Feature) String() string {
    return fmt.Sprintf("", feature.x)
}   //end func
