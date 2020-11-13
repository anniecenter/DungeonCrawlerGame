import javax.swing.*;
import java.awt.*;

public class Game {
   public final int NUM_LEVELS = 6;
   private int score;
   private int level;
   Player player;

   // Constructor
   public Game() {
      setLevel(1);
      setScore(0);
   }

   /**
    * Description: A getter for the game's score
    * @return - The score
    */
   public int getScore() {
      return score;
   }

   /**
    * Description: A setter for the game's score
    * @param score - The new score
    * @return - True if score is positive; false otherwise
    */
   public boolean setScore(int score) {
      if(score < 0) {
         return false;
      }
      else {
         this.score = score;
         return true;
      }
   }

   /**
    * Description: A method to add points to the score
    * @param points - The amount of points to add
    * @return
    */
   public boolean scorePoints(int points) {
      setScore(getScore() + points);
      return true;
   }

   /**
    * Description: A getter for the game's level
    * @return - The level
    */
   public int getLevel() {
      return level;
   }

   /**
    * Description: A setter for the game's level
    * @param level - The new level
    * @return
    */
   public boolean setLevel(int level) {
      this.level = level;
      return true;
   }

   /**
    * Description: A method that increments the game's level by 1
    * @return
    */
   public boolean levelUp() {
      setLevel(getLevel() + 1);
      return true;
   }

   /**
    * Description: A getter for the Player
    * @return - The Player
    */
   public Player getPlayer() {
      return player;
   }

   /**
    * Description: A setter for the Player
    * @param player - the new Player
    * @return
    */
   public boolean setPlayer(Player player) {
      this.player = player;
      return true;
   }
}
