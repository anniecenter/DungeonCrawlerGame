import javax.swing.*;
import java.awt.*;

enum EnemyClass {Warlock, Goblin, Rival}

public class Enemy extends GameCharacter {
   // General Info
   private EnemyClass enemyClass;
   private JLabel icon;
   private boolean hasMoved;

   // Constructors
   public Enemy(EnemyClass enemyClass) {
      setEnemyClass(enemyClass);

      ImageIcon img;

      switch(enemyClass) {
         case Goblin:
            img = new ImageIcon("images/goblin_icon.png");
            break;
         case Rival:
            img = new ImageIcon("images/rival_icon.png");
            break;
         case Warlock:
            img = new ImageIcon("images/warlock_icon.png");
            break;
         default:
            img = new ImageIcon("images/wizard_icon.png");
      }

      Image image = img.getImage(); // transform it
      Image newimg = image.getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
      img = new ImageIcon(newimg);  // transform it back
      icon = new JLabel(img);
   }

   public Enemy(EnemyClass enemyClass, int x, int y) {
      setEnemyClass(enemyClass);

      ImageIcon img;

      switch(enemyClass) {
         case Goblin:
            setTotalHealth(50);
            setCurrentHealth(50);
            setAttack(8);
            setDefense(7);
            setMagic(5);
            setMagicDefense(6);
            setX(x);
            setY(y);
            img = new ImageIcon("images/goblin_icon.png");
            break;
         case Rival:
            setTotalHealth(100);
            setCurrentHealth(100);
            setAttack(16);
            setDefense(12);
            setMagic(8);
            setMagicDefense(12);
            setX(x);
            setY(y);
            img = new ImageIcon("images/rival_icon.png");
            break;
         case Warlock:
            setTotalHealth(75);
            setCurrentHealth(75);
            setAttack(7);
            setDefense(7);
            setMagic(20);
            setMagicDefense(20);
            setX(x);
            setY(y);
            img = new ImageIcon("images/warlock_icon.png");
            break;
         default:
            img = new ImageIcon("images/wizard_icon.png");
      }

      Image image = img.getImage(); // transform it
      Image newimg = image.getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
      img = new ImageIcon(newimg);  // transform it back
      icon = new JLabel(img);
   }

   /**
    * Description: A getter for the enemy's class
    * @return - Goblin, Wizard, or Warlock
    */
   public EnemyClass getEnemyClass() {
      return enemyClass;
   }

   /**
    * Description: A setter for the enemy's class
    * @param enemyClass - The new class
    * @return
    */
   public boolean setEnemyClass(EnemyClass enemyClass) {
      this.enemyClass = enemyClass;
      return true;
   }

   /**
    * Description: A method to retrieve an enemy's icon
    * @return - The enemy's icon
    */
   public JLabel getIcon() {
      return icon;
   }

   /**
    * Description: A getter for the hasMoved flag
    * @return - true or false depending on if the enemy has already moved that turn
    */
   public boolean getHasMoved() {
      return hasMoved;
   }

   /**
    * Description: A setter for the hasMoved flag
    * @param hasMoved - true or false depending on if the enemy has already moved that turn
    */
   public void setHasMoved(boolean hasMoved) {
      this.hasMoved = hasMoved;
   }

   @Override
   public String toString() {
      return "Enemy - " + enemyClass + "\n\n" +
            "\tHealth:\t\t" + getCurrentHealth() + "/" + getTotalHealth() + "\n" +
            "\tAttack:\t\t" + getAttack() + "\n" +
            "\tDefense:\t\t" + getDefense() + "\n" +
            "\tMagic:\t\t" + getMagic() + "\n" +
            "\tMagic Defense:\t" + getMagicDefense();
   }

   @Override
   public int attack(GameCharacter character) {
      int damage = 0;
      if(this.getAttack() >= this.getMagic()) {
         damage = this.getAttack() * 15 / character.getDefense();
      }
      else {
         damage = this.getMagic() * 15 / character.getMagicDefense();
      }
      return damage;
   }

   @Override
   public void takeDamage(int damage) {
      // Could be implemented later
   }
}
