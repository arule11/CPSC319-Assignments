// Created by: Athena McNeil-Roberts
import java.io.*;

public class Input{
  FileReader reader;
  BufferedReader in;
  Output out;
  static ListElement[] initialList = new ListElement[0];
  static int listSize = 0;


  /**
  * Opens specifed input file and initializes out with the specifed output file
  * @param inputFile : the name of the input file
  * @param outputFile : the name of the output file
  */
  public Input(String inputFile, String outputFile){
    out = new Output(outputFile);

    File toRead = new File(inputFile);
    if(!toRead.exists()){
      System.out.println("Input file does not exist.");
      System.exit(0);
    }
    try{
      reader = new FileReader(toRead);
      in = new BufferedReader(reader);
    } catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
  }

  /**
  * Reads the input file, adding each element to the array
  */
  public void readFile(){
    ListElement helper;
    try{
      String line = in.readLine();

      while(line != null){
        addToList(new ListElement(line));
        line = in.readLine();
      }
    }catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
  }

  /**
  * Adds the specifed ListElement to the array - if a valid symbol/number
  * @param elementToSort : the specified ListElement to add into the array
  */
  public void addToList(ListElement elementToSort) {
    if(elementToSort.invalid){
      out.writeErrorToFile();
      System.exit(0);
    }

    ListElement[] newList = new ListElement[listSize + 1];

  	if(listSize == 0) {
  		newList[0] = elementToSort;
      listSize++;
  	}else {
  		for(int i = 0; i < listSize; i++) {
  			newList[i] = initialList[i];
  		}
  		newList[listSize] = elementToSort;
  		listSize++;
  	}
		initialList = newList;
	}

  /**
  * Checks if the array of ListElements contains any '666' elements; if so removes
  *   them all and adds a single '@' element to the array. And sorts the resulting
  *    array in ascending order.
  */
  public void check666(){
    int count = 0;
    for(int i = 0; i < listSize; i++){
      if(!initialList[i].isSymbol()){
        if(initialList[i].value == 666){
          removeElement(initialList[i]);
          count++;
          i--;
        }
      }
    }
    if(count > 0){
      addToList(new ListElement("@"));
      sortDescending(initialList, listSize);
      reverse(initialList, listSize);
    }
  }


  /**
  * Removes the specifed ListElement from the array
  * @param toRemove : the specified ListElement to remove from the array
  */
  public void removeElement(ListElement toRemove){
    ListElement[] newList = new ListElement[listSize - 1];
    int j = 0;
    for(int i = 0; i < listSize; i++){
      if(initialList[i] != toRemove){
        newList[j] = initialList[i];
        j++;
      }
    }
    listSize--;
    initialList = newList;
  }

  /**
  * Sorts the specifed array in descending order
  * @param arr : the specified array to sort
  * @param size : the size of the specifed array
  */
  public void sortDescending(ListElement[] arrToSort, int size){
    ListElement temp;
    for(int i = 0; i < size; i++){
      for(int j = i + 1; j < size; j++){
        if(arrToSort[i].compare(arrToSort[j]) < 0){
          temp = arrToSort[i];
          arrToSort[i] = arrToSort[j];
          arrToSort[j] = temp;
        }
      }
    }
  }

  /**
  * Reverses the order of the specifed array
  * @param arr : the specified array to reverse
  * @param size : the size of the specifed array
  */
  public void reverse(ListElement[] arr, int size){
    int i = 0;
    int j = size - 1;
    while(i < j){
      ListElement temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
      i++;
      j--;
    }
  }


  public static void main(String[] args){
    if(args.length < 2){
      System.out.println("Invalid number of arguments. Usage: Input <inputfile> <outputfile>");
      System.exit(0);
    }

    Input run = new Input(args[0], args[1]);
    run.readFile();
    run.sortDescending(initialList, listSize);
    run.check666();
    run.out.writeListToFile(initialList);
  }
}
