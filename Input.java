import java.io.*;
import java.util.regex.*;

public class Input{
  FileReader reader;
  BufferedReader in;
  String[] commands = new String[0];
  static Tree ternaryTree;

  /**
  * Opens specifed input file and initializes ternaryTree
  * @param inputFile : the name of the input file
  * @param outputFile : the name of the output file
  */
  public Input(String inputFile, String outputFile){
    ternaryTree = new Tree(outputFile);

    File toRead = new File(inputFile);
    if(!toRead.exists()){
      System.out.println("Input file does not exist.");
      System.exit(0);
    }
    try{
      reader = new FileReader(toRead);
      in = new BufferedReader(reader);
      readFile();
      //compute all the commands read from the input file
      for(int i = 0; i < commands.length; i++){
        try {
          computeLine(commands[i]);
        } catch (Exception e){
          System.out.println(e);
          System.exit(0);
        }
      }
    } catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
    ternaryTree.cleanUp();
  }


  /**
  * Reads the input file, adding each command to the array
  */
  public void readFile(){
    try{
      String line = in.readLine();

      if(line == null){
        ternaryTree.ErrorExit();
        System.exit(0);
      }
      while(line != null){
        addToCommands(line);
        line = in.readLine();
      }
    }catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
  }


  /**
  * Adds the specified command to the array of commands
  * @param command : the command to add
  */
  public void addToCommands(String command) {
    String[] newArr = new String[commands.length + 1];

    if(commands.length == 0) {
      newArr[0] = command;
    }else {
      for(int i = 0; i < commands.length; i++) {
        newArr[i] = commands[i];
      }
      newArr[commands.length] = command;
    }
    commands = newArr;
  }



  /**
  * Computes the specified command on the ternary tree
  * @param line : the command to perform
  */
  public static void computeLine(String line){
    if(line.contains("\n") || line.contains("\t") || line.contains(" ")){
      ternaryTree.ErrorExit();
      System.exit(0);
    }

    String valA = null;
    String valB = null;

    if(line.matches("(AddL|AddM|AddR)(\\([^\\s]+[,]+[^\\s]+\\))")){

      int comma = line.indexOf(',');
      while(line.charAt(comma + 1) == ','){
        comma = comma + 1;
      }
      valA = line.substring(5, comma);
      valB = line.substring(comma + 1, line.length() -1);

      if(line.contains("AddL")){
        ternaryTree.AddL(valA, valB);
      }else if(line.contains("AddM")){
        ternaryTree.AddM(valA, valB);
      }else{
        ternaryTree.AddR(valA, valB);
      }

    }else if(line.matches("(DelL|DelM|DelR)\\([^\\s]+\\)")){
      valA = line.substring(5, line.length() - 1);

      if(line.contains("DelL")){
        ternaryTree.DelL(valA);
      }else if(line.contains("DelM")){
        ternaryTree.DelM(valA);
      }else{
        ternaryTree.DelR(valA);
      }

    }else if(line.matches("Exchange\\([^\\s]+[,]+[^\\s]+\\)")){

      int comma = line.indexOf(',');
      while(line.charAt(comma + 1) == ','){
        comma = comma + 1;
      }
      valA = line.substring(9, comma);
      valB = line.substring(comma + 1, line.length() -1);

      ternaryTree.Exchange(valA, valB);
    }
    //else if(line.matches("Print\\(\\)")){
    else if(line.contains("Print()") && line.length() == 7){
      ternaryTree.Print();
    }else{
      ternaryTree.ErrorExit();
      System.exit(0);
    }
  }


  public static void main(String[] args){
    if(args.length < 2){
      System.out.println("Invalid number of arguments. Usage: Input <inputfile> <outputfile>");
      System.exit(0);
    }
    Input run = new Input(args[0], args[1]);
  }

}
