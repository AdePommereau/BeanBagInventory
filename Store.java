import java.io.*;
import java.util.*;


/**
* BadStore is a minimally compiling, but non-functioning implementor of the
* BeanBagStore interface.
*
* @author Jonathan Fieldsend
* @version 1.1
*/
public class Store implements BeanBagStore
{


   static ObjectArrayList currentStock = new ObjectArrayList();
   static ObjectArrayList reservedStock = new ObjectArrayList();
   static ObjectArrayList soldStock = new ObjectArrayList();

   private static int reservationNumber = 0;

   private boolean validID(String id){
        //checks if the id is valid and verifies all the conditions: 8 characters long hexadecimal number
        //if it is, return true, else return false
        if (id.matches("[0-9]+") && id.length() == 8) {
           return true;
        }
        else {
             return false;
        }
   }
   
   private boolean NoReservationNumber(int reservationNumber){
       //handle error if the reservation number given does not match any reservation number in the system
       for (int i = 0; i < reservedStock.size(); i++){
          BeanBag item = (BeanBag) reservedStock.get(i);
          if (reservationNumber == item.getreservationNumber()){
              return true;
          }
       }
       return false;
   }

  /**
  * Method adds bean bags to the store with the arguments as bean bag details .
  * <p>
  * The state of this BeanBagStore must be be unchanged if any exceptions are
  * thrown .
  *
  * @param num number of bean bags added
  * @param manufacturer bean bag manufacturer
  * @param name bean bag name
  * @param id ID of bean bag
  * @param year year of manufacture
  * @param month month of manufacture
  * @throws IllegalNumberOfBeanBagsAddedException if the number to be added
  * is less than 1
  * @throws BeanBagMismatchException if the id already exists (as a current in
  * stock bean bag , or one that has been previously
  * stocked in the store , but the other stored
  * elements ( manufacturer , name and free text ) do
  * not match the pre - existing version
  * @throws IllegalIDException if the ID is not a positive eight character
  * hexadecimal number
  * @throws InvalidMonthException if the month is not in the range 1 to 12
  */
   @Override
   public void addBeanBags(int num, String manufacturer, String name,
   String id, short year, byte month)
   throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
   IllegalIDException, InvalidMonthException {

     //checks if the number to be added is less than one
     if (num<1){
       throw new IllegalNumberOfBeanBagsAddedException("Error: Cannot add a negative number of beanbags");
     }
     //checks if the inputted month is valid
     if (month<1 && month>12){
       throw new InvalidMonthException("Error: Inputted month not valid");
     }
     //checks if the id is valid
     if(!validID(id)){
       throw new IllegalIDException("Error: ID does not match the required conditions");
     }

     //check if the object already exists in the store by looping through the currentStock
     boolean inStock = false;
     for (int i = 0; i < currentStock.size(); i++){
       BeanBag item = (BeanBag) currentStock.get(i);
       if (id == item.getId()){
         if (name == item.getName() && manufacturer == item.getManufacturer() && item.getfreeText() == ""){
           // set quantity to plus one
           item.setQuantity((int) item.getQuantity() + num);
           inStock = true;
         }
         //throw error because not the same name or manufacturer or freeText
         else{
             throw new BeanBagMismatchException("Error: Does not match the ID");
         }
       }
     }

     //if doesn't exist, add it to the currentStock
     if(!inStock){
       String information = "";
       currentStock.add(new BeanBag(num, id, manufacturer, name, year, month, information));
     }
   }

