## LDTS_<2><6> - <Ascent>

In this roguelike dungeon-crawler, you, the player, must go through several dangerous levels of the deep-dark caves above Narnia, in which  you will fight through hordes of enemies to ascend to the next level. As you ascend, the danger will continuously escalate, but so will your ability to deal with them.

This project was developed by **Afonso Moura** (**up202403939*@fe.up.pt**), **Daniel Lobo** (**up2023...@fe.up.pt**) and **Nuno Coimbra** (**up202405191@fe.up.pt**) for LDTS 2025/2026.

### IMPLEMENTED FEATURES

- **Ascent.Game loop** - The game continuously reads and interprets the input of the player
- **Character movement** - The player can control their character using the arrow keys or wasd keys. They can also only change their looking direction by holding SHIFT while pressing the movement keys.
- **Collision detection** - The player character cannot phase through other entities.
- **Multiple levels** - The game progresses through 5 hand-made levels that are stored in the 'levels' folder.
- **Stats** - The player and the monsters have different stats, used to calculate fights, movement and awareness.
- **Monster Pathfinding** - Each monster has an awareness stat that determines how far they can detect the player and move towards them.
- **Menus** - The game has separate menus for: Main menu, Game menu, Death screen & Win screen.
- **Interactions** - The player can open & close doors & chests, pick up or equip items from chests & go up levels through stairs.
- **Items** - Each level has chests that contain items, from equipment to health potions, that affect the players stats and are essential for progression.
- **Game HUD** - The game displays an in-game HUD that displays player stats, the player inventory, stats of the last monster the player interacted with (if any) and an interaction dialog if the player is looking at any interactable entity.

![Game GIF](Playing_Ascent.gif)

### DESIGN

**THERE SHOULD BE A WAY TO PLAY TO MOVE THE GAME FORWARD**

For a non-turn-based game, there must be a way to move the entities in-game as time progresses.

**The Pattern**

We applied the **Game Loop** pattern. Every time an in-game tick elapses, every single `Entity` "moves" a step forward.

**Implementation**

It's pretty much everywhere in the code, but the game loop is started in the `Game` class, found in the following file:

(src/main/java/Ascent/Game)

**THERE SHOULD ONLY BE 1 ITEMFACTORY**
 
The ItemFactory is the class responsible for creating all items in the game like weapons, armour and potions. Without any control, we could create multiple factories across the code, and each one would have its own Random object. This could lead to inconsistent item generation. Also, having multiple factories makes no sense since all items follow the same creation rules. We needed a way to guarantee that only one factory exists in the whole game.
 
**The Pattern**
 
We applied the **Singleton** pattern. This pattern ensures that a class has only one instance and provides a global point of access to it. This pattern fits our problem because we need exactly one `ItemFactory` shared by everyone, and `getInstance()` gives us an easy way to access it from anywhere.
 
**Implementation**
 
This class can be found in the following file:
 
(src/main/java/Ascent.model/item/factories/ItemFactory.java)

**Consequences**

Using this pattern, we guarantee that only one factory exists, so item creation is always consistent. The instance is created only when first needed, which saves resources. However, the singleton makes testing harder due to its global Ascent.state, and it’s not thread-safe but that’s fine since our game runs on a single thread.
 
**CREATING MONSTERS IN THE MIDDLE OF GAMEPLAY AFFECTS PERFORMANCE**

Our game will have multiple levels and each level can have many monsters. When the player moves to a new level, we need to spawn new monsters and get rid of the old ones. The problem is that creating a new `Monster` object takes time because we need to set up its `Stats`, position and other properties. If we keep creating and destroying monsters, the garbage collector runs often and causes small pauses in the game. Since we plan to have many levels with lots of monsters, this problem would only get worse. After implementing the fix for this problem, we also found that the game needs to make sure that there is only one pool of monsters in the game, otherwise monsters taken from one pool could not be returned to another, which would break the whole system.
 
**The Pattern**
 
We applied the **Object Pool** pattern combined with the **Singleton** pattern. The Object Pool keeps a set of pre-created monsters ready to use. When we need a monster, we take it from the pool with `acquire()`. When we are done, we return it with `release()` instead of destroying it. The Singleton ensures only one pool exists. These patterns fit our problem because we use and release monsters frequently, all monsters have the same structure, and resetting a monster is much faster than creating a new one.
 
