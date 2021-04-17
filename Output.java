
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
  * Writes the specified value to the output file
  * @param val : the value to write to the output file
  */
  public void writeToFile(String val){
    try{
      out.write(val);
      out.flush();
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