   /**
    * Method adds bean bags to the store with the arguments as bean bag details.
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param num               number of bean bags added
    * @param manufacturer      bean bag manufacturer
    * @param name              bean bag name
    * @param id                ID of bean bag
    * @param year              year of manufacture
    * @param month             month of manufacture
    * @param information       free text detailing bean bag information
    * @throws IllegalNumberOfBeanBagsAddedException   if the number to be added
    *                           is less than 1
    * @throws BeanBagMismatchException if the id already exists (as a current in
    *                           stock bean bag, or one that has been previously
    *                           stocked in the store, but the other stored
    *                           elements (manufacturer, name and free text) do
    *                           not match the pre-existing version
    * @throws IllegalIDException   if the ID is not a positive eight character
    *                           hexadecimal number
    * @throws InvalidMonthException    if the month is not in the range 1 to 12
    */
   @Override
   public void addBeanBags(int num, String manufacturer, String name,
   String id, short year, byte month, String information)
   throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
   IllegalIDException, InvalidMonthException {

     //checks if the number to be added is less than one
     if (num<1){
       throw new IllegalNumberOfBeanBagsAddedException("Error: Cannot add a negative number of beanbags");
     }
     //checks if the inputted month is valid
     if (month<1 && month>12){
       throw new InvalidMonthException("Error: Inputted month not valid");
     }
     //checks if the id is valid
     if(!validID(id)){
       throw new IllegalIDException("Error: ID does not match the required conditions");
     }

     //check if the object already exists in the store by looping through the currentStock
     boolean inStock = false;
     for (int i = 0; i < currentStock.size(); i++){
       BeanBag item = (BeanBag) currentStock.get(i);
       if (id == item.getId()){
         if (name == item.getName() && manufacturer == item.getManufacturer() && item.getfreeText() == information){
           item.setQuantity((int) item.getQuantity() + num);
           inStock = true;
         }
         //throw error because not the same name or manufacturer or freeText
         else{
             throw new BeanBagMismatchException("Error: Does not match the ID");
         }
       }
      }

     //if doesn't exist, add it to the currentStock
     if(!inStock){
         currentStock.add(new BeanBag(num,id, manufacturer, name, year, month, information));
     }
   }
   /**
    * Method to set the price of bean bags with matching ID in stock.
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param id                ID of bean bags
    * @param priceInPence      bean bag price in pence
    * @throws InvalidPriceException if the priceInPence < 1
    * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
    *                          match any bag in (or previously in) stock
    * @throws IllegalIDException if the ID is not a positive eight character
    *                           hexadecimal number
    */
   @Override
   public void setBeanBagPrice(String id, int priceInPence)
   throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException {
     //check if id is valid
     if(!validID(id)){
       throw new IllegalIDException("Error: ID does not match required conditions");
     }
     //check if price to set is higher than 0
     if(priceInPence<0){
       throw new InvalidPriceException("Error: Price cannot be negative");
     }

     //loop through the currentStock to find the item with the same id and set it a price
     boolean found = false;
     for (int i = 0; i < currentStock.size(); i++){
       BeanBag item = (BeanBag) currentStock.get(i);
       if (id == item.getId()){
         item.setPrice(priceInPence);
         found = true;
       }
     }
     if (found == false){
         //if id does not matches any ids in store, throw exception
         throw new BeanBagIDNotRecognisedException("Error: The ID of the beanbag is not recognized!");
     }
   }

   /**
    * Method sells bean bags with the corresponding ID from the store and removes
    * the sold bean bags from the stock.
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param num           number of bean bags to be sold
    * @param id            ID of bean bags to be sold
    * @throws BeanBagNotInStockException   if the bean bag has previously been in
    *                      stock, but is now out of stock
    * @throws InsufficientStockException   if the bean bag is in stock, but not
    *                      enough are available (i.e. in stock and not reserved)
    *                      to meet sale demand
    * @throws IllegalNumberOfBeanBagsSoldException if an attempt is being made to
    *                      sell fewer than 1 bean bag
    * @throws PriceNotSetException if the bag is in stock, and there is sufficient
    *                      stock to meet demand, but the price has yet to be set
    * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
    *                          match any bag in (or previously in) stock
    * @throws IllegalIDException   if the ID is not a positive eight character
    *                           hexadecimal number
    */
   @Override
   public void sellBeanBags(int num, String id) throws BeanBagNotInStockException,
   InsufficientStockException, IllegalNumberOfBeanBagsSoldException,
   PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {
        //check is number of bean bags to sell is bigger than one
        if(num<1){
            throw new IllegalNumberOfBeanBagsSoldException("Error: You cannot sell less than one item.");
        }
        //check if id is valid
        if(!validID(id)){
            throw new IllegalIDException("Error: ID does not match required conditions");
        }

       /* check if item with the same id is in stock and with the same quantity. If it is remove it from the stock and add it to the sold arraylist.
          if the quantity to sell is smaller thant the item quantity, set the quantity of the item to be the difference of these two numbers.*/
       boolean inStock = false;
       for (int i = 0; i < currentStock.size(); i++){
            BeanBag item = (BeanBag) currentStock.get(i);
            if (id == item.getId()){
                inStock = true;
                //checks if a price is set for the item with the matching id
                if(item.getPrice() == 0){
                    throw new PriceNotSetException("Error: No price set for this beanbag");
                }
                //checks if item is in stock
                else if (item.getQuantity() < num){
                    throw new InsufficientStockException("Error: Stock is low");
                }
                else if (item.getQuantity() == 0){
                    throw new BeanBagNotInStockException("Error: the beanbag is not in stock");
                }
                else if (num <= item.getQuantity()){
                    int quantity = item.getQuantity() - num;
                    item.setQuantity(quantity);
                    BeanBag copyItem = item.copy();
                    copyItem.setQuantity(num);
                    soldStock.add(copyItem);                                      
                }                
            }
       }
       //checks if id matches any ids in store
       if(!inStock){
           throw new BeanBagIDNotRecognisedException("Error: The ID of the beanbag is not recognized!");
       }
   }

