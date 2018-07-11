
public class Population {
	public int[] values;
	public int[][] matX;
	public int[] individualObjVal;
	public int objValue;
	public double fitness;
	public double probabilty;
	public double sumProbabilty;
	public double selectionRandom;
	
	public Population(int n){
		values = new int[n];
	}
	
	public void printX(){
		for(int j = 0 ; j < 2 ; j++){
			for(int i = 0 ; i < matX.length ; i++){
				System.out.print("" + matX[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	public Population clone(){
		Population cloned = new Population(values.length);
		
		cloned.values = values.clone();
		
		int[][] clonedMat = new int[matX.length][];
		for(int i = 0 ; i < matX.length ; i++){
			clonedMat[i] = matX[i].clone();
		}
		cloned.matX = clonedMat;
		
		cloned.individualObjVal = individualObjVal.clone();
		cloned.objValue = objValue;
		cloned.fitness = fitness;
		cloned.probabilty = probabilty;
		cloned.sumProbabilty = sumProbabilty;
		cloned.selectionRandom = selectionRandom;
		
		return cloned;
	}
}
