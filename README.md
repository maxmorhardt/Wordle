# Wordle
Recreation of the game Wordle in Java.

### Things to do:
- The code is getting messy in the view. I would like a class for keyboard input to get that ugly switch statement out of the view and I could also add the GUI code there. Also, the model is basically in the view, but there are so many things the view need that the model should have that will make me pour hours of thought into fixing it before the game is even close to complete...

- At some point I need to make a class for a WordleCharacter. These characters would be essentially a char and either green yellow or gray depending on what they are compared to the secret word. This will allow the GUI keyboard to color each character accordingly, but I'm dreading figuring that out so it will come last.

- I have dumped hours into getting CSS files to work in JavaFX with zero success. To counter this a CSSHandler class will be made and I will just make methods of long CSS strings to be called on the buttons because I have spent more time trying to figure that out than I did getting the game to work in console.

- The big refactor. I have already started commenting about magic numbers and paths, but there is plenty more to be fixed. Variable names are getting confusing as the codebase grows, I'm using title constants for things other than titles (i.e. TITLE_FONT_SIZE), etc. At some point in the near future I need to clean this mess. It blows my mind that in javascript this game is one file with a better look and feel, yet JavaFX likes to make an easy project 800 lines of code.