   /**
    * Method reserves bean bags with the corresponding ID in the store and returns
    * the reservation number needed to later access the reservation
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param num           number of bean bags to be reserved
    * @param id            ID of bean bags to be reserved
    * @return              unique reservation number, i.e. one not currently live
    *                      in the system
    * @throws BeanBagNotInStockException   if the bean bag has previously been in
    *                      stock, but is now out of stock
    * @throws InsufficientStockException   if the bean bag is in stock, but not
    *                      enough are available to meet the reservation demand
    * @throws IllegalNumberOfBeanBagsReservedException if the number of bean bags
    *                      requested to reserve is fewer than 1
    * @throws PriceNotSetException if the bag is in stock, and there is sufficient
    *                      stock to meet demand, but the price has yet to be set
    * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
    *                          match any bag in (or previously in) stock
    * @throws IllegalIDException   if the ID is not a positive eight character
    *                           hexadecimal number
    */
   public int reserveBeanBags(int num, String id) throws BeanBagNotInStockException,
   InsufficientStockException, IllegalNumberOfBeanBagsReservedException,
   PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {
         //check is number of bean bags to sell is bigger than one
        if(num<1){
            throw new IllegalNumberOfBeanBagsReservedException("Error: You cannot sell less than one item.");
        }
        //check if id is valid
        if(!validID(id)){
            throw new IllegalIDException("Error: ID does not match required conditions");
        }

        //check if object exists
       boolean inStock = false;
       /*iterate through the currentStock to find the item. If the item is found, set a reservation number to that number.
          If the quantities are the same, remove the item from current stock and add it to reserved stock.
          If the quantities are different, modifies the quantity of the item in current stock and add the item in reservedStock in num quantities. */
       for (int i = 0; i < currentStock.size(); i++){
           BeanBag item = (BeanBag) currentStock.get(i);
           if (id == item.getId()){
                inStock = true;
                reservationNumber++;
                //checks if a price is set for the item with the matching id
                if(item.getPrice() == 0){
                    throw new PriceNotSetException("Error: No price set for this beanbag");
                }
                //checks if item is in stock
                else if (item.getQuantity() < num){
                    throw new InsufficientStockException("Error: Stock is low");
                }
                else if (item.getQuantity() == 0){
                    throw new BeanBagNotInStockException("Error: the beanbag is not in stock");
                }
                else if (num <= item.getQuantity()){
                    int quantity = item.getQuantity() - num;
                    item.setQuantity(quantity);
                    BeanBag copyItem = item.copy();
                    copyItem.setQuantity(num);
                    reservedStock.add(copyItem);                                      
                }                
            }
        }

       //check if item is in stock
       if(!inStock){
           throw new BeanBagNotInStockException("Error: The beanbag you are looking for is not in stock!");
       }

     return reservationNumber;
   }

