#!/bin/bash

FOLDER_PREFIX="day"
JAVA_PREFIX="Day"
TEMPLATE_DAY="20"

for NEXT_DAY in "21" "22" "23" "24" "25"
do
    FOLDER_NAME="${FOLDER_PREFIX}${NEXT_DAY}"
    JAVA_NAME="${JAVA_PREFIX}${NEXT_DAY}.java"

    cp -r "./${FOLDER_PREFIX}${TEMPLATE_DAY}" $FOLDER_NAME
    cd $FOLDER_NAME
    mv ${JAVA_PREFIX}${TEMPLATE_DAY}.java $JAVA_NAME

    sed -i -b "s/${TEMPLATE_DAY}/${NEXT_DAY}/g" $(ls)
    cd ..
done
