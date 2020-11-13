import javax.swing.*;
import java.awt.*;

enum PlayerClass { Warrior, Knight, Wizard }

public class Player extends GameCharacter {
   // General Info
   private String name;
   private PlayerClass playerClass;
   private JLabel icon;

   // Constructors
   public Player(PlayerClass playerClass) {
      setName("Player");
      setPlayerClass(playerClass);

      ImageIcon img;

      switch(playerClass) {
         case Warrior:
            setAttack(16);
            setDefense(12);
            setMagic(12);
            setMagicDefense(8);
            img = new ImageIcon("images/warrior_icon.png");
            break;
         case Knight:
            setAttack(12);
            setDefense(16);
            setMagic(8);
            setMagicDefense(12);
            img = new ImageIcon("images/knight_icon.png");
            break;
         case Wizard:
            setAttack(8);
            setDefense(8);
            setMagic(16);
            setMagicDefense(16);
            img = new ImageIcon("images/wizard_icon.png");
            break;
         default:
            img = new ImageIcon("images/goblin_icon.png");
      }

      Image image = img.getImage(); // transform it
      Image newimg = image.getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
      img = new ImageIcon(newimg);  // transform it back
      icon = new JLabel(img);
   }

   public Player(String name, PlayerClass playerClass) {
      setName(name);
      setPlayerClass(playerClass);
      setTotalHealth(100);
      setCurrentHealth(100);
      setX(2);
      setY(9);

      ImageIcon img;

      switch(playerClass) {
         case Warrior:
            setAttack(16);
            setDefense(12);
            setMagic(12);
            setMagicDefense(8);
            img = new ImageIcon("images/warrior_icon.png");
            break;
         case Knight:
            setAttack(12);
            setDefense(16);
            setMagic(8);
            setMagicDefense(12);
            img = new ImageIcon("images/knight_icon.png");
            break;
         case Wizard:
            setAttack(8);
            setDefense(8);
            setMagic(16);
            setMagicDefense(16);
            img = new ImageIcon("images/wizard_icon.png");
            break;
         default:
            img = new ImageIcon("images/goblin_icon.png");
      }

      Image image = img.getImage(); // transform it
      Image newimg = image.getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
      img = new ImageIcon(newimg);  // transform it back
      icon = new JLabel(img);
   }

   /**
    * Description: A getter for the player's name field
    * @return - the player's name
    */
   public String getName() {
      return name;
   }

   /**
    * Description: A setter for the player's name field
    * @param name - the new name
    * @return - true if the new name is less than 20 characters; false otherwise
    */
   public boolean setName(String name) {
      if(name.length() <= 0 || name.length() > 20) {
         return false;
      }
      else {
         this.name = name;
         return true;
      }
   }

   /**
    * Description: A getter for the player's class
    * @return - Warrior, Knight, or Wizard
    */
   public PlayerClass getPlayerClass() {
      return playerClass;
   }

   /**
    * Description: A setter for the player's class
    * @param playerClass - The new player class
    * @return -
    */
   public boolean setPlayerClass(PlayerClass playerClass) {
      this.playerClass = playerClass;
      return true;
   }

   /**
    * Description: A method to retrieve the player's icon
    * @return - The player's icon
    */
   public JLabel getIcon() {
      return icon;
   }

   @Override
   public int attack(GameCharacter character) {
      // Could be implemented later
      return 0;
   }

   @Override
   public void takeDamage(int damage) {
      setCurrentHealth(getCurrentHealth() - damage);
   }

   @Override
   public String toString() {
      return "\n    Player - " + name + "\n\n" +
            "    Health:         " + getCurrentHealth() + "/" + getTotalHealth() + "\n" +
            "    Attack:\t         " + getAttack() + "\n" +
            "    Defense:\t         " + getDefense() + "\n" +
            "    Magic:\t         " + getMagic() + "\n" +
            "    Magic Defense:    " + getMagicDefense();
   }
}
