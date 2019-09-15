/**
 *
 */
package start;
import view.*;
import model.*;
import controller.*;

/**
 * @author Artur
 *
 */
public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		View V = new View();
		Model M = new Model(V);
		Controller C = new Controller(V, M);
		C.start();
	}

}