   /**
    * Method removes an existing reservation from the system due to a reservation
    * cancellation (rather than sale). The stock should therefore remain unchanged.
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param reservationNumber           reservation number
    * @throws ReservationNumberNotRecognisedException  if the reservation number
    *                          does not match a current reservation in the system
    */
   public void unreserveBeanBags(int reservationNumber)
   throws ReservationNumberNotRecognisedException {

       //check if item has reservation number
       if (!NoReservationNumber(reservationNumber)){
           throw new ReservationNumberNotRecognisedException("Error: Reservation number nor recognized!");
       }
       
       int Quantity = 0;
       String beanBagID = null;
       //iterate through the reserved stock to find the item with the matching reservation number. Add it to currenStock and remove it from the reservedStock.
       for (int i = 0; i < reservedStock.size(); i++){
         BeanBag item = (BeanBag) reservedStock.get(i);
         if (reservationNumber == item.getreservationNumber()){
           beanBagID = item.getId();
           Quantity = item.getQuantity();
           reservedStock.remove(i);
         }
       }
       //set the quantity of the reserved item in the currenstock equivalent item
       for (int i = 0; i < currentStock.size(); i++){
         BeanBag item = (BeanBag) currentStock.get(i);
         if (beanBagID == item.getId()){
           int newQuantity = item.getQuantity() + Quantity;
           item.setQuantity(newQuantity);
         }
       }
   }

   /**
    * Method sells beanbags with the corresponding reservation number from
    * the store and removes these sold beanbags from the stock.
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param reservationNumber           unique reservation number used to find
    *                                    beanbag(s) to be sold
    * @throws ReservationNumberNotRecognisedException  if the reservation number
    *                          does not match a current reservation in the system
    */
   public void sellBeanBags(int reservationNumber)
   throws ReservationNumberNotRecognisedException {

       //check if the item has reservation number
       if (!NoReservationNumber(reservationNumber)){
           throw new ReservationNumberNotRecognisedException("Error: Reservation number nor recognized!");
       }

       //iterate through the reserved stock to find the item with the matching reservation number. Add it to soldStock and remove it from the reservedStock.
       //check if their is a difference between the price of the reserved item and the one in stock. If they is, set the new price
       for (int i = 0; i < reservedStock.size(); i++){
         BeanBag item = (BeanBag) reservedStock.get(i);
         if (reservationNumber == item.getreservationNumber()){
           String id = item.getId();
           for (int a = 0; a < currentStock.size(); a++){
               BeanBag Currentitem = (BeanBag) currentStock.get(i);
               if (Currentitem.getId() == id){
                   if(item.getPrice() > Currentitem.getPrice()){
                       item.setPrice(Currentitem.getPrice());

                   }
               }
           }
           soldStock.add(item);
           reservedStock.remove(item);
         }
       }
   }

   /**
    * Access method for the number of BeanBags stocked by this BeanBagStore
    * (total of reserved and unreserved stock).
    *
    * @return                  number of bean bags in this store
    */
   public int beanBagsInStock() {

     //sum the sizes of the reserved stock and current stock and return the value
     int total_stock = 0;
     for (int i = 0; i < currentStock.size(); i++){
        BeanBag item = (BeanBag) currentStock.get(i);
        total_stock += item.getQuantity();
     }
     for (int i = 0; i < reservedStock.size(); i++){
        BeanBag item = (BeanBag) reservedStock.get(i);
        total_stock += item.getQuantity();
     }
     return total_stock;
   }

   /**
    * Access method for the number of reserved bean bags stocked by this
    * BeanBagStore.
    *
    * @return                  number of reserved bean bags in this store
    */
   public int reservedBeanBagsInStock() {

     //returns the size of the reservedStock array.
     int reserved_stock = 0;
     for (int i = 0; i < reservedStock.size(); i++){
        BeanBag item = (BeanBag) soldStock.get(i);
        reserved_stock += item.getQuantity();
     }
     return reserved_stock;
   }

