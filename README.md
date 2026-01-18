# 🐍 Snakes and Ladders – Software Engineering Project

<img width="842" height="525" alt="image" src="https://github.com/user-attachments/assets/3f69a24e-53ed-49b8-a30d-67bc668233f6" />


This project is a **Java-based simulation of the classic Snakes and Ladders board game**, developed as part of the *Software Engineering* course.
The application allows users to configure and observe different game variants through a graphical interface. 

---

##  Context

* **Course:** Software Engineering
* **Academic Year:** 2022–2023
* **University:** Università della Calabria
* **Project Type:** Individual project

---

##  Project Goals

The main goal of the project is to provide a **configurable and extensible simulation** of the Snakes and Ladders game, allowing the user to:

* define custom game rules and variants
* observe the game execution step by step
* analyze the behavior of the system through both graphical and textual feedback

The project emphasizes **object-oriented design** and the use of **design patterns** to manage complexity.

---

##  Technologies

* **Language:** Java (Java SE)
* **GUI:** Java Swing
* **Paradigm:** Object-Oriented Programming
* **Persistence:** Java Serialization

---

##  Main Features

* Configurable board size (6x8 or 10x10)
* Support for 1 to 4 players (including single-player mode)
* Automatic game simulation (observer-only interaction)
* Graphical visualization of player movement
* Textual log explaining each move and rule application
* Save and restore last game configuration

---

##  Game Variants

The user can enable multiple optional rule variants during configuration, including:

* **Single Die Mode**
* **Single Die Near Finish**
* **Double Six Rule**
* **Bonus Cells**
* **Stop Cells**
* **Draw-a-Card Cells** (with different effects)

Incompatible options are automatically handled by the configuration interface.

---

##  Software Architecture & Design Patterns

The project makes extensive use of design patterns to improve modularity and maintainability:

* **Observer**

  * To update the graphical board when player positions change
  * To implement a textual logger describing the simulation steps

* **State**

  * To manage different movement behaviors based on game configuration
  * To define different behaviors depending on the type of board cell

* **Builder**

  * To incrementally construct the game configuration based on user choices

This results in a **distributed-control architecture**, avoiding large conditional blocks and improving code clarity.


