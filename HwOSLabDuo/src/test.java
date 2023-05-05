import java.util.ArrayList;
import java.util.Random;

public class test {

	public static void main(String[] args) {
		// Dynamic assigning port // incomplete
		ArrayList<Integer> activePort = new ArrayList<Integer>();
		Random ran =new Random();
		int max = 5010, min = 5000;
		
		for(int i = 0 ; i < 9 ; i++) {
			int tmp = ran.nextInt((max - min)+1)+min;;
			activePort.add(tmp);
		}
		
		
		int randomPort = ran.nextInt((max - min)+1)+min;
		int tmp = activePort.size()-1;
		int tmp2 = 0;
		while(tmp2 != tmp){
			
			if(randomPort == activePort.get(tmp)){
				randomPort = ran.nextInt((max - min)+1)+min;
				tmp -=1;
			}
		}
		
	
	}

}
