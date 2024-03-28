package Game;
import Game.NumPuzView.SplashScreen;

/**
 * 
 * @author suraj
 *
 */
public class NumPuz{
	
	/**
	 * main method
	 * @param args - additional arguments for the program
	 */
	public static void main(String[] args) {
		
		SplashScreen.startSplashScreen();
		
		NumPuzView numPuzView = new NumPuzView();
		NumPuzModel numPuzModel = new NumPuzModel();
		NumPuzController numPuzController = new NumPuzController(numPuzView, numPuzModel);
		NumPuzClient numPuzClient = new NumPuzClient(numPuzController, numPuzView);
		numPuzClient.start();
		numPuzController.start();
		
	}
}