   /**
    * Method returns number of bean bags with matching ID in stock (total
    * researved and unreserved).
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param id            ID of bean bags
    * @return              number of bean bags matching ID in stock
    * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
    *                          match any bag in (or previously in) stock
    * @throws IllegalIDException   if the ID is not a positive eight character
    *                           hexadecimal number
    */
   public int beanBagsInStock(String id) throws BeanBagIDNotRecognisedException,
   IllegalIDException {

    //check if id is valid
    if(!validID(id)){
        throw new IllegalIDException("Error: ID does not match required conditions");
    }

    //int to keep track of items with the same id
    int same_id = 0;
    boolean found = false;
    //iterate through the currentStock array to find items with matcing id. If found, increment same_id by 1.
    for (int i = 0; i < currentStock.size(); i++){
         BeanBag item = (BeanBag) currentStock.get(i);
         if (id == item.getId()){
             found = false;
             same_id += item.getQuantity();
         }
    }
    //iterate through the reservedStock array to find items with matcing id. If found, increment same_id by 1.
    for (int i = 0; i < reservedStock.size(); i++){
         BeanBag item = (BeanBag) reservedStock.get(i);
         if (id == item.getId()){
             found = false;
             same_id += item.getQuantity();
         }
    }
    
    //checks if id matches any ids in store
    if (found == false){
        throw new BeanBagIDNotRecognisedException("Error: The ID of the beanbag is not recognized!");
    }
    
    //return the number of items in stock with matching id
    return same_id;
   }

   /**
    * Method saves this BeanBagStore's contents into a serialised file,
    * with the filename given in the argument.
    *
    * @param filename      location of the file to be saved
    * @throws IOException  if there is a problem experienced when trying to save
    *                      the store contents to the file
    */
   public void saveStoreContents(String filename) throws IOException {

       try{
           FileOutputStream file = new FileOutputStream(filename);
           ObjectOutputStream out = new ObjectOutputStream(file);

           //write the three arrays to the file
           out.writeObject(currentStock);
           out.writeObject(reservedStock);
           out.writeObject(soldStock);
           //close the two streams
           out.close();
           file.close();
       }
       catch (IOException i){
           i.printStackTrace();
       }
   }

   /**
    * Method should load and replace this BeanBagStore's contents with the
    * serialised contents stored in the file given in the argument.
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param filename      location of the file to be loaded
    * @throws IOException  if there is a problem experienced when trying to load
    *                      the store contents from the file
    * @throws ClassNotFoundException   if required class files cannot be found when
    *                      loading
    */
   public void loadStoreContents(String filename) throws IOException,
   ClassNotFoundException {

       try{
           FileInputStream file = new FileInputStream(filename);
           ObjectInputStream in = new ObjectInputStream(file);
           //retrieve the three objects from the file
           currentStock = (ObjectArrayList) in.readObject();
           reservedStock = (ObjectArrayList) in.readObject();
           soldStock = (ObjectArrayList) in.readObject();

           in.close();
           file.close();
       }
       catch (IOException i){
           i.printStackTrace();
       }
       catch (ClassNotFoundException i){
           i.printStackTrace();
       }
   }

   /**
    * Access method for the number of different bean bags currently stocked by this
    * BeanBagStore.
    *
    * @return                  number of different specific bean bags currently in
    *                          this store (i.e. how many different IDs represented
    *                          by bean bags currently in stock, including reserved)
    */
   public int getNumberOfDifferentBeanBagsInStock() {

    //Create two new Hashsets (cannot contain duplicates) to find the unique values
    ObjectArrayList uniqueIDs = new ObjectArrayList();

    //iterate through the currenStock and for every item, add it to testiDs hashset.
    for (int i = 0; i < currentStock.size(); i++){
         BeanBag item = (BeanBag) currentStock.get(i);
         boolean idMatching = false;
         String id = item.getId();
         
         for (int a = 0; a < uniqueIDs.size(); a++){
             if(id == uniqueIDs.get(a)){
                 idMatching = true;
             }
             if (idMatching == false){
                 uniqueIDs.add(id);
             } 
         }
    }
    //return the size of the list containing all the unique ids.
    return uniqueIDs.size();
   }

   /**
    * Method to return number of bean bags sold by this BeanBagStore.
    *
    * @return                  number of bean bags sold by the store
    */
   public int getNumberOfSoldBeanBags() {

       //return the size of the array containing all the sold items.
       int sold_bags = 0;
       for (int i = 0; i < soldStock.size(); i++){
         BeanBag item = (BeanBag) soldStock.get(i);
         sold_bags += item.getQuantity();
       }
       return sold_bags;
   }

