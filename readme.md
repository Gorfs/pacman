# Pacman Game

## Overview

Project Pre-pro 2 done in the first semester of the second year of the "Licence Informatique" for 
Universite Paris cite.

This Pacman game is a classic arcade game implemented in Java 17 with JavaFX. The project is configured with Gradle using the JavaFX plugin. This game is inspired by the original [Pac-Man](https://en.wikipedia.org/wiki/Pac-Man) game released in 1980.

The game principle is well-known and simple (refer to the Wikipedia page).

Currently, only some basic functionalities are implemented, and there are still bugs and improvements to be made.

Development goals:

- Debugging
- Rationalizing and documenting the code
- Implementing all the basic features of Pac-Man: the classic maze, ghost strategies, bonuses, the effect of super pellets, etc.
- Further enhancements: multiple levels, 3D, multiplayer, etc. (these are just suggestions)

## Instructions

### Downloading Pacman

The easiest way to download Pacman for development is to clone the repository from GitHub. From the console:

```bash
$ git clone https://github.com/Gorfs/pacman
```


## Execution and Compilation

After downloading/cloning the sources, you can compile and run the project using Gradle. The `gradlew` script in the project directory will download and use the correct Gradle version for the project.

To compile, execute the following command from the `pacman` directory:

```bash
`./gradlew build`
```

To run the game, execute the following command from the `pacman` directory:

```bash
`./gradlew run`
```

The project requires Java 17 to be compiled and executed.

### Special Cases

#### Working with Eclipse

Eclipse contains a distribution of Java 17 and can pass the correct configuration to Gradle. To work with Eclipse, launch Eclipse and import the project:

1. File > Import... > Gradle > Existing Gradle Project, Next >
2. Choose the path to the `pacman` directory and validate with Finish.

In the "Gradle Tasks" tab, you can find tasks to compile and run the project.

#### Working with other Java versions

To work with other Java versions, you may need to modify the `build.gradle` file.

Important: Commit the changes to `build.gradle` and push them to your fork so that everyone on your team works with the same Java version.

## Playing

Pacman is controlled with the four arrow keys.
