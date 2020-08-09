# Chaps Challenge

Chap’s challenge is a creative clone of the (first level of the) 1989 Atari game [Chips Challenge](https://en.wikipedia.org/wiki/Chip%27s_Challenge).

## Basics

The objective of this game is to collect all of the chips/treasures on every level before advancing onto the next level. In order to obtain these chips the player must overcome obsticals such ast traversing water, avoiding hostile mobs and unlocking locked doors. 

The design elements drew heavy inspiration from the online flash game [squaremeal](http://www.nitrome.com/games/squaremeal/). 

## Playing

Start the game by invoking the main method in the application package.
```
nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge.main
```
Enable level 2 by dropping the level-2.zip into the src/levels directory.
All dynamic class loading should be done automatically.

## Create Recordings

To create a new recording, use the record and play functionality by selecting the "Recording Options > Start recording" option from the top bar of the gui. 
This will start recording all of your (and any mobs) actions. When you are finished your recording, select "Recording options > Save Recording". 
This will stop recording your moves and will create a file "record.txt".

If you die during a recording, the recording is deleted and cannot be saved.

## Loading and Save Recordings

To load and play a recording Select "Recording Options > Load Recording" from the top bar of the gui. This will load the recording stored in "record.txt"
if present. All movement will be disabled and the game will be set to the state of the recording start.

To step through the recording, use the "Step Recording" menu item.

To playback the recording, use the "Playback Recording" menu item.

Playback speed can be selected from the "Playback speed" menu item.

After the recording finishes, movement will be enabled and you may continue from the end of the recording.

## Authors

* Harrison Cook
* Jacob Fraser
* Luisa Kristen
* Zac Durant
* Zac Scott
