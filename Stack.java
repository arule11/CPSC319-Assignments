// Created by: Athena McNeil-Roberts

import java.io.*;

/**
* Class representing a confused stack - implemented using Arrays
*/
public class Stack{

  private static int[] stackElements = new int[0];
  private static int stackSize = 0;
  private static File outputFile;
  private static String[] outputLines = new String[0];

  public static void main(String[] args){
    if(args.length < 2){
      System.out.println("Invalid number of arguments. Usage: Stack <inputfile> <outputfile> ");
      System.exit(0);
    }
    outputFile = new File(args[1]);

    try{
      // over write the output file if it already exists
      if(outputFile.exists() && outputFile.isFile()){
        outputFile.delete();
      }
      outputFile.createNewFile();
    }catch(Exception e){
      System.out.println("Error: " + e.getMessage());
      writeToFile();
      System.exit(0);
    }

    File inputFile = new File(args[0]);
    String[] commands = openFile(inputFile);
    //compute all the commands read from the input file
    for(int i = 0; commands[i] != null; i++){
      try {
        computeLine(commands[i]);
      } catch (Exception e){
        System.out.println("Error: " + e.getMessage());
        writeToFile();
        System.exit(0);
      }
    }
    writeToFile();
  }

  /**
  * Opens specifed file and reads each line
  * @param fileToOpen : the name of the file
  * @return Returns an array of Strings - all commands read from file
  */
  public static String[] openFile(File fileToOpen){
    String[] commands = new String[50];
    try{
      if(!fileToOpen.exists()){
        System.out.println("Input file does not exist.");
        System.exit(0);
      }

      FileReader reader = new FileReader(fileToOpen);
      BufferedReader in = new BufferedReader(reader);

      String line = in.readLine();
      // if the file is blank or starts with a blank - output "Input error."
      if(line == null){
        addOutput("Input error.");
        writeToFile();
        System.exit(0);
      }

      int i = 0;
      // read all lines from the input file, adding each to the array of commands
      while(line != null){
        commands[i] = line;
        line = in.readLine();
        i++;
      }
      in.close();
    } catch (Exception e){
      writeToFile();
      System.out.println("Error: " + e.getMessage());
      System.exit(0);
    }
    return commands;
  }

  /**
  * Compute action (either pop push or top) corresponding to a line from the file read
  * @param line : the specified line
  */
  public static void computeLine(String line){
    // check that the command contains both braces - if not output "Input error."
    if(!line.contains("(") || !line.contains(")")){
      addOutput("Input error.");
      writeToFile();
      System.exit(0);
    }

    int indexOne = line.indexOf('(');
    int indexTwo = line.indexOf(')');
    String paramValue = line.substring(indexOne + 1, indexTwo);

    // check for whitespace outside of the braces - if there is output "Input error."
    if(line.contains(" ") && !paramValue.contains(" ")){
      addOutput("Input error.");
      writeToFile();
      System.exit(0);
    }

    if(line.contains("push")){
      push(paramValue);
    } else if(line.contains("pop()") && line.length() == 5){
      pop();
    } else if(line.contains("top()") && line.length() == 5){
      top();
    } else {
      addOutput("Input error.");
      writeToFile();
      System.exit(0);
    }
  }

  /**
  * Pushes the element onto the stack - preforming confused actions for given cases
  * @param elementToPush: the specified value to be push onto the stack
  */
  public static void push(String elementToPush){
    try{
      // check for leading zeros in value being pushed - if there is output "Imput error."
      if((elementToPush.length() > 1) && (elementToPush.charAt(0) == '0')){
        addOutput("Imput error.");
        writeToFile();
        System.exit(0);
      }

      int param = Integer.parseInt(elementToPush);
      int[] newStack = new int[stackSize + 1];
      // check if value being pushed is negative - if yes, output "Imput error."
      if(param < 0){
        addOutput("Imput error.");
        writeToFile();
        System.exit(0);
      }

      switch (param){
        // push(0) should not result in any change of the stack, except if the stack
        //    is empty, in which case it adds 0 to the stack
        case 0:
          if(isEmpty()){
            newStack[0] = param;
            stackSize++;
            stackElements = newStack;
          }
          break;
        // push(666) adds not just once 666 to the stack, but it adds it 3 times
        case 666:
          if(isEmpty()){
            newStack = new int[3];
            newStack[0] = param;
            newStack[1] = param;
            newStack[2] = param;
            stackSize = stackSize + 3;
          }else{
            newStack = new int[stackSize + 3];
            for(int i = 0; i < stackSize; i++){
              newStack[i] = stackElements[i];
            }
            newStack[stackSize] = param;
            newStack[stackSize + 1] = param;
            newStack[stackSize + 2] = param;
            stackSize = stackSize + 3;
          }
          stackElements = newStack;
          break;
        // push(3) does not add 3 to the stack, but instead 7
        case 3:
          if(isEmpty()){
            newStack[0] = 7;
            stackSize++;
          }else{
            for(int i = 0; i < stackSize; i++){
              newStack[i] = stackElements[i];
            }
            newStack[stackSize] = 7;
            stackSize++;
          }
          stackElements = newStack;
          break;
        // push(13) first empties the stack (outputting each element in the sequence a
        //    series of pops by a non-confused stack would do) and then enters 13 on the stack.
        case 13:
          if(isEmpty()){
            newStack[0] = 13;
            stackSize++;
          } else{
            for(int i = stackSize - 1; i >= 0; i--){
              addOutput(Integer.toString(stackElements[i]));
            }
            newStack = new int[1];
            newStack[0] = 13;
            stackSize = 1;
          }
          stackElements = newStack;
          break;
        // normal push, adds the element to the stack
        default:
          if(isEmpty()){
            newStack[0] = param;
            stackSize++;
          } else{
            for(int i = 0; i < stackSize; i++){
              newStack[i] = stackElements[i];
            }
            newStack[stackSize] = param;
            stackSize++;
          }
          stackElements = newStack;
          break;
        }
    } catch(NumberFormatException e){
      addOutput("Imput error.");
      writeToFile();
      System.exit(0);
      return;
    }
  }

