enum ItemType {Potion, Gold}

public class Item {
   private ItemType itemType;
   private int x;
   private int y;

   // Constructor
   public Item(ItemType itemType) {
      setItemType(itemType);
   }

   public ItemType getItemType() {
      return itemType;
   }

   public boolean setItemType(ItemType itemType) {
      this.itemType = itemType;
      return true;
   }

   public int getX() {
      return x;
   }

   public boolean setX(int x) {
      this.x = x;
      return true;
   }

   public int getY() {
      return y;
   }

   public boolean setY(int y) {
      this.y = y;
      return true;
   }
}
