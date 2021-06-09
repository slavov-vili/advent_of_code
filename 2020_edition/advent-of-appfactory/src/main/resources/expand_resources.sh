#!/bin/bash
FOLDER_PREFIX="day"
TEMPLATE_DAY="20"

for NEXT_DAY in "21" "22" "23" "24" "25"
do
    cp -r "./${FOLDER_PREFIX}${TEMPLATE_DAY}" $FOLDER_PREFIX$NEXT_DAY
done