**Implementation**
 
These classes can be found in the following files:

(src/main/java/Ascent/model/entities/poolsMonsterPool.java)
(src/main/java/Ascent/model/entities/poolsMonster.java)
(src/main/java/Ascent/model/game/level/BaseplateBuilder.java)

**Consequences**
 
Using these patterns, we avoid creating monsters during gameplay, which improves performance. The garbage collector runs less often, making the game smoother.  On the other hand, the code is more complex, because we need to manage the pool, reset methods and active states. We must also remember to reset monsters before using them, and if we need more than 100 monsters at once, the pool will run out.

**IMPLEMENTING DIFFERENT GAME STATES**

Whether the player is in a menu or in game should change the steps the code runs.

**The Pattern**

We applied the **State** pattern, implementing it as a *Stack of States*. Whenever the game is first started, a `GameMenuState` will be pushed to the stack, and will stay there until the player chooses the 'Exit' option in the menu at any time. Whenever we start the game or do any other action that implies switching to another state, the code pushes the state into the stack. When there's an action that represents the end of the current state, the code pops the topmost state on the stack.

**Implementation**

The State classes can be found in the following folder:

(src/main/java/Ascent/state)

**Consequences**

Using the specific stack of patterns implementation impedes us from creating a menu that allows viewing the current game map as a way to plan the next step,
since the game state gets pushed down the stack and out of the top spot.

**DIVIDING STEPS TO CREATE FLOOR**

Constructing a game level `Floor` is a complex task that involves assembling various components:
walls, monsters, chests, and doors. 
Initializing all these parts directly inside the Floor class would result in overly complicated initialization 
code and make the level logic dependent on a single way of loading data. 
We needed a flexible solution to construct levels step-by-step and support different creation strategies in the future.

**The Pattern**

We applied the **Builder** pattern. 
The `FloorBuilder` abstract class defines the 
standard steps for building a level
(`createFloor`, `getWalls`, `getMonsters`, etc...),
acting as the blueprint. 
The `FileLevelBuilder` is a concrete implementation that fulfills 
these steps by parsing a text file.

**Implementation**

These classes can be found in the following files:

(src/main/java/ascent/model/game/floor/FloorBuilder.java)
(src/main/java/ascent/model/game/floor/FileLevelBuilder.java)

**Consequences**

*   **Separation of Concerns**: The complex logic of parsing files is isolated in `FileLevelBuilder`, keeping the `Floor` class clean and focused only on game data.
*   **Extensibility**: We can easily add new ways to create levels 
by simply extending `FloorBuilder`, without modifying the existing game code.
*   **Modularity**: The construction process is broken down into discrete steps, making the code more readable and maintainable.

**DECOUPLING PATHFINDING IMPLEMENTATION FROM USAGE**

Monsters need to find a path to the player. 
However, we might want to change the pathfinding algorithm in the future or use a dummy implementation for testing, without rewriting the monster logic.

**The Pattern**

We applied the **Strategy** pattern. 
We defined an interface `IPathFinder` that declares the `findNextStep` method.
The `PathFinder` class implements this interface with a specific algorithm. 
The monsters rely on the interface, not the concrete implementation,
allowing us to swap strategies easily.

**Implementation**

This pattern is defined in:
*   (src/main/java/ascent/model/game/IPathFinder.java)
*   (src/main/java/ascent/model/game/PathFinder.java)

**Consequences**

Makes it easier to introduce different pathfinding algorithms by simply passing
a different strategy implementation.

**HANDLING USER ACTIONS**

The game needs to process various inputs (up, down, interact, quit) from the keyboard. 
Hardcoding these checks inside the game loop leads to a mess of `if-else` statements. 
We need to decouple the request for an action from the execution of that action.

**The Pattern**

We applied a variation of the **Command** pattern. The `ACTION` enum represents the commands that can be issued by the user. 
The `Controller` then interprets these command objects and executes the corresponding logic (`step`).

**Implementation**

(src/main/java/ascent/gui/ACTION.java)
(src/main/java/ascent/controller/Controller.java)

**Consequences**

* **Flexibility**: Allows mapping multiple inputs to a single ACTION without altering core logic.

* **Readability**: Replaces messy key-code checks with clean, readable switch statements using the ACTION enum.

* **Extensibility**: New game commands can be added systematically by simply updating the Enum and the Controller.

