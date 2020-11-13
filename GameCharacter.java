public abstract class GameCharacter {
   // Stats
   private final int MAX_HEALTH = 1000;
   private int currentHealth;
   private int totalHealth;
   private int attack;
   private int magic;
   private int defense;
   private int magicDefense;

   // Location
   private int x;
   private int y;

   /**
    * Description: Gets the player's current health
    * @return - currentHealth
    */
   public int getCurrentHealth() {
      return currentHealth;
   }

   /**
    * Description: Sets the player's current health
    * @param currentHealth - The new currentHealth
    * @return - true if currentHealth <= totalHealth; false otherwise
    */
   public boolean setCurrentHealth(int currentHealth) {
      if(currentHealth > totalHealth) {
         return false;
      }
      else if(currentHealth < 0) {
         this.currentHealth = 0;
         return true;
      }
      else {
         this.currentHealth = currentHealth;
         return true;
      }
   }

   /**
    * Description: Increases the characters currentHealth stat by the given amount
    * @param boost - The amount currentHealth will be increased
    * @return -
    */
   public boolean boostCurrentHealth(int boost) {
      setCurrentHealth(getCurrentHealth() + boost);
      return true;
   }

   /**
    * Description: A getter for the totalHealth stat
    * @return - totalHealth
    */
   public int getTotalHealth() {
      return totalHealth;
   }

   /**
    * Description: A setter for the totalHealth stat
    * @param totalHealth
    * @return - true if the new totalHealth is > 0 and < MAX_HEALTH; false otherwise
    */
   public boolean setTotalHealth(int totalHealth) {
      if(totalHealth < 0 || totalHealth > MAX_HEALTH) {
         return false;
      }
      else {
         this.totalHealth = totalHealth;
         return true;
      }
   }

   /**
    * Description: Increases the characters totalHealth stat by the given amount
    * @param boost - The amount totalHealth will be increased
    * @return -
    */
   public boolean boostTotalHealth(int boost) {
      setTotalHealth(getTotalHealth() + boost);
      boostCurrentHealth(boost);
      return true;
   }

   /**
    * Description: A getter for the attack stat
    * @return - attack
    */
   public int getAttack() {
      return attack;
   }

   /**
    * Description: A setter for the attack stat
    * @param attack - The new attack stat
    * @return - true if 0 <= attack <= 1000; false otherwise
    */
   public boolean setAttack(int attack) {
      this.attack = attack;
      return true;
   }

   /**
    * Description: Increases the characters attack stat by the given amount
    * @param boost - The amount attack will be increased
    * @return -
    */
   public boolean boostAttack(int boost) {
      setAttack(getAttack() + boost);
      return true;
   }

   /**
    * Description: A getter for the defense stat
    * @return - defense
    */
   public int getDefense() {
      return defense;
   }

   /**
    * Description: A setter for the defense stat
    * @param defense - The new defense
    * @return - true if 0 <= defense <= 1000; false otherwise
    */
   public boolean setDefense(int defense) {
      this.defense = defense;
      return true;
   }

   /**
    * Description: Increases the characters defense stat by the given amount
    * @param boost - The amount defense will be increased
    * @return -
    */
   public boolean boostDefense(int boost) {
      setDefense(getDefense() + boost);
      return true;
   }

   /**
    * Description: A getter for the magic stat
    * @return - magic
    */
   public int getMagic() {
      return magic;
   }

   /**
    * Description: A setter for the magic stat
    * @param magic - The new magic
    * @return - true if 0 <= magic <= 1000; false otherwise
    */
   public boolean setMagic(int magic) {
      this.magic = magic;
      return true;
   }

   /**
    * Description: Increases the characters magic stat by the given amount
    * @param boost - The amount magic will be increased
    * @return -
    */
   public boolean boostMagic(int boost) {
      setMagic(getMagic() + boost);
      return true;
   }

   /**
    * Description: A getter for the magicDefense stat
    * @return - magicDefense
    */
   public int getMagicDefense() {
      return magicDefense;
   }

   /**
    * Description: A setter for the magic defense stat
    * @param magicDefense - The new magicDefense
    * @return - true if 0 <= magicDefense <= 1000; false otherwise
    */
   public boolean setMagicDefense(int magicDefense) {
      this.magicDefense = magicDefense;
      return true;
   }

   /**
    * Description: Increases the characters magicDefense stat by the given amount
    * @param boost - The amount magicDefense will be increased
    * @return -
    */
   public boolean boostMagicDefense(int boost) {
      setMagicDefense(getMagicDefense() + boost);
      return true;
   }

   /**
    * Description: A getter for a Character's x coordinate
    * @return - x
    */
   public int getX(){
      return x;
   }

   /**
    * Description: A setter for a character's x coordinate
    * @param x - The new x coordinate
    * @return true if x is between 0 and 9; false otherwise
    */
   public boolean setX(int x) {
      if(x > 9 || x < 0) {
         return false;
      }
      this.x = x;
      return true;
   }

   /**
    * Description: A getter for a Character's y coordinate
    * @return - y
    */
   public int getY(){
      return y;
   }

   /**
    * Description: A setter for a character's y coordinate
    * @param y - The new y coordinate
    * @return true if y is between 0 and 9; false otherwise
    */
   public boolean setY(int y) {
      if(y > 9 || y < 0) {
         return false;
      }
      this.y = y;
      return true;
   }

   /**
    * Description: A method to move the player to a new (x, y) position
    * @param x - The new x coordinate
    * @param y - The new y coordinate
    */
   public void move(int x, int y) {
      setX(x);
      setY(y);
   }

   public abstract int attack(GameCharacter character);

   public abstract void takeDamage(int damage);
}
