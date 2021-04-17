// Created by: Athena McNeil-Roberts

/**
* Class representing a ternary tree. root is a Node, levelNodes is an array of Strings,
*   and out is an Output object
*/
public class Tree{

  static Node root;
  String[] levelNodes = new String[0];
  Output out;

  /**
  * Creates a tree with the root value of 'root'
  * @param outputFile : the name of the output file
  */
  public Tree(String outputFile){
    out = new Output(outputFile);
    this.root = new Node("root", 0);
  }


  /**
  * Searches the tree for the node matching the specified value. highest level and
  *   either right or left most depending on specified boolean
  * @param parent : the specified parent node
  * @param val : the value searching for
  * @param right : boolean indicating whether or not to find the right most matching node
  * @return Returns the highest level (right or left most) node matching the search value
  */
  public Node searchTree(Node parent, String val, boolean right){
    Node match = null;
    int highestLevel = 0;

    if(parent.value.equals(val)){
      match = parent;
    }

    if(right){
      if(parent.left != null){
        if(searchTree(parent.left, val, right) != null){
          Node temp = searchTree(parent.left, val, right);

          if(temp.level >= highestLevel){
            highestLevel = temp.level;
            match = temp;
          }
        }
      }
    } else{
      if(parent.right != null){
        if(searchTree(parent.right, val, right) != null){
          Node temp = searchTree(parent.right, val, right);

          if(temp.level >= highestLevel){
            highestLevel = temp.level;
            match = temp;
          }
        }
      }
    }

    if(parent.mid != null){
      if(searchTree(parent.mid, val, right) != null){
        Node temp = searchTree(parent.mid, val, right);

        if(temp.level >= highestLevel){
          highestLevel = temp.level;
          match = temp;
        }
      }
    }

    if(right){
      if(parent.right != null){
        if(searchTree(parent.right, val, right) != null){
          Node temp = searchTree(parent.right, val, right);

          if(temp.level >= highestLevel){
            highestLevel = temp.level;
            match = temp;
          }
        }
      }
    }else{
      if(parent.left != null){
        if(searchTree(parent.left, val, right) != null){
          Node temp = searchTree(parent.left, val, right);

          if(temp.level >= highestLevel){
            highestLevel = temp.level;
            match = temp;
          }
        }
      }
    }
    return match;
  }


  /**
  * Adds a node with payload valB to the node with payload valA in the tree as a left child
  * @param valA : the parent payload to search for
  * @param valB : the childs payload to add
  */
  public void AddL(String valA, String valB){
    Node parent = searchTree(root, valA, true);
    if(parent == null){
      return;
    }

    if(valB.charAt(0) == '$'){
      String newVal = valB.substring(1, valB.length());
      if(parent.left != null){
        parent.left.value = newVal;
      }else{
        parent.left = new Node(newVal, parent.level + 1);
      }
    }else{
      if(parent.left == null){
        parent.left = new Node(valB, parent.level + 1);
      }else{
        out.writeToFile("Add operation not possible.\n");
      }
    }
  }

  /**
  * Adds a node with payload valB to the node with payload valA in the tree as a middle child
  * @param valA : the parent payload to search for
  * @param valB : the childs payload to add
  */
  public void AddM(String valA, String valB){
    Node parent = searchTree(root, valA, true);

    if(parent == null){
      return;
    }

    if(valB.charAt(0) == '$'){
      String newVal = valB.substring(1, valB.length());
      if(parent.mid != null){
        parent.mid.value = newVal;
      }else{
        parent.mid = new Node(newVal, parent.level + 1);
      }
    }else{
      if(parent.mid == null){
        parent.mid = new Node(valB, parent.level + 1);
      }else{
        out.writeToFile("Add operation not possible.\n");
      }
    }

  }


