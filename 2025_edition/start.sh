#!/bin/bash

DAY="day$1"
MAIN="$DAY/Main.kts"
INPUT="$DAY/input.txt"
OUTPUT="$DAY/output.txt"

mkdir "$DAY"
touch "$MAIN" "$INPUT" "$OUTPUT"



cat << EOF > "$MAIN"
package $DAY

import java.io.File

val input = File("input.txt").readLines().filterNot(String::isEmpty)
EOF



cat << EOF > "$OUTPUT"
A: 

B: 
EOF
