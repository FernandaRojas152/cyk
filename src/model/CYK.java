package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CYK {
	
	/**
	 * variables that contains the CFG
	 */
	ArrayList<String> vars;
	
	/**
	 * Initial matrix with productions
	 */
	String[][] mainMatrix;
	
	/**
	 * final matrix
	 */
	String[][] resultMatrix;
	
	/**
	 * Map with values of the matrix inital
	 */
	HashMap<String,List<String>> map;

	public ArrayList<String> getVars() {
		return vars;
	}

	public void setVars(ArrayList<String> vars) {
		this.vars = vars;
	}

	public String[][] getMainMatrix() {
		return mainMatrix;
	}

	public void setMainMatrix(String[][] mainMatrix) {
		this.mainMatrix = mainMatrix;
	}

	public String[][] getResultMatrix() {
		return resultMatrix;
	}

	public void setResultMatrix(String[][] resultMatrix) {
		this.resultMatrix = resultMatrix;
	}

	public HashMap<String, List<String>> getMap() {
		return map;
	}

	public void setMap(HashMap<String, List<String>> map) {
		this.map = map;
	}
	
	public CYK(String[][] matrix, int stringSize) {	
		mainMatrix = matrix;
		resultMatrix = new String[stringSize][stringSize];
		vars = new ArrayList<String>();
		map = new HashMap<>();
	}
	
	/**
	 * This method fills HashMap
	 */
	public void addValueToMap() {
		String pVariable = "";
		vars = new ArrayList<String>();
		for (int i = 0; i < mainMatrix.length; i++) {
			List<String> productions = new ArrayList<String>();
			for (int j = 0; j < mainMatrix[i].length; j++) {
					if(j == 0) {
						vars.add(mainMatrix[i][j]);
						pVariable = mainMatrix[i][j];												
					}else {
						String[] productionsArray = mainMatrix[i][j].split("\\|");
						productions = Arrays.asList(productionsArray);	
					}
			}
			addValueAux(pVariable,productions);
		}		
	}	
	/**
	 * This method helps addValueToMap method to add values in​​to the HashMap 
	 * @param pVariable It is the producing variable of production
	 * @param productions they are the productions of the producing variable
	 */
	public void addValueAux(String pVariable, List<String> productions) {
		 map.put(pVariable, productions);
	}
	
	/**
	 * Performs CYK algorithm
	 * @param w string 
	 */
	public void CYKAlgorthm(String w) {
		for (int j = 2; j <= w.length(); j++) {
			for (int i = 1; i <= (w.length()-j+1); i++) {
				List<String> tuples = new ArrayList<String>();
				for (int k = 1; k <= j-1; k++) {
					String[] partition = resultMatrix[i-1][k-1].split(",");
					String[] partition1 = resultMatrix[(i-1)+(k)][(j-1)-(k)].split(",");
					String[] resultCartesianProduct = cartesianProduct(partition,partition1);
					tuples.addAll(Arrays.asList(resultCartesianProduct));					
				}
				resultMatrix[i-1][j-1] = generates(tuples);				
			}
		}
	}
	
	/**
	 * Verifies if a string is contained in one of the last position of the result matrix
	 * @return returns true if it contains the string
	 */
	public boolean containsString() {
		String[] checkValue = resultMatrix[0][resultMatrix[0].length-1].split(",");
		boolean contained = false;
		for (int i = 0; i < checkValue.length && !contained; i++) {
			if(checkValue[i].equals(vars.get(0))) {
				contained = true;
			}
		}
		return contained;
	}
	
	/**
	 * Adds the first column to the result matrix 
	 * @param w string to analyze
	 */
	public void addFirstColumn(String w){
		for (int j = 1; j <= w.length(); j++) {
			String value = "";
			for (int k = 0; k < vars.size(); k++) {
				String subCadena = Character.toString(w.charAt(j - 1));
				String key = vars.get(k);
				if (map.get(key).contains(subCadena)) {
					value += key + ",";
				}
			}
			if (value != "" && value.charAt(value.length() - 1) == ',') {
				value = value.substring(0, value.length() - 1);
			}
			resultMatrix[j - 1][0] = value;
		}
	}
	

	/**
	 * Which variable generates a string
	 * @param tuples 
	 * @return
	 */
	public String generates(List<String> tuples) {
		List<String> rTuple = new ArrayList<String>();
		String result = "";

		for (int i = 0; i < tuples.size(); i++) {
			for (int j = 0; j < vars.size(); j++) {
				String key = vars.get(j);

				if (map.get(key).contains(tuples.get(i))) {
					if (!rTuple.contains(key)) {
						rTuple.add(key);
						result += key + ",";
					}

				}
			}

		}
		if (result != "" && result.charAt(result.length() - 1) == ',') {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	/**
	 * Allows realizing the Cartesian product between two partitions
	 * @param partition - Arrays to String
	 * @param partition1 - Arrays to String
	 * @return result to cartesian product between partition and partition1
	 */
	public String[] cartesianProduct(String[] partition, String[] partition1) {

		int size = partition.length * partition1.length;
		String[] combs = new String[size];
		int counter = 0;
		for (int i = 0; i < partition.length; i++) {
			for (int j = 0; j < partition1.length; j++) {
				combs[counter] = partition[i] + "" + partition1[j];
				counter++;
			}
		}

		return combs;
	}

}