   /**
    * Method to return number of bean bags sold by this BeanBagStore with
    * matching ID.
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param id                 ID of bean bags
    * @return                   number bean bags sold by the store with matching ID
    * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
    *                          match any bag in (or previously in) stock
    * @throws IllegalIDException   if the ID is not a positive eight character
    *                           hexadecimal number
    */
   public int getNumberOfSoldBeanBags(String id) throws
   BeanBagIDNotRecognisedException, IllegalIDException {

    //check if id is valid
    if(!validID(id)){
        throw new IllegalIDException("Error: ID does not match required conditions");
    }

    int sameid_sold = 0;
    boolean found = false;
    //iterate through the sold stock and if item has matching id, increment the counter by 1
    for (int i = 0; i < soldStock.size(); i++){
         BeanBag item = (BeanBag) soldStock.get(i);
         if (id == item.getId()){
             found = true;
             sameid_sold += item.getQuantity();
         }
    }
    
    //checks if id matches any ids in soldstock
    if(found == false){
       throw new BeanBagIDNotRecognisedException("Error: The ID of the beanbag is not recognized!"); 
    }
    //return the number of bean bags sold with matching ids.
    return sameid_sold;
   }

   /**
    * Method to return total price of bean bags sold by this BeanBagStore
    * (in pence), i.e. income that has been generated by these sales).
    *
    * @return                  total cost of bean bags sold (in pence)
    */
   public int getTotalPriceOfSoldBeanBags() {

      int total_price = 0;
      //iterate through the sold stock and for every item, add the price to the total_price int
      for (int i = 0; i < soldStock.size(); i++){
         BeanBag item = (BeanBag) soldStock.get(i);
         total_price += item.getPrice();
      }
      //return total_price
      return total_price;
   }

   /**
    * Method to return total price of bean bags sold by this BeanBagStore
    * (in pence) with  matching ID (i.e. income that has been generated
    * by these sales).
    * <p>
    * The state of this BeanBagStore must be be unchanged if any exceptions are
    * thrown.
    *
    * @param id                ID of bean bags
    * @return                  total cost of bean bags sold (in pence) with
    *                          matching ID
    * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
    *                          match any bag in (or previously in) stock
    * @throws IllegalIDException   if the ID is not a positive eight character
    *                           hexadecimal number
    */
   public int getTotalPriceOfSoldBeanBags(String id) throws
   BeanBagIDNotRecognisedException, IllegalIDException {

        //check if id is valid
        if(!validID(id)){
            throw new IllegalIDException("Error: ID does not match required conditions");
        }

        int sameid_price = 0;
        boolean found = false;
        //iterate through the sold stock and for every item with matching ids, add the price to the sameid_price int
        for (int i = 0; i < soldStock.size(); i++){
             BeanBag item = (BeanBag) soldStock.get(i);
             if (id == item.getId()){
                 found = true;
                 sameid_price += item.getPrice();
                 System.out.println(item.getName());
             }
        }
        
        //checks if id matches any ids in store
        if (found == false){
           throw new BeanBagIDNotRecognisedException("Error: The ID doesn't match to any bean bags!"); 
        }
        //return sameid_price
        return sameid_price;
   }

   /**
    * Method to return the total price of reserved bean bags in this BeanBagStore
    * (i.e. income that would be generated if all the reserved stock is sold
    * to those holding the reservations).
    *
    * @return                  total price of reserved bean bags
    */
   public int getTotalPriceOfReservedBeanBags() {

      int reservedPrice = 0;
      //iterate through the reserved stock and for every item, add the price to the reservedPrice int
      for (int i = 0; i < reservedStock.size(); i++){
         BeanBag item = (BeanBag) reservedStock.get(i);
         reservedPrice += item.getPrice();
      }
      //return the sum of all reserved item prices with matching ids
      return reservedPrice;
    }

