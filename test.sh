#!/bin/bash

export DISPLAY=:0.0

rm -rf Game/p1/*
cp src/* Game/p1/
javac Game/p1/*

rm -rf Game/p2/*
cp src/* Game/p2/
javac Game/p2/*

rm -rf Game/p3/*
rm -rf Game/p4/*

cp Game/Player.java Game/p3/
cp Game/Player.java Game/p4/

javac Game/p3/*
javac Game/p4/*

java -jar Game/Game.jar Game/p1 Game/p2 Game/p3 Game/p4

