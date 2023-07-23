# PigGameGUI

The implementation provided earlier contains the necessary code to save the game history to a text file. The `saveGameHistory()` method is called when one of the players wins the game or when the "Save Game" button is clicked. It appends the game result, date and time, total points, and player information to the `historyRecords` ArrayList. Then, it writes the contents of the `historyRecords` to a text file named "game_history.txt" in the current directory.

To run the program:

1. Copy the entire code provided above and save it into a Java file named `PigGameGUI.java`.
2. Compile the Java file using the command `javac PigGameGUI.java`.
3. Run the program using the command `java PigGameGUI`.

The GUI interface for the Game of Pig will appear, allowing you to play the game with two human players. When the game ends, you can click the "Save Game" button to save the game history to a text file.

Please make sure that you have write permissions in the current directory to create the text file and save the game history successfully.
