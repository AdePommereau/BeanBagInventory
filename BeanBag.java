
import java.io.*;
/**
 * Write a description of class BeanBag here.
 *
 * @author (Antoine de Pommereau)
 * @version (1.1)
 */

public class BeanBag implements Serializable{

   // instance variables - replace the example below with your own
   private int Num; //price?
   private String ID;   //id
   private String Manufacturer;  //manufacturer
   private String Name; //name
   private short Year;  //year
   private byte Month;  //month
   private String freeText;
   private int quantity;
   private int price;
   private int reservationNumber;

   public BeanBag (int num, String ID, String Manufacturer, String Name, short Year, byte Month, String freeText){
       super();
       this.ID = ID;
       this.Manufacturer = Manufacturer;
       this.Name = Name;
       this.Year = Year;
       this.Month = Month;
       this.freeText = freeText;
       this.quantity = num;
       this.price = 0;
       this.reservationNumber = 0;
   }

   public BeanBag(BeanBag beanBagObject){
       this.ID = beanBagObject.ID;
       this.quantity = beanBagObject.quantity;
       this.Year = beanBagObject.Year;
       this.Month = beanBagObject.Month;
       this.Manufacturer = beanBagObject.Manufacturer;
       this.Name = beanBagObject.Name;
   }
   public String getId(){
       return ID;
   }
   public void setId(String ID){
       this.ID = ID;
   }

   public String getManufacturer(){
       return Manufacturer;
   }
   public void setManufacturer(){
       this.Manufacturer = Manufacturer;
   }

   public String getName(){
       return Name;
   }
   public void SetName(){
       this.Name = Name;
   }

   public short getYear(){
       return Year;
   }
   public void setYear(){
       this.Year = Year;
   }

   public byte getMonth(){
       return Month;
   }
   public void setMonth(){
       this.Month = Month;
   }

   public String getfreeText(){
      return freeText;
   }
   public void setfreeText(){
      this.freeText = freeText;
   }

   public int getPrice(){
       return price;
   }
   public void setPrice(int Price){
       this.price = Price;
   }

   public int getQuantity(){
       return quantity;
   }
   public void setQuantity(int Quantity){
       this.quantity = Quantity;
   }

   public int getreservationNumber(){
       return reservationNumber;
   }
   public void setreservationNumber(int reservationNumber){
       this.reservationNumber = reservationNumber;
   }
   
   public BeanBag copy(){
       return new BeanBag(this);
   }
}
