import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameView {
   // Fields
   private Game game;
   private JFrame window;
   private JPanel gameBoard;
   private Player player;
   private JTextArea playerInfo;
   private static final int GB_SIZE = 10;

   // All level and enemy arrays are at the bottom of this file

   /**
    * Description: Constructor
    * @param game - The current game
    */
   public GameView(Game game) {
      // Set Game
      this.game = game;

      // Player Creation
      playerCreationWindow();
   }

   /**
    * Description: Handles the Player Creation window
    */
   private void playerCreationWindow() {
      game.player = new Player("Merlin", PlayerClass.Wizard);
      // Set Up Main Window
      JFrame playerWindow = new JFrame("Create Your Character");
      playerWindow.setSize(300, 200);
      JPanel pnl = new JPanel();
      JPanel cntnr = new JPanel();
      cntnr.setLayout(new FlowLayout());
      pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));

      // Set Up Components
      TextField nameBox = new TextField();
      JRadioButton warriorRBtn = new JRadioButton("Warrior");
      JRadioButton knightRBtn = new JRadioButton("Knight");
      JRadioButton wizardRBtn = new JRadioButton("Wizard");
      JButton createBtn = new JButton("Create");
      pnl.add(nameBox);
      pnl.add(warriorRBtn);
      pnl.add(knightRBtn);
      pnl.add(wizardRBtn);
      pnl.add(createBtn);

      // Group Radio Buttons
      ButtonGroup bg = new ButtonGroup( );
      bg.add(warriorRBtn);
      bg.add(knightRBtn);
      bg.add(wizardRBtn);

      cntnr.add(pnl);
      playerWindow.add(cntnr);

      playerWindow.setVisible(true);

      createBtn.setFocusable(true);
      createBtn.requestFocus();
      createBtn.addActionListener(actionEvent -> {
         String newName;

         if(nameBox.getText().equals("")) {
            newName = "Player";
         }
         else {
            newName = nameBox.getText();
         }

         if(warriorRBtn.isSelected()) {
            player = new Player(newName, PlayerClass.Warrior);
            game.setPlayer(player);
         }
         else if(knightRBtn.isSelected()) {
            player = new Player(newName, PlayerClass.Knight);
            game.setPlayer(player);
         }
         else if (wizardRBtn.isSelected()) {
            player = new Player(newName, PlayerClass.Wizard);
            game.setPlayer(player);
         }
         else {
            player = new Player("Merlin", PlayerClass.Wizard);
            game.setPlayer(player);
         }

         createMainWindow();
         playerWindow.setVisible(false);
      });
   }

   /**
    * Description: Handles the Main window
    */
   private void createMainWindow() {
      window = new JFrame("Dungeon Crawler");
      window.setLayout(new BorderLayout());
      window.setSize(1000,700);
      window.setResizable(false);
      window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      // Set Focus on window
      window.setFocusable(true);
      window.requestFocus();

      // Key Listener
      KeyListener listener = new KeyListener() {
         @Override
         public void keyTyped(KeyEvent keyEvent) {

         }

         @Override
         public void keyPressed(KeyEvent keyEvent) {
            switch(game.getLevel()) {
               case 1:
                  moveEnemies(lv1Enemies);
                  break;
               case 2:
                  moveEnemies(lv2Enemies);
                  break;
               case 3:
                  moveEnemies(lv3Enemies);
                  break;
               case 4:
                  moveEnemies(lv4Enemies);
                  break;
               case 5:
                  moveEnemies(lv5Enemies);
                  break;
               case 6:
                  moveEnemies(lv6Enemies);
                  break;
            }
            switch(keyEvent.getKeyCode()) {
               case KeyEvent.VK_LEFT:
                  movePlayer(game.player.getX() - 1, game.player.getY());
                  window.repaint();
                  break;
               case KeyEvent.VK_RIGHT:
                  movePlayer(game.player.getX() + 1, game.player.getY());
                  window.repaint();
                  break;
               case KeyEvent.VK_UP:
                  movePlayer(game.player.getX(), game.player.getY() - 1);
                  window.repaint();
                  break;
               case KeyEvent.VK_DOWN:
                  movePlayer(game.player.getX(), game.player.getY() + 1);
                  window.repaint();
                  break;
               default:
                  //throw new IllegalStateException("Unexpected value: " + keyEvent.getKeyCode());
            }
         }

         @Override
         public void keyReleased(KeyEvent keyEvent) {

         }
      };

      window.addKeyListener(listener);

      // Game Board
      gameBoard = new JPanel(new GridLayout(GB_SIZE, GB_SIZE));
      JPanel gameBoardFL = new JPanel(new FlowLayout());
      gameBoardFL.setBackground(new Color(30, 32, 33));
      gameBoard.setPreferredSize(new Dimension(500,500));
      gameBoardFL.add(gameBoard);
      initGameBoard();
      loadLevel(1);

      // Player Icon
      addIconToBoard(player.getIcon(), 2, 9);

      // Title Area
      JPanel titleArea = new JPanel();
      JPanel titleAreaFL = new JPanel(new FlowLayout());
      titleAreaFL.setBackground(new Color(30, 32, 33));
      JLabel gameTitle = new JLabel("Dungeon Crawler");
      Font titleFont = new Font ("Centaur", Font.BOLD | Font.ITALIC, 60);
      gameTitle.setFont(titleFont);
      gameTitle.setForeground(Color.WHITE);
      titleArea.add(gameTitle);
      titleAreaFL.add(titleArea);
      titleArea.setPreferredSize(new Dimension(1000, 100));
      titleArea.setBackground(new Color(27, 45, 102));

      // Player Info Area
      JPanel playerInfoArea = new JPanel(new FlowLayout());
      player = game.getPlayer();
      playerInfo = new JTextArea("\n    Score: " + game.getScore() + "\n" + player.toString());
      playerInfo.setBackground(Color.DARK_GRAY);
      playerInfo.setForeground(Color.BLACK);
      playerInfo.setFont(new Font("Centaur", Font.BOLD, 20));
      playerInfo.setEditable(false);
      playerInfo.setPreferredSize(new Dimension(250, 500));
      playerInfoArea.add(playerInfo);
      playerInfoArea.setBackground(new Color(30, 32, 33));

      // Add components to the window
      window.add(gameBoardFL, BorderLayout.CENTER);
      window.add(titleAreaFL, BorderLayout.NORTH);
      window.add(playerInfoArea, BorderLayout.EAST);
      window.setVisible(true);
   }

   /**
    * Description: Pops up a window that asks the user if they want to play again
    */
   private void playAgainWindow() {
      JFrame playAgainWin = new JFrame("Play Again?");
      playAgainWin.setSize(300, 200);
      JPanel pnl = new JPanel();
      JLabel message = new JLabel("Play Again?");
      JButton yesBtn = new JButton("Yes");
      yesBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            GameView newGame = new GameView(new Game());
            playAgainWin.setVisible(false);
            window.setVisible(false);
         }
      });
      JButton noBtn = new JButton("No");
      noBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
         }
      });
      pnl.add(message);
      pnl.add(yesBtn);
      pnl.add(noBtn);
      playAgainWin.add(pnl);
      playAgainWin.setVisible(true);
   }

   /**
    * Description: Creates a game board with 100 tiles in a 10 x 10 grid
    */
   private void initGameBoard() {
      for(int i = 0; i < GB_SIZE; i++) {
         for(int j = 0; j < GB_SIZE; j++) {
            tiles[i][j] = new JPanel();
            tiles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gameBoard.add(tiles[i][j]);
         }
      }
   }

   /**
    * Description: Loads the specified level design and enemies onto the game board
    * @param levelNum - The number of the level to be loaded
    */
   private void loadLevel(int levelNum) {
      if(levelNum == 1) {
         loadLevelHelper(level1);
         loadEnemies(1);
      }
      else if(levelNum == 2) {
         loadLevelHelper(level2);
         loadEnemies(2);
      }
      else if(levelNum == 3) {
         loadLevelHelper(level3);
         loadEnemies(3);
      }
      else if(levelNum == 4) {
         loadLevelHelper(level4);
         loadEnemies(4);
      }
      else if(levelNum == 5) {
         loadLevelHelper(level5);
         loadEnemies(5);
      }
      else if(levelNum == 6) {
         loadLevelHelper(level6);
         loadEnemies(6);
      }
      else {
         System.out.println("Error in loadLevel();");
      }
   }

   /**
    * Description: A helper for the loadLevel() method that sets each tile's background color
    * @param level - The level to be colored
    */
   private void loadLevelHelper(Color[][] level) {
      for(int i = 0; i < GB_SIZE; i++) {
         for(int j = 0; j < GB_SIZE; j++) {
            setTileColor(level[i][j], j, i);
         }
      }
   }

   /**
    * Description: Moves the player to a new position
    * @param x - The new x coordinate
    * @param y - The new y coordinate
    */
   private void movePlayer(int x, int y) {
      if(getTileColor(x, y) != Color.DARK_GRAY) {
         player.move(x, y);
         addIconToBoard(player.getIcon(), x, y);
         updatePlayerInfo();
      }
      if(getTileColor(x, y) == Color.BLACK) {
         if(game.getLevel() == game.NUM_LEVELS) {
            youWin();
         }
         else {
            nextLevel();
         }
      }
      else if(getTileColor(x, y) == Color.RED || getTileColor(x, y) == Color.BLUE) {
         gameOver();
      }
   }

   /**
    * Description: Moves an enemy to a new position
    * @param enemy - The enemy to be relocated
    * @param x - The new x coordinate
    * @param y - The new y coordinate
    */
   private void moveEnemy(Enemy enemy, Enemy[][] enemies, int x, int y) {
      // Check if (x, y) is within the bounds of the game board
      if(x > -1 && x < GB_SIZE && y > -1 && y < GB_SIZE) {
         // If the player is at (x, y), the player takes damage
         if(player.getX() == enemy.getX() && player.getY() == enemy.getY()) {
            player.takeDamage(enemy.attack(player));
            System.out.println("Damage: " + enemy.attack(player));
            updatePlayerInfo();
            if(isGameOver()) {
               gameOver();
            }
         }
         if(getTileColor(x, y) == Color.GRAY && enemies[y][x] == null) {
            enemy.setHasMoved(true);
            enemies[y][x] = enemy;
            enemies[enemy.getY()][enemy.getX()] = null;
            enemy.move(x, y);
            addIconToBoard(enemy.getIcon(), x, y);
            System.out.println("Moving enemy to (" + x + ", " + y + ")");
         }
         else {
            System.out.println("Did not move enemy to (" + x + ", " + y + ")");
         }
      }
   }

   /**
    * Description: Moves all enemies on the board one step in a random direction
    * @param enemies - The array of enemies for a level
    */
   private void moveEnemies(Enemy[][] enemies) {
      System.out.println("Moving enemies");
      for(int i = 0; i < GB_SIZE; i++) {
         for(int j = 0; j < GB_SIZE; j++) {
            if(enemies[i][j] != null) {
               int move = (int) (Math.random() * 4) + 1;
               //System.out.println(move);
               // If the enemy at (j, i) has not moved this turn, move it one space in a random direction
               if(!enemies[i][j].getHasMoved()) {
                  if(move == 1) { // right
                     moveEnemy(enemies[i][j], enemies, enemies[i][j].getX() + 1, enemies[i][j].getY());
                  }
                  else if(move == 2) { // left
                     moveEnemy(enemies[i][j], enemies, enemies[i][j].getX() - 1, enemies[i][j].getY());
                  }
                  else if(move == 3) { // up
                     moveEnemy(enemies[i][j], enemies, enemies[i][j].getX(), enemies[i][j].getY() - 1);
                  }
                  else if(move == 4) { // down
                     moveEnemy(enemies[i][j], enemies, enemies[i][j].getX(), enemies[i][j].getY() + 1);
                  }
               }
            }
         }
      }
      // After all enemies have moved, reset their hasMoved flags to false
      for(int i = 0; i < GB_SIZE; i++) {
         for(int j = 0; j < GB_SIZE; j++) {
            if(enemies[i][j] != null) {
               enemies[i][j].setHasMoved(false);
            }
         }
      }
   }

   /**
    * Description: Sets up for the next level
    */
   private void nextLevel() {
      player.move(2, 9);
      player.boostTotalHealth(5);
      if(player.getPlayerClass() == PlayerClass.Warrior) {
         player.boostAttack(3);
         player.boostDefense(2);
         player.boostMagic(2);
         player.boostMagicDefense(1);
      }
      else if(player.getPlayerClass() == PlayerClass.Knight) {
         player.boostAttack(2);
         player.boostDefense(3);
         player.boostMagic(1);
         player.boostMagicDefense(2);
      }
      else if(player.getPlayerClass() == PlayerClass.Wizard) {
         player.boostAttack(1);
         player.boostDefense(1);
         player.boostMagic(3);
         player.boostMagicDefense(3);
      }
      addIconToBoard(player.getIcon(), 2, 9);
      clearEnemies(game.getLevel());
      game.levelUp();
      loadLevel(game.getLevel());
      double points = ((double) player.getCurrentHealth()/(double)player.getTotalHealth()) * 1000;
      game.scorePoints((int) points);
      updatePlayerInfo();
   }

   /**
    * Description: Checks if the player's health has hit 0
    * @return true if game player's current health = 0; false otherwise
    */
   private boolean isGameOver() {
      return player.getCurrentHealth() == 0;
   }

   /**
    * Description: Clears the tiles, player icon, and enemies from the board and displays the final score
    */
   private void gameOver() {
      playerInfo.setText("\n    Game Over!\n\n    Final Score: " + game.getScore());
      clearGameBoard();
      clearEnemies(game.getLevel());
      removeIconFromBoard(player.getIcon(), player.getX(), player.getY());
      playAgainWindow();
   }

   /**
    * Description: Clears the tiles, player icon, and enemies from the board and displays the final score
    */
   private void youWin() {
      game.setScore(game.getScore() + 1000);
      playerInfo.setText("\n    You Win!\n\n    Final Score: " + game.getScore());
      clearGameBoard();
      removeIconFromBoard(player.getIcon(), player.getX(), player.getY());
      playAgainWindow();
   }

   /**
    * Description: Gets the color of the tile at the specified location
    * @param x - The x coordinate of the tile
    * @param y - The y coordinate of the tile
    * @return - The color of the tile
    */
   private Color getTileColor(int x, int y) {
      if(x > -1 && x < GB_SIZE && y > -1 && y < GB_SIZE) {
         return tiles[y][x].getBackground();
      }
      else {
         return Color.PINK;
      }
   }

   /**
    * Description: Changes the color of the tile at the specified location
    * @param color - The color the tile will be changed to
    * @param x - The tile's x coordinate
    * @param y - The tile's y coordinate
    */
   private void setTileColor(Color color, int x, int y) {
      tiles[y][x].setBackground(color);
   }

   /**
    * Description: Clear's the game board by setting all of its tiles to dark gray
    */
   private void clearGameBoard() {
      for(int i = 0; i < GB_SIZE; i++) {
         for(int j = 0; j < GB_SIZE; j++) {
            setTileColor(Color.DARK_GRAY, j, i);
         }
      }
   }

   /**
    * Description: Adds an icon to the game board at (x, y)
    * @param icon - The icon to be added
    * @param x - The x coordinate
    * @param y - The y coordinate
    */
   private void addIconToBoard(JLabel icon, int x, int y) {
      if(x > -1 && x < GB_SIZE && y > -1 && y < GB_SIZE) {
         tiles[y][x].add(icon);
      }
   }

   /**
    * Description: Removes an icon from the game board at (x, y)
    * @param icon - The icon to be removed
    * @param x - The x coordinate
    * @param y - The y coordinate
    */
   private void removeIconFromBoard(JLabel icon, int x, int y) {
      if(x > -1 && x < GB_SIZE && y > -1 && y < GB_SIZE) {
         tiles[y][x].remove(icon);
      }
   }

   /**
    * Description: Updates the information displayed to the player on the right of the screen
    */
   private void updatePlayerInfo() {
      playerInfo.setText("\n    Score: " + game.getScore() + "\n" + player.toString());
   }

   /**
    * Description: Adds an enemy on the game board at (x, y)
    * @param enemy - The enemy to be added
    * @param enemies - The array of enemies for the level you want to add it to
    * @param x - The x coordinate
    * @param y - The y coordinate
    */
   private void addEnemyAt(Enemy enemy, Enemy[][] enemies, int x, int y) {
      enemies[y][x] = enemy;
      addIconToBoard(enemy.getIcon(), x, y);
   }

   /**
    * Description: Removes the enemy from the  specified level and location
    * @param enemies - The array of enemies for the level
    * @param x - The x coordinate
    * @param y - The y coordinate
    */
   private void removeEnemyAt(Enemy[][] enemies, int x, int y) {
      if(enemies[y][x] != null && x < GB_SIZE && y < GB_SIZE) {
         removeIconFromBoard(enemies[y][x].getIcon(), x, y);
         enemies[y][x] = null;
         System.out.println("Removing enemy at (" + x + ", " + y + ")");
      }
   }

   /**
    * Description: Loads the enemies for the specified level
    * @param level - The level
    */
   private void loadEnemies(int level) {
      switch(level) {
         case 1:
            Enemy e1_1 = new Enemy(EnemyClass.Goblin, 2, 2);
            addEnemyAt(e1_1, lv1Enemies, 2, 2);
            Enemy e1_2 = new Enemy(EnemyClass.Goblin, 7, 7);
            addEnemyAt(e1_2, lv1Enemies, 7, 7);
            break;
         case 2:
            Enemy e2_1 = new Enemy(EnemyClass.Goblin, 2, 2);
            addEnemyAt(e2_1, lv2Enemies, 2, 2);
            Enemy e2_2 = new Enemy(EnemyClass.Goblin, 7, 7);
            addEnemyAt(e2_2, lv2Enemies, 7, 7);
            break;
         case 3:
            Enemy e3_1 = new Enemy(EnemyClass.Warlock, 1, 1);
            addEnemyAt(e3_1, lv3Enemies, 1, 1);
            Enemy e3_2 = new Enemy(EnemyClass.Warlock, 8, 7);
            addEnemyAt(e3_2, lv3Enemies, 8, 7);
            break;
         case 4:
            Enemy e4_1 = new Enemy(EnemyClass.Rival, 5, 7);
            addEnemyAt(e4_1, lv4Enemies, 5, 7);
            Enemy e4_2 = new Enemy(EnemyClass.Rival, 1, 4);
            addEnemyAt(e4_2, lv4Enemies, 1, 4);
            Enemy e4_3 = new Enemy(EnemyClass.Rival, 5, 1);
            addEnemyAt(e4_3, lv4Enemies, 5, 1);
            break;
         case 5:
            Enemy e5_1 = new Enemy(EnemyClass.Rival, 2, 2);
            addEnemyAt(e5_1, lv5Enemies, 2, 2);
            Enemy e5_2 = new Enemy(EnemyClass.Rival, 2, 7);
            addEnemyAt(e5_2, lv5Enemies, 2, 7);
            Enemy e5_3 = new Enemy(EnemyClass.Rival, 7, 2);
            addEnemyAt(e5_3, lv5Enemies, 7, 2);
            Enemy e5_4 = new Enemy(EnemyClass.Rival, 7, 7);
            addEnemyAt(e5_4, lv5Enemies, 7, 7);
            break;
         case 6:
            break;

      }
   }

   /**
    * Description: Clears all enemies from the game board
    * @param level - The level you want the enemies removed from
    */
   private void clearEnemies(int level) {
      for(int i = 0; i < GB_SIZE; i++) {
         for(int j = 0; j < GB_SIZE; j++) {
            switch(level) {
               case 1:
                  removeEnemyAt(lv1Enemies, j, i);
                  break;
               case 2:
                  removeEnemyAt(lv2Enemies, j, i);
                  break;
               case 3:
                  removeEnemyAt(lv3Enemies, j, i);
                  break;
               case 4:
                  removeEnemyAt(lv4Enemies, j, i);
                  break;
               case 5:
                  removeEnemyAt(lv5Enemies, j, i);
                  break;
               case 6:
                  removeEnemyAt(lv6Enemies, j, i);
                  break;

            }
         }
      }
   }

   // Array that stores the tiles for the game board
   private JPanel[][] tiles = new JPanel[GB_SIZE][GB_SIZE];

   // Arrays to store enemies for each level
   private static Enemy[][] lv1Enemies = new Enemy[GB_SIZE][GB_SIZE];
   private static Enemy[][] lv2Enemies = new Enemy[GB_SIZE][GB_SIZE];
   private static Enemy[][] lv3Enemies = new Enemy[GB_SIZE][GB_SIZE];
   private static Enemy[][] lv4Enemies = new Enemy[GB_SIZE][GB_SIZE];
   private static Enemy[][] lv5Enemies = new Enemy[GB_SIZE][GB_SIZE];
   private static Enemy[][] lv6Enemies = new Enemy[GB_SIZE][GB_SIZE];

   // Arrays to store level designs
   private static Color[][] level1 = {
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLACK, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
   };

   private static Color[][] level2 = {
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLACK, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
   };

   private static Color[][] level3 = {
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLACK, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.RED, Color.RED, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.RED, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
   };

   private static Color[][] level4 = {
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.GRAY, Color.BLUE, Color.BLUE, Color.BLUE, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.BLUE, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.BLUE, Color.GRAY, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.BLUE, Color.BLACK, Color.GRAY, Color.GRAY, Color.BLUE, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.BLUE, Color.GRAY, Color.BLUE, Color.BLUE, Color.BLUE, Color.GRAY, Color.BLUE, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.BLUE, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLUE, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.GRAY, Color.BLUE, Color.BLUE, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
   };

   private static Color[][] level5 = {
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLACK, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLUE, Color.BLUE, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLUE, Color.BLUE, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
   };

   private static Color[][] level6 = {
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.RED, Color.BLACK, Color.RED, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.GRAY, Color.GRAY, Color.RED, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.BLUE, Color.GRAY, Color.RED, Color.GRAY, Color.GRAY, Color.GRAY, Color.RED, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.BLUE, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLUE, Color.GRAY, Color.RED, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.RED, Color.GRAY, Color.RED, Color.GRAY, Color.BLUE, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.RED, Color.GRAY, Color.RED, Color.GRAY, Color.GRAY, Color.BLUE, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.RED, Color.RED, Color.RED, Color.GRAY, Color.BLUE, Color.GRAY, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.BLUE, Color.GRAY, Color.GRAY, Color.DARK_GRAY},
         {Color.DARK_GRAY, Color.DARK_GRAY, Color.GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY},
   };
}