**COMPOSITION OVER INHERITANCE**

Initially, one might be tempted to create deep inheritance trees for game entities. 
However, entities share behaviors that don't fit a strict hierarchy, for example both players and monsters have `Stats`.
Forcing this via inheritance leads to rigid code.

**The Pattern**

We applied the **Component** pattern. Instead of inheriting all behavior, the `Entity` class, and its subclasses like `Player` and `Monster`, 
holds instances of components that provide specific capabilities, such as `Stats` for health/damage, `Inventory` for items, and `LOOKING` for orientation.

**Implementation**

The components are isolated in their own package:

(src/main/java/ascent/model/entities/components/Stats.java)
(src/main/java/ascent/model/entities/components/Inventory.java)

**Consequences**

This makes the `Entity` classes lighter and more focused.

**ELIMINATING SWITCH STATEMENTS WITH SMART ENUMS**

In many games, handling directions or types usually leads to large `switch` statements or generic `if-else` chains in the Controller. 
This makes the code brittle and hard to extend. We needed a way to bundle the data (vectors) and behavior (movement calculation) directly within the definition of the direction itself.

**The Pattern**

We applied the **State Pattern** (specifically via **Smart Enums**). 
As described in *["Enums in Java: From constants to the State pattern"](https://www.ensolvers.com/post/enums-in-java-from-constants-to-the-state-pattern)*, Java enums can implement methods and hold specific data for each constant. By treating the Enum as a state-carrier that knows how to behave, we replace external procedural logic with internal polymorphism.

**Implementation**

*   **`LOOKING` Enum**: Instead of just being a label, for example `UP`, `DOWN`, `LEFT`, and `RIGHT`, it stores their delta coordinates (`dx`, `dy`) and the symbol. 
More importantly, the `move(Position)` method calculates the next position based on the enum's specific data. 
The Controller simply calls `currentDirection.move(pos)`, without knowing which direction acts.
*   **`MonsterType` Enum**: Defines not just the name, but the specific configuration data (health, damage, color) and factory methods (`createBaseStats`) for that specific type.

These implementations can be found in:
*   (src/main/java/ascent/model/entities/components/LOOKING.java)
*   (src/main/java/ascent/model/entities/monster/MonsterType.java)

**Consequences**

*   **Cleaner Code**: We removed direction-checking switch statements from the movement logic.
*   **Extensibility**: Adding a new direction would only require adding a line to the Enum and the Controller logic would remain untouched.
*   **Safety**: Impossible to define an invalid direction with missing data, as the Enum constructor enforces the presence of deltas and symbols.

*A UML class diagram showcasing some of the used Design Patterns:*

![UML Design Patterns](Ascent.png)

*(Due to the UML being large, it may appear blurry. The original image is located at:)*

*(docs/Ascent.png)*

### CODE SMELLS

This section describes the code smells identified in the current implementation of the project, representing areas that would benefit from refactoring to improve maintainability and code quality.

**NAIVE GAME LOOP**

The game loop relies on Thread.sleep(frameTime - elapsedTime). If elapsedTime > frameTime,
the sleep time is negative, which makes Thread.sleep throw an exception. It does not "catch up" lost time.

**Implementation**

(src/main/java/ascent/Game.java)

**Consequences**

It leads to non-deterministic gameplay. Since game logic updates
are tied to the rendering speed (frame-rate dependent), the game plays differently
on different hardware. On slow machines, it runs in slow-motion; on fast machines,
physics might behave incorrectly if not capped. It lacks a "Delta Time" mechanism to
decouple simulation time from real time.

**SINGLETON ABUSE**

MonsterPool is implemented as a Singleton.

**Implementation**

(src/main/java/ascent/model/entities/monster/MonsterPool.java)

**Consequences**

It introduces hidden global state, leading to unpredictable tests
that pass/fail randomly based on execution order. If one test modifies the
pool and doesn't clean it, subsequent tests fail unexpectedly. It also
creates tight coupling between the MonsterController and the specific MonsterPool
implementation, preventing dependency injection and mocking.

### TESTING

![Screenshot of test succeeding](pitest-coverage.png)

### SELF-EVALUATION


- Afonso Moura: 33.(3)%
- Daniel Lobo: 33.(3)%
- Nuno Coimbra: 33.(3)%