  /**
  * Adds a node with payload valB to the node with payload valA in the tree as a right child
  * @param valA : the parent payload to search for
  * @param valB : the childs payload to add
  */
  public void AddR(String valA, String valB){
    Node parent = searchTree(root, valA, true);
    if(parent == null){
      return;
    }

    if(valB.charAt(0) == '$'){
      String newVal = valB.substring(1, valB.length());
      if(parent.right != null){
        parent.right.value = newVal;
      }else{
        parent.right = new Node(newVal, parent.level + 1);
      }
    }else{
      if(parent.right == null){
        parent.right = new Node(valB, parent.level + 1);
      }else{
        out.writeToFile("Add operation not possible.\n");
      }
    }
  }


  /**
  * Deletes the whole left subtree of the node with payload valA
  * @param valA : the parent payload to search for
  */
  public void DelL(String valA){
    Node parent = searchTree(root, valA, false);
    if(parent != null){
      parent.left = null;
    }
  }


  /**
  * Deletes the whole middle subtree of the node with payload valA
  * @param valA : the parent payload to search for
  */
  public void DelM(String valA){
    Node parent = searchTree(root, valA, false);
    if(parent != null){
      parent.mid = null;
    }
  }


  /**
  * Deletes the whole right subtree of the node with payload valA
  * @param valA : the parent payload to search for
  */
  public void DelR(String valA){
    Node parent = searchTree(root, valA, false);
    if(parent != null){
      parent.right = null;
    }
  }


  /**
  * Changes the payload of all nodes in the tree that currently have payload valA to the new payload valB
  * @param valA : the orginal payload to change
  * @param valB : the value to exchange with
  */
  public void Exchange(String valA, String valB){
    Node exchange = null;
    while((exchange = searchTree(root, valA, true)) != null){
      if(valB.charAt(0) == '$'){
        String newVal = valB.substring(1, valB.length());
        exchange.value = exchange.value + newVal;
      }else{
        exchange.value = valB;
      }
    }
  }



  /**
  * Calculates the height of the current tree
  * @param parent : the parent node
  * @return Returns an int of tree height
  */
  public int getTreeHeight(Node parent){
      if (parent == null){
        return 0;
      }else{
        int left = getTreeHeight(parent.left);
        int mid = getTreeHeight(parent.mid);
        int right = getTreeHeight(parent.right);

        if(left > mid && left > right){
          return left + 1;
        }else if(mid > left && mid > right){
          return mid + 1;
        }else{
          return right + 1;
        }
      }
  }


  /**
  * Prints the current tree
  */
  public void Print(){
    int treeHeight = getTreeHeight(root);
    for(int i = 1; i <= treeHeight; i++){
      levelNodes = new String[0];
      getLevelValues(root, i);
      printLevel();
      out.writeToFile("\n");
    }
  }


  /**
  * Gets the nodes at each tree level and adds them to an array to be printed
  * @param parent : the parent node
  * @param level : the current level of the tree
  */
  /**
  * Code found from : https://www.geeksforgeeks.org/print-level-order-traversal-line-line/
  */
  public void getLevelValues(Node parent, int level){
    if (parent == null){
      return;
    }
    if(level == 1){
      addLevel(parent.value);
    }else if(level > 1){
      getLevelValues(parent.left, level - 1);
      getLevelValues(parent.mid, level - 1);
      getLevelValues(parent.right, level - 1);
    }
  }


  /**
  * Add the specified node value to an array to be printed
  * @param toAdd : the node value to add
  */
  public void addLevel(String toAdd){
    String[] newArr = new String[levelNodes.length + 1];
    for(int i = 0; i < levelNodes.length; i++){
      newArr[i] = levelNodes[i];
    }
    newArr[levelNodes.length] = toAdd;
    levelNodes = newArr;
  }

  /**
  * Prints all node values of the current level
  */
  public void printLevel(){
    for(int i = 0; i < levelNodes.length; i++){
      if(i == levelNodes.length - 1){
        out.writeToFile(levelNodes[i]);
      }else{
        out.writeToFile(levelNodes[i] + " ; ");
      }
    }
  }


  /**
  * Prints "Input error." to the output file
  */
  public void ErrorExit(){
    out.writeErrorToFile();
  }

  /**
  * Closes any open I/O
  */
  public void cleanUp(){
    out.cleanUp();
  }

}