    /**
     * Method to return the free text details of a bean bag in stock. If there
     * are no String details for a bean bag, there will be an empty String
     * instance returned.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                ID of bean bag
     * @return                  any free text details relating to the bean bag
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
   public String getBeanBagDetails(String id) throws
   BeanBagIDNotRecognisedException, IllegalIDException {

        //check if id is valid
        if(!validID(id)){
            throw new IllegalIDException("Error: ID does not match required conditions");
        }
        
        boolean found = false;
        //iterate through the currentStock (contains all the information) and for beanbag with matching id, return freetext
        for (int i = 0; i < currentStock.size(); i++){
             BeanBag item = (BeanBag) currentStock.get(i);
             if (id == item.getId()){
                 found = true;
                 return item.getfreeText();
             }
        }
        
        //checks if id matches any ids in store
        if(found == false){
            throw new BeanBagIDNotRecognisedException("Error: The ID of the beanbag is not recognized!");
        }
        
        //return empty string if nothing comes out of for loop
        return "";
   }

   /**
    * Method empties this BeanBagStore of its contents and resets
    * all internal counters.
    */
   public void empty() {

       //iterate through the currentStock and remove first item everytime.
       for (int i = 0; i < currentStock.size(); i++){
           currentStock.remove(0);
       }
       //iterate through the reservedStock and remove first item everytime.
       for (int i = 0; i < reservedStock.size(); i++){
           reservedStock.remove(0);
       }
       //iterate through the soldStock and remove first item everytime.
       for (int i = 0; i < soldStock.size(); i++){
           soldStock.remove(0);
       }
   }

   /**
    * Method resets the tracking of number and costs of all bean bags sold.
    * The stock levels of this BeanBagStore and reservations should
    * be unaffected.
    */
   public void resetSaleAndCostTracking() {

          //iterate through the soldStock and for every go, set price and quantity to 0
          for (int i = 0; i < soldStock.size(); i++){
              BeanBag item = (BeanBag) soldStock.get(i);
              item.setQuantity(0);
              item.setPrice(0);
          }
   }

   /**
    * Method replaces the ID of current stock matching the first argument with the
    * ID held in the second argument. To be used if there was e.g. a data entry
    * error on the ID initially entered. After the method has completed all stock
    * which had the old ID should now have the replacement ID (including
    * reservations), and all trace of the old ID should be purged from the system
    * (e.g. tracking of previous sales that had the old ID should reflect the
    * replacement ID).
    * <p>
    * If the replacement ID already exists in the system, this method will return
    * an {@link IllegalIDException}.
    *
    * @param oldId             old ID of bean bags
    * @param replacementId     replacement ID of bean bags
    * @throws BeanBagIDNotRecognisedException  if the oldId does not match any
    *                          bag in (or previously in) stock
    * @throws IllegalIDException   if either argument is not a positive eight
    *                          character hexadecimal number, or if the
    *                          replacementID is already in use in the store as
    *                          an ID
    */
   public void replace(String oldId, String replacementId)
   throws BeanBagIDNotRecognisedException, IllegalIDException {
       //checks if the oldId is valid
       if(!validID(oldId)){
             throw new IllegalIDException("Error: ID does not match required conditions");
       }
       //checks if the replaclementId is valid
       if(!validID(replacementId)){
             throw new IllegalIDException("Error: ID does not match required conditions");
       }
       boolean found = false;

       //iterates though currentStock. If replacementid is already in use, throw exception. Else set id to replacement id
       for (int i = 0; i < currentStock.size(); i++){
         BeanBag item = (BeanBag) currentStock.get(i);
         if (oldId == item.getId()){
             if(item.getId() == replacementId){
                 throw new IllegalIDException("Error: ID does not match required conditions");
             }
             else{
                 item.setId(replacementId);
                 found = true;
             }
         }
       }
       //iterates though reservedStock. If replacementid is already in use, throw exception. Else set id to replacement id
       for (int i = 0; i < reservedStock.size(); i++){
         BeanBag item = (BeanBag) reservedStock.get(i);
         if (oldId == item.getId()){
             if(item.getId() == replacementId){
                 throw new IllegalIDException("Error: ID does not match required conditions");
             }
             else{
                 item.setId(replacementId);
                 found = true;
             }
         }
       }
       
       //checks if id matches any ids in store
       if(found == false){
         throw new BeanBagIDNotRecognisedException("Error: The ID of the beanbag is not recognized!");  
       }
  }
}
