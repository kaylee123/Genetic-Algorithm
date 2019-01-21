import java.io.*;
import java.util.Scanner;
public class GeneticMain {

	private static String readFile(){
		FileReader reader;
		String str = "";
		try {
			reader = new FileReader("inputData.txt");
			
			int ch;
		
			
			while((ch = reader.read()) != -1) { 
				str += (char)ch;
			}
			
			reader.close(); 
			
			return str;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		String fileStr = readFile();
		GeneticProcess gProcess = new GeneticProcess();
		
		if(fileStr != ""){
			Scanner scanner = new Scanner(fileStr);
			
			gProcess.n = scanner.nextInt();
			gProcess.m = scanner.nextInt();
			gProcess.processingTimes = new int[gProcess.n];
			
			for(int i = 0; i < gProcess.n ; i++){
				gProcess.processingTimes[i] = scanner.nextInt();
			}
			
			gProcess.initialize();
			
			
		}
		
	}

}
