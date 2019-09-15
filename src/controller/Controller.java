/**
 *
 */
package controller;
import view.*;
import model.*;

/**
 * @author Artur
 *
 */
public class Controller {
    // OPCJE GRY
    public final static int PLAYER_COMPUTER = 0;
    public final static int COMPUTER_COMPUTER = 1;
    // WYNIK GRY
    public final static int GRACZ = 1;
    public final static int OPONENT = -1;
    public final static int REMIS = 0;
    public final static int BRAK = 2;
    // OGRANICZENIA
    private final static int MIN_SIZE = 3;
    private final static int MAX_SIZE = 30;
    private final static int MIN_LENGTH = 3;
    private final static int TREE_MIN_SIZE = 1;
    private final static int TREE_MAX_SIZE = 10;
	private View V;
	private Model M;
	int h;
	int w;
	int l;
	int t1;
	int t2;
	public Controller(View v, Model m) {V = v; M = m; h = 20; w = 20; l = 5; t1 = 5; t2 = 5;}
	public void start()
	{
		V.setController(this);
		M.setController(this);
		V.showMenu(h, w, l, t1, t2);
	}
	public int decreaseHeight() {return ((h == MIN_SIZE) || (h == l && w < l) ? h : --h);}
	public int increaseHeight() {return (h == MAX_SIZE ? h : ++h);}
	public int decreaseWidth() {return ((w == MIN_SIZE) || (w == l && h < l) ? w : --w);}
	public int increaseWidth() {return (w == MAX_SIZE ? w : ++w);}
	public int decreaseLength() {return (l == MIN_LENGTH ? l : --l);}
	public int increaseLength() {if ((l + 1) > h && (l + 1) > w) return l; else return ++l;}
	public int decreaseTreeFirst() {return (t1 == TREE_MIN_SIZE ? t1 : --t1);}
	public int increaseTreeFirst() {return (t1 == TREE_MAX_SIZE ? t1 : ++t1);}
	public int decreaseTreeSecond() {return (t2 == TREE_MIN_SIZE ? t2 : --t2);}
	public int increaseTreeSecond() {return (t2 == TREE_MAX_SIZE ? t2 : ++t2);}
	public void play(int option)
    {
        V.show(h, w);
        if (option == PLAYER_COMPUTER)
        {
            M.create(h, w, l, t1);
        }
        else
        {
            V.disableButtons();
            int wynik = M.play(h, w, l, t1, t2);
            V.showResult(wynik);
        }
    }
    public void pressed(int x, int y)
    {
        V.disableButtons();
        int wynik = M.add(x, y);
        if (wynik != BRAK) V.showResult(wynik);
        V.enableButtons();
    }
    public void showMenu()
    {
        V.showMenu(h, w, l, t1, t2);
    }
}
