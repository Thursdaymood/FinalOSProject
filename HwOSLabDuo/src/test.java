import java.util.ArrayList;

public class test {
	static String tmp = "Hello";

	public static void main(String[] args) {
		System.out.println(checkLetter(1,"l"));

	}

	private static boolean checkLetter(int turn, String letter) {

		ArrayList<String> tmpCheck = new ArrayList<String>();

		if (tmp.contains(letter)) {
			return true;
		}
		
		return false;

	}

}