  /**
  * Pops the element from the stack - removing and outputting it
  *     - preforming confused actions for given cases
  * @return Returns an int
  */
  public static int pop(){
    // pop() on an empty stack should result in the output "Error" and the program should terminate
    if(isEmpty()){
      addOutput("Error");
      writeToFile();
      System.exit(0);
      return -1;
    } else {
      int[] newStack = new int[stackSize - 1];
      int last = stackElements[stackSize - 1];

      switch(last){
        // If the top of the stack is 666, pop() does not just remove it from the stack (and outputs it),
        //       it also removes the next element from the stack, without outputting it (if it exists)
        case 666:
          if(stackSize > 2){
            newStack = new int[stackSize - 2];
            for(int i = 0; i < stackSize - 2; i++){
              newStack[i] = stackElements[i];
            }
            stackSize = stackSize - 2;
          } else {
            newStack = new int[0];
            stackSize = 0;
          }
          stackElements = newStack;
          addOutput(Integer.toString(last));
          return last;
        // If the top of the stack is 7, pop() does not remove it from the stack, but just outputs 7.
        case 7:
          addOutput(Integer.toString(last));
          return last;
        // If the top of the stack is 42, pop() removes all elements of the stack, only outputting 42
        case 42:
          newStack = new int[0];
          stackElements = newStack;
          stackSize = 0;
          addOutput(Integer.toString(42));
          return 42;
        // normal pop(), removes and outputs the top element on the stack
        default:
          for(int i = 0; i < stackSize - 1; i++){
            newStack[i] = stackElements[i];
          }
          stackElements = newStack;
          stackSize--;
          addOutput(Integer.toString(last));
          return last;
      }
    }
  }

  /**
  * Gets the current element at the top of the stack - preforming confused actions for given cases
  * @return Returns an int
  */
  public static int top(){
    // top() on an empty stack should result in an output of "null" and the program continues
    if(isEmpty()){
      addOutput("null");
      return -1;
    } else {
      int top = stackElements[stackSize - 1];

      switch(top){
        // If the top element of the stack is 666, top() does not output 666, but 999
        case 666:
          addOutput(Integer.toString(999));
          return 999;
        // If the top of the stack is 7, top() does not output 7, it just removes it from the stack
        case 7:
        // top() does not output 7, it just removes it from the stack
          int[] newStack = new int[stackSize - 1];

          for(int i = 0; i < stackSize - 1; i++){
            newStack[i] = stackElements[i];
          }
          stackElements = newStack;
          stackSize--;
          return -1;
        // If the top of the stack is 319, then applying top() outputs 666
        case 319:
          addOutput(Integer.toString(666));
          return 666;
        // normal top(), outputs the top element on the stack (without removing it)
        default:
          addOutput(Integer.toString(top));
          return top;
      }
    }
  }

  /**
  * Adds the specifed String to the array of output values
  * @param output: the specifed string to be outputted
  */
  public static void addOutput(String output){
    String[] newOutput = new String[outputLines.length + 1];
    if(outputLines.length == 0){
      newOutput[0] = output;
    }else{
      for(int i = 0; i < outputLines.length; i++){
        newOutput[i] = outputLines[i];
      }
      newOutput[outputLines.length] = output;
    }
    outputLines = newOutput;
  }

  /**
  * Writes all elements of the array of output values to the output file
  */
  public static void writeToFile(){
    try {
      FileWriter writer = new FileWriter(outputFile);
      BufferedWriter out = new BufferedWriter(writer);

      for(int i = 0; i < outputLines.length - 1; i++){
        out.write(outputLines[i]);
        out.newLine();
      }
      out.write(outputLines[outputLines.length - 1]);

      out.flush();
      out.close();
    } catch(Exception e){
      System.out.println("Error: " + e.getMessage());
      System.exit(0);
    }
  }

  /**
  * Determines whether the stack is currently empty (has no elements)
  * @return Returns a boolean
  */
  public static boolean isEmpty(){
    if(stackSize == 0){
      return true;
    } else {
      return false;
    }
  }

  /**
  * prints the stack to the console
  */
  public static void printStack(){
    System.out.println("printing Stack");
    for(int i = stackSize - 1; i >= 0; i--){
      System.out.println(stackElements[i]);
    }
  }

}
