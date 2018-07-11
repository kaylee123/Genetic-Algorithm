import java.util.ArrayList;
import java.util.Random;


public class GeneticProcess {
	private final int POPULATION_COUNT = 20;
	private final double ALPHA = 1;
	private final double BETA = 1;
	private final double CROSSOVER_PROBABILITY = 0.92;
	private final double MUTATION_PROBABILITY = 0.05;
	private final int TOTAL_GENERATION = 77;
	
	public int n;
	public int m;
	public int[] processingTimes;
	private double totalFit = 0;
	
	public Population[] pArray;
	public Population[] nextPArray;
	public ArrayList<Population> crossOveredPop = new ArrayList<>();
	
	public ArrayList<Population[]> generationArray =new ArrayList<>();
	
	private int nextPArrayIndex = 0;
	
	private void fillPopulation(Population p){
		Random rand = new Random();
		for(int i = 0 ; i < n; i++){
			p.values[i] = rand.nextInt(m) + 1;
			p.matX = new int[n][m];
		}
	}
	
	private void makeMatrix(Population p){
		for(int i = 0 ; i < n ; i++){
			for(int j = 0 ; j < m ; j++){
				if(p.values[i] == (j+1)){
					p.matX[i][j] = 1;
				}else{
					p.matX[i][j] = 0;
				}
			}
		}
		
	}
	
	public void print(){
		int min = pArray[0].objValue;
		int minIndex = 0;
		
		for(int i = 0 ; i < POPULATION_COUNT; i++){
			if(min > pArray[i].objValue){
				min = pArray[i].objValue;
				minIndex = i;
			}
		}
		
		for(int machine = 0 ; machine < m ; machine++){
			System.out.print("Machine " + (machine+1) + " | ");
			
			for(int job = 0 ; job < n; job++){
				if(pArray[minIndex].matX[job][machine] > 0){
					System.out.print("" + (job+1) + " ");
				}
			}
			System.out.print("|");
			System.out.println(" " + pArray[minIndex].individualObjVal[machine] + " ");
		}
	}
	
	public void initialize(){
		pArray = new Population[POPULATION_COUNT];
		
		for(int i = 0 ; i < POPULATION_COUNT ; i++){
			pArray[i] = new Population(n);
			fillPopulation(pArray[i]);
			makeMatrix(pArray[i]);
			evaluate(pArray[i]);
			totalFit += pArray[i].fitness;
		}
		
		for(int g = 0 ; g < TOTAL_GENERATION; g++){
			calcSelection();
			crossOver();
			mutation();		
			
			for(int i = 0 ; i < POPULATION_COUNT ; i++){
				makeMatrix(pArray[i]);
				evaluate(nextPArray[i]);
			}
			generationArray.add(pArray);
			pArray = nextPArray;
		}
		
		print();
	}
	
	public int multiProcessingTime(int[] xi)
	{
        int result = 0;
       
        for(int i = 0 ; i < processingTimes.length ; i++){
        	result += (xi[i] * processingTimes[i]);
        }
        
        
        return result;
	}
	private int max(int[] arr){
		int maxVal = 0;
		
		for(int i = 0 ; i< arr.length; i++){
			if(maxVal < arr[i])
				maxVal = arr[i];
		}
		
		return maxVal;
		
	}
	
	private int objectiveFunc(Population p){
		int[] xi;
		
		p.individualObjVal = new int[m];
		
		for(int j = 0 ; j < m ; j++){
			xi = new int[n];
			
			for(int i = 0 ; i < n; i++){
				xi[i] = p.matX[i][j];
			}
			
			p.individualObjVal[j] = multiProcessingTime(xi);
		}
		
		return max(p.individualObjVal);
	}
	
	public void evaluate(Population p){
		p.objValue = objectiveFunc(p);
		p.fitness = ALPHA * Math.exp(-1 * BETA * p.objValue);
	}
	
	private void selectPop(){
		
		for(int i = 0; i < POPULATION_COUNT ; i++){
		
			double randomNum = pArray[i].selectionRandom;
			
			for(int j = 0; j < POPULATION_COUNT; j++){
				double prev;
				
				if(j > 0)
					prev = pArray[j-1].sumProbabilty ;
				else
					prev = 0;
				
				if( prev < randomNum && randomNum <= pArray[j].sumProbabilty ){
					nextPArray[nextPArrayIndex++] = pArray[j].clone();
					break;
				}
			}
		}
	}
	
	public void calcSelection(){
		double sum = 0;
		Random random = new Random();
		nextPArray = new Population[POPULATION_COUNT];
		nextPArrayIndex = 0;
		
		for(int i = 0; i < POPULATION_COUNT; i++){
			pArray[i].probabilty = pArray[i].fitness / totalFit;
			sum += pArray[i].probabilty;
			pArray[i].sumProbabilty = sum;
			pArray[i].selectionRandom = random.nextDouble();
		}
		
		selectPop();
	}
	
	public void crossOver(){
		crossOveredPop.clear();
		
		for(int i =0 ; i < POPULATION_COUNT; i++){
			double random = pArray[i].selectionRandom;
			
			if(random < CROSSOVER_PROBABILITY){
				crossOveredPop.add(nextPArray[i]);
			}
		}
		
		int cnt = crossOveredPop.size() / 2;
		Random random = new Random();
		
		for(int i = 0 ; i < cnt ; i++){
			int split1 = 1;
			int split2 = n-1;
			
			for(int j = split1 ; j < split2 ; j++){
				int tmp;
				tmp = crossOveredPop.get(i).values[j];
				crossOveredPop.get(i).values[j] = crossOveredPop.get(i+cnt).values[j];
				crossOveredPop.get(i+cnt).values[j] = tmp;
			}
		}
		
	}
	public void mutation(){
		Random random = new Random();
		double selectProb;
		
		for(int i = 0 ; i < POPULATION_COUNT ; i++ ){
			for(int j = 0 ; j < n; j++ ){
				selectProb = random.nextDouble();
				if(selectProb <= 0.05){
					int gene = -1;
					
					do{
						gene = random.nextInt(m) + 1;
					}while(gene == nextPArray[i].values[j]);
					
					nextPArray[i].values[j] = gene;
				}
			}
		}
		
		
	}
	
}
