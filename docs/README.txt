## LDTS_<2><6> - <Ascent>

In this roguelike dungeon-crawler, you, the player, must go through several dangerous levels of the deep-dark caves above Narnia, in which  you will fight through hordes of enemies to ascend to the next level. As you ascend, the danger will continuously escalate, but so will your ability to deal with them.

This project was developed by **Afonso Moura** (**up202403939*@fe.up.pt**), **Daniel Lobo** (**up2023...@fe.up.pt**) and **Nuno Coimbra** (**up202405191@fe.up.pt**) for LDTS 2025/2026.

### IMPLEMENTED FEATURES

- **Ascent.Game loop** - The game continuously reads and interprets the input of the player
- **Character movement** - The player can control their character using the arrow keys or wasd keys. They can also only change their looking direction by holding SHIFT while pressing the movement keys.
- **Collision detection** - The player character cannot phase through other entities.
- **Multiple levels** - The game progresses through 5 hand-made levels that are stored in the levels folder.
- **Stats** - The player and the monsters have different stats, used to calculate fights, movement and awareness.
- **Monster Pathfinding** - Each monster has an awareness stat that determines how far they can detect the player and move towards them.
- **Menus** - The game has separate menus for: Main menu, Game menu, Death screen & Win screen.
- **Interactions** - The player can open & close doors & chests, pick up or equip items from chests & go up levels through stairs.
- **Items** - Each level has chests that contain items, from equipment to health potions, that affect the players stats and are essential for progression.

![Game GIF](docs/jogo_final.gif)


/* TO CHANGE:
A UML diagram showing what the `Ascent.view.draw()` function does:

![UML class diagram](docs/Draw.png)
*/

### DESIGN

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
 
The following figure shows how the patterns were mapped to our classes.
 
![UML class diagram](docs/MonsterPool.png)
 
These classes can be found in the following files:

(src/main/java/Ascent/model/entities/poolsMonsterPool.java)
(src/main/java/Ascent/model/entities/poolsMonster.java)
(src/main/java/Ascent/model/game/level/BaseplateBuilder.java)

**Consequences**
 
Using these patterns, we avoid creating monsters during gameplay, which improves performance. The garbage collector runs less often, making the game smoother.  On the other hand, the code is more complex, because we need to manage the pool, reset methods and active states. We must also remember to reset monsters before using them, and if we need more than 30 monsters at once, the pool will run out.

**IMPLEMENTING DIFFERENT GAME STATES**

Whether the player is in a menu or in game should change the steps the code runs.

**The Pattern**

We applied the **State** pattern, implementing it as a *Stack of States*. Whenever the game is first started, a main menu state will be pushed to the stack, and will stay there until the player chooses the 'Exit' option in the menu at any time. Whenever we start the game or do any other action that implies another state, the code pushes the state into the stack. When there's an action that simbolizes the end of the current state, the code pops the topmost state on the stack.

**Implementation**

The State classes can be found in the following folder:

(src/main/java/Ascent/state)

**Consequences**

Using the specific stack of patterns implementation impedes us from creating a menu that allows viewing the current game map as a way to plan the next step, since the game state gets pushed down the stack and out of the top spot.


# Code Smells Identified in Current Implementation

This section describes the code smells identified in the current implementation of the project, representing areas that would benefit from refactoring to improve maintainability and code quality.

**Switch Statements**

Our game contains several switch statements that could be improved using polymorphism. The most notable example is found in the `GameController` class, in the `processAction()` method, where each movement direction is handled in a separate case with similar logic. Similarly, the `ItemFactory` class contains a switch statement in the `createItem()` method for creating different item types.

**Magic Numbers**

Throughout our code there are some literal values that could be extracted to named constants for better readability. In the `Monster` class, the constructors use values like `(90, 2, 2)` for stats initialization. In the Ascent.Game class, the GUI dimensions and player starting position are defined as `(40, 20)` and `(20, 10)`.

**Long Method**

The `createItem()` method in `ItemFactory` handles the creation of all item types (potions, weapons, and armour) in a single method. The method could be decomposed into smaller helper methods like `createPotion()`, `createWeapon()`, and `createArmour()` to improve readability and make each section easier to test independently.

**Naming Clarity Issues**

Some method and variable names could be more descriptive to better communicate their purpose. The `conditionals()` method in `GameController` does not clearly indicate what conditions it checks as it's a placeholder for a future, more developed version.

### TESTING

![Screenshot of test succeeding](docs/pitest-coverage.png)

### SELF-EVALUATION


- Afonso Moura: 33.(3)%
- Daniel Lobo: 33.(3)%
- Nuno Coimbra: 33.(3)%
