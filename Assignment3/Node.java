// Created by: Athena McNeil-Roberts

public class Node{

  Node left;
  Node mid;
  Node right;

  String value;
  int level;

  /**
  * Creates a node setting the value and level to the specified values
  * @param val : the payload of the node
  * @param level : the tree level of the node
  */
  public Node(String val, int level){
    this.value = val;
    this.level = level;
  }


}
