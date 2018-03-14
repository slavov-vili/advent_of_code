package main

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
    // maps particle IDs to a pointer to an actual particle structure
    var particles = make(map[int]*Particle, 0);
    // stores the ID of the last seen particle
    var last_particle_id = -1;
    // stores the parsed particle from the input
    var new_particle *Particle;

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // parse the input and store the data into a struct
            new_particle, last_particle_id = parse_input_line(input, last_particle_id);
            // map the particle's ID to the struct
            particles[last_particle_id] = new_particle;
            continue;
        }   //end if


        // PART I
        fmt.Println("Particle", get_closest_to_0(particles), "will stay closest to 0!");


        // PART II


        // clear the particle container
        particles = make(map[int]*Particle, 0);
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
        var feature_struct = NewFeature(parts[0], value_x, value_y, value_z);

        // based on the feature's name - store it in the correct variable
        switch feature_struct.name {
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



// finds the particle, which will stay closest to the 0 point of the 3D space
func get_closest_to_0(particles map[int]*Particle) (closest_id int) {
    // ASSUMPTION: the particle with the lowest acceleration will stay near 0

    // convert the map to a list
    var particles_list = make([]*Particle, 0);
    for i:=0; i<len(particles); i++ {
        particles_list = append(particles_list, particles[i]);
    }   //end for
    // get all particles with the lowest acceleration
    var closest_to_0_ids = get_lowest_feat_sum(particles_list, "acc");
    //fmt.Println("Particles with minimum acceleration:", closest_to_0_ids);

    if len(closest_to_0_ids) != 0 {
        log.Fatal("Multiple particles with lowest acceleration")
    } else {
        closest_id = closest_to_0_ids[0];
    }   //end else

    return;
}   //end func


// gets a list of particles who have the lowest value for a given feature
func get_lowest_feat_sum(particles []*Particle, feature_name string) (lowest_ids []int) {
    // set the minimum value for the feature to be that of particle '0'
    var min_feat_value = particles[0].GetFeature(feature_name).GetAbsSum();

    // for each particle
    for id, particle := range particles {
        // store the absolute sum of the particle's feature
        var temp_abs_sum = particle.GetFeature(feature_name).GetAbsSum();

        // if the absolute sum is lower than the minimum
        if temp_abs_sum < min_feat_value {
            // update the minimum value
            min_feat_value = temp_abs_sum;
            // all previous particles are further, so clear the list
            lowest_ids = []int{id};
        // if the absolute sum is equal to the minimum
        } else if temp_abs_sum == min_feat_value {
            // add the id of the particle to the list
            lowest_ids = append(lowest_ids, id);
        }   //end else
    }   //end for
    return;
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


// returns a given feature for the particle based on the feature's name
func (particle Particle) GetFeature(feature_name string) (feature *Feature) {
    switch feature_name {
    case "acc":
        feature = particle.acc;

    case "vel":
        feature = particle.vel;

    case "pos":
        feature = particle.pos;
    }   //end switch
    return;
}   //end func


// updates the values in the particle and then return the new position
func (particle Particle) Update() (new_pos *Feature) {
    // increase velocity
    particle.vel = add_features(particle.vel, particle.acc);

    // increase position
    particle.pos = add_features(particle.pos, particle.vel);

    return particle.pos;
}   //end func


// returns the Manhattan distance between this particle and the center of the 3D space
func (particle Particle) DistFromZero() (manh_dist_from_0 int) {
    var zero_feature = NewFeature("zero", 0, 0, 0);
    return particle.DistFrom(NewParticle(-1, zero_feature, zero_feature, zero_feature));
}   //end func


// returns the Manhattan distance between this particle and another particle
func (particle Particle) DistFrom(other_particle *Particle) (manh_dist int) {
    return calc_manh_dist(particle.pos, other_particle.pos);
}   //end func


func (particle Particle) String() string {
    return fmt.Sprintf("%v:{%v, %v, %v}", particle.id, particle.pos, particle.vel, particle.acc);
}   //end func



// return the Manhattan distance between the two positions
func calc_manh_dist(pos_a, pos_b *Feature) (manh_dist int) {
    return int(math.Abs(float64(pos_a.x - pos_b.x)) +
               math.Abs(float64(pos_a.y - pos_b.y)) +
               math.Abs(float64(pos_a.z - pos_b.z)));
}   //end func





type Feature struct {
    name string
    x    int
    y    int
    z    int
}   //end type


func NewFeature(new_name string, new_x, new_y, new_z int) *Feature {
    var feature = new(Feature);

    feature.name = new_name;
    feature.x    = new_x;
    feature.y    = new_y;
    feature.z    = new_z;

    return feature;
}   //end func


// returns the sum of the absolute values of the feature's coordinates
func (feature Feature) GetAbsSum() int {
    return int(math.Abs(float64(feature.x)) +
               math.Abs(float64(feature.y)) +
               math.Abs(float64(feature.z)));
}   //end func


func (feature Feature) String() string {
    return fmt.Sprintf("%v:{%v,%v,%v}", feature.name, feature.x, feature.y, feature.z);
}   //end func


// adds the corresponding coordinates of feat2 to feat1
func add_features(feat1, feat2 *Feature) *Feature {
    return NewFeature(feat1.name,
                     (feat1.x + feat2.x),
                     (feat1.y + feat2.y),
                     (feat1.z + feat2.z));
}   //end func
