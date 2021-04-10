// Created by: Athena McNeil-Roberts

public class ListElement {

	String[] symbols = new String[]{"Do", "@", "&", "Fa", "$", "Asymbolwithareallylongname", "Re", "One", "Two", "Three", "Mi", "%"};
	Double[] symbolsAsNum = new Double[]{0.5, 3.3, 3.6, 15.5, 20.5, 55.5, 100.5, 103.25, 103.5, 103.75, 1000.5, 1005000.5};
	String symbol;
	Double value;
	boolean invalid = false;


	/**
	* Sets the values of: symbol, value, and invalid, for the element based on the specified String
	* @param elementValue : the specified String read as the element
	*/
	public ListElement(String elementValue){
		if(elementValue.matches("^(0|[1-9]\\d*$)")) {
			value = (double)Integer.parseInt(elementValue);
			return;
		}else{
			int symIndex = 0;
			boolean symValue = false;
			for (int i = 0; i < symbols.length; i++) {
				if(elementValue.equals(symbols[i])) {
					symValue = true;
					symIndex = i;
				}
			}
			if(symValue){
				symbol = elementValue;
				value = symbolsAsNum[symIndex];
			}else {
				invalid = true;
			}
		}
	}

	/**
	* Checks if the ListElement is a symbol
	*/
	public boolean isSymbol() {
		return this.symbol != null;
	}

	/**
	* Gets the index of the specifed symbol in the list of all symbols
	* @param sym : the specified symbol
	*/
	public int getIndex(String sym) {
		for(int i = 0; i < symbols.length; i++){
			if(sym.equals(symbols[i])){
				return i;
			}
		}
		return -3;
	}


	/**
	* Compares the specified element against the one calling this method. returning a
	* value > 0 if thisObj > compareObj ; value < 0 if thisObj < compareOb ; and
	* 	value = 0 if thisObj = compareObj
	* @param elementToCompare : the specified element to compare against
	*/
	public int compare(ListElement elementToCompare){
		return this.value.compareTo(elementToCompare.value);
		}

}
