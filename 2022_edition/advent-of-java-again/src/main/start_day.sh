#!/bin/sh

# $1 = day

if [ $# -eq 0 ]
then
    echo "Usage: start_day.sh <day>"
    exit 1
fi

CODE_DIR="./java/day$1"
RSRC_DIR="./resources/day$1"
MAIN_PATH="$CODE_DIR/Day$1Main.java"

mkdir $CODE_DIR $RSRC_DIR
cp ./resources/template_day.txt $MAIN_PATH

sed -i "s/ayXX/ay$1/" $MAIN_PATH

touch $RSRC_DIR/input.txt
cp ./resources/template_output.txt $RSRC_DIR/output.txt
