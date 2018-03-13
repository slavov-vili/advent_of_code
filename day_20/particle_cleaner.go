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

    // while input is being received
    println("Input:")
    for scanner.Scan() {
        // store the current input
        var input = scanner.Text();

        // if the input is NOT "END"
        if input != "END" {
            // TODO: process input
            continue;
        }   //end if


        // PART I


        // PART II


        // TODO: clear the particle list
    }   //end for
}   //end main



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





type Feature struct {
    x int
    y int
    z int
}   //end type


func NewFeature(new_x, new_y, new_z int) *Feature {
    var feature = new(Feature);

    feature.x = new_x;
    feature.y = new_y;
    feature.z = new_z;

    return feature;
}   //end func
