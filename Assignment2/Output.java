// Created by: Athena McNeil-Roberts

import java.io.*;

public class Output{
  FileWriter writer;
  BufferedWriter out;

  /**
  * Creates a new output file
  * @param outputFile : the specified output file
  */
  public Output(String outputFile){
    File toWrite = new File(outputFile);
    try{
      if(toWrite.exists() && toWrite.isFile()){
        toWrite.delete();
      }
      toWrite.createNewFile();

      writer = new FileWriter(outputFile);
      out = new BufferedWriter(writer);
    }catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
  }

  /**
  * Writes "Input error." to the output file and terminates
  */
  public void writeErrorToFile(){
    try{
      out.write("Input error.");
      cleanUp();
    }catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
  }

  /**
  * Writes all elements of the specified sorted array to the output file
  * @param sorted : the specifed array
  */
  public void writeListToFile(ListElement[] sorted){
    try{
      for(int i = 0; i < sorted.length; i++) {
        if(sorted[i].isSymbol()){
          out.write(sorted[i].symbol + "\n");
        } else{
          out.write((sorted[i].value).intValue() + "\n");
        }
      }
      cleanUp();
    }catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
  }

  /**
  * Closes any open I/O
  */
  public void cleanUp(){
    try{
      out.flush();
      out.close();
    } catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
  }

}
