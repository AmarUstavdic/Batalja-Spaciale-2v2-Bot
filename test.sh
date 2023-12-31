#!/bin/bash

BASE_DIR=$(dirname "$0")
SRC_DIR="$BASE_DIR/src"
GAME_DIR="$BASE_DIR/Game"
P1_DIR="$GAME_DIR/p1"
P2_DIR="$GAME_DIR/p2"
P3_DIR="$GAME_DIR/p3"
P4_DIR="$GAME_DIR/p4"

compile_and_copy() {
    # shellcheck disable=SC2115
    rm -rf "$1"/*
    cp "$SRC_DIR"/* "$1"/
    javac "$1"/*
}

compile_and_copy "$P1_DIR"
compile_and_copy "$P2_DIR"

# shellcheck disable=SC2115
rm -rf "$P3_DIR"/*
cp "$GAME_DIR/Player.java" "$P3_DIR"/
javac "$P3_DIR"/*

# shellcheck disable=SC2115
rm -rf "$P4_DIR"/*
cp "$GAME_DIR/Player.java" "$P4_DIR"/
javac "$P4_DIR"/*

# Run the game
java -jar "$GAME_DIR/Game.jar" "$P1_DIR" "$P2_DIR" "$P3_DIR" "$P4_DIR"
