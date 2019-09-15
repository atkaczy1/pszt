/**
 *
 */
package model;
import view.*;
import controller.*;
import minmax.*;

/**
 * @author Artur
 *
 */
public class Model {
	private View V;
	private Controller C;
	private int tree;
	private StanPlanszy stan;
	public Model(View v) {V = v;}
	public void setController(Controller c) {C = c;}
	public int play(int h, int w, int l, int t1, int t2)
	{
	    stan = new StanPlanszy(h, w, l);
	    while(true)
        {
            stan = (Model.StanPlanszy) MinMax.minmax(stan, t1);
            V.paint(stan.pokazTablice(), stan.czyjaRunda());
            if (stan.czyWygral() == StanPlanszy.KOLKO) return Controller.GRACZ;
            if (stan.czyRemis()) return Controller.REMIS;
            stan = (Model.StanPlanszy) MinMax.minmax(stan, t2);
            V.paint(stan.pokazTablice(), stan.czyjaRunda());
            if (stan.czyWygral() == StanPlanszy.KRZYZYK) return Controller.OPONENT;
            if (stan.czyRemis()) return Controller.REMIS;
        }
	}
	public void create(int h, int w, int l, int t1)
	{
        tree = t1;
        stan = new StanPlanszy(h, w, l);
	}
	public int add(int x, int y)
	{
	    stan.add(x, y);
	    V.paint(stan.pokazTablice(), stan.czyjaRunda());
	    if (stan.czyWygral() == StanPlanszy.KOLKO) return Controller.GRACZ;
	    if (stan.czyRemis()) return Controller.REMIS;
	    stan = (Model.StanPlanszy) MinMax.minmax(stan, tree);
	    V.paint(stan.pokazTablice(), stan.czyjaRunda());
	    if (stan.czyWygral() == StanPlanszy.KRZYZYK) return Controller.OPONENT;
	    if (stan.czyRemis()) return Controller.REMIS;
	    return Controller.BRAK;
	}
	public class StanPlanszy implements Stan, Cloneable
	{
	    private int height;
	    private int width;
	    private int length;
	    public static final int KOLKO = 0;
	    public static final int KRZYZYK = 1;
	    public static final int PUSTE = 2;
	    private int turn;
	    private int[][] plansza;
	    public StanPlanszy(int h, int w, int l)
	    {
	        height = h;
	        width = w;
	        length = l;
	        turn = KOLKO;
	        plansza = new int[h][];
	        for (int i = 0; i < h; i++)
                plansza[i] = new int[w];
            for (int i = 0; i < h; i++)
                for (int j = 0; j < w; j++)
                    plansza[i][j] = PUSTE;
	    }
	    public StanPlanszy clone() throws CloneNotSupportedException
	    {
	        StanPlanszy cloned = new StanPlanszy(height, width, length);
	        cloned.turn = turn;
	        for (int i = 0; i < plansza.length; i++)
            {
                for (int j = 0; j < plansza[i].length; j++)
                {
                    cloned.plansza[i][j] = plansza[i][j];
                }
            }
	        return cloned;
	    }
	    public boolean player()
	    {
	        return turn == KOLKO;
	    }
	    public boolean finalState()
	    {
	        return (czyRemis() || (czyWygral() != PUSTE));
	    }
	    public void add(int x, int y)
	    {
	        if (x >= height || x < 0) return;
	        if (y >= width || y < 0) return;
	        plansza[x][y] = turn;
	        if (turn == KOLKO) turn = KRZYZYK;
	        else turn = KOLKO;
	    }
	    public Stan[] stanyPotomne()
	    {
	        int ile = 0;
	        for (int i = 0; i < plansza.length; i++)
            {
                for (int j = 0; j < plansza[i].length; j++)
                {
                    if (plansza[i][j] == PUSTE) ile++;
                }
            }
            StanPlanszy[] stany = new StanPlanszy[ile];
            ile = 0;
            for (int i = 0; i < plansza.length; i++)
            {
                for (int j = 0; j < plansza[i].length; j++)
                {
                    if (plansza[i][j] == PUSTE)
                    {
                        try
                        {
                            stany[ile] = clone();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        stany[ile].add(i, j);
                        ile++;
                    }
                }
            }
            return stany;
	    }
	    public int funkcjaPrzystosowania()
	    {
	        int wynik = 0;
	        int znak = PUSTE;
	        int licznik = 0;
	        for (int i = 0; i < plansza.length; i++)
            {
                if (znak == KOLKO) wynik += (licznik * licznik);
                else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                znak = PUSTE;
                licznik = 0;
                for (int j = 0; j < plansza[i].length; j++)
                {
                    if (plansza[i][j] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            if (znak == KRZYZYK) wynik -= (licznik * licznik);
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[i][j] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            if (znak == KOLKO) wynik += (licznik * licznik);
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        if (znak == KOLKO) wynik += (licznik * licznik);
                        else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                        znak = PUSTE;
                        licznik = 0;
                    }
                }
            }
            for (int i = 0; i < plansza[0].length; i++)
            {
                if (znak == KOLKO) wynik += (licznik * licznik);
                else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                znak = PUSTE;
                licznik = 0;
                for (int j = 0; j < plansza.length; j++)
                {
                    if (plansza[j][i] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            if (znak == KRZYZYK) wynik -= (licznik * licznik);
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[j][i] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            if (znak == KOLKO) wynik += (licznik * licznik);
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        if (znak == KOLKO) wynik += (licznik * licznik);
                        else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                        znak = PUSTE;
                        licznik = 0;
                    }
                }
            }
            for (int i = 0; i < plansza[0].length; i++)
            {
                int x = 0;
                int y = i;
                if (znak == KOLKO) wynik += (licznik * licznik);
                else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                znak = PUSTE;
                licznik = 0;
                while (x < plansza.length && y < plansza[0].length)
                {
                    if (plansza[x][y] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            if (znak == KRZYZYK) wynik -= (licznik * licznik);
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[x][y] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            if (znak == KOLKO) wynik += (licznik * licznik);
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        if (znak == KOLKO) wynik += (licznik * licznik);
                        else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                        znak = PUSTE;
                        licznik = 0;
                    }
                    x++;
                    y++;
                }
            }
            for (int i = 0; i < plansza[0].length; i++)
            {
                int x = 0;
                int y = i;
                if (znak == KOLKO) wynik += (licznik * licznik);
                else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                znak = PUSTE;
                licznik = 0;
                while (x < plansza.length && y >= 0)
                {
                    if (plansza[x][y] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            if (znak == KRZYZYK) wynik -= (licznik * licznik);
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[x][y] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            if (znak == KOLKO) wynik += (licznik * licznik);
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        if (znak == KOLKO) wynik += (licznik * licznik);
                        else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                        znak = PUSTE;
                        licznik = 0;
                    }
                    x++;
                    y--;
                }
            }
            for (int i = 1; i < plansza.length; i++)
            {
                int x = i;
                int y = 0;
                if (znak == KOLKO) wynik += (licznik * licznik);
                else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                znak = PUSTE;
                licznik = 0;
                while (x < plansza.length && y < plansza[0].length)
                {
                    if (plansza[x][y] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            if (znak == KRZYZYK) wynik -= (licznik * licznik);
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[x][y] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            if (znak == KOLKO) wynik += (licznik * licznik);
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        if (znak == KOLKO) wynik += (licznik * licznik);
                        else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                        znak = PUSTE;
                        licznik = 0;
                    }
                    x++;
                    y++;
                }
            }
            for (int i = 1; i < plansza.length; i++)
            {
                int x = i;
                int y = plansza[0].length - 1;
                if (znak == KOLKO) wynik += (licznik * licznik);
                else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                znak = PUSTE;
                licznik = 0;
                while (x < plansza.length && y >= 0)
                {
                    if (plansza[x][y] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            if (znak == KRZYZYK) wynik -= (licznik * licznik);
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[x][y] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            if (znak == KOLKO) wynik += (licznik * licznik);
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        if (znak == KOLKO) wynik += (licznik * licznik);
                        else if (znak == KRZYZYK) wynik -= (licznik * licznik);
                        znak = PUSTE;
                        licznik = 0;
                    }
                    x++;
                    y--;
                }
            }
            if (znak == KOLKO) wynik += (licznik * licznik);
            else if (znak == KRZYZYK) wynik -= (licznik * licznik);
            return wynik;
	    }
	    public int[][] pokazTablice()
	    {
	        return plansza.clone();
	    }
	    public int czyjaRunda()
	    {
	        return turn;
	    }
	    public int czyWygral()
	    {
	        int znak;
	        int licznik;
	        for (int i = 0; i < plansza.length; i++)
            {
                znak = PUSTE;
                licznik = 0;
                for (int j = 0; j < plansza[i].length; j++)
                {
                    if (plansza[i][j] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[i][j] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        znak = PUSTE;
                        licznik = 0;
                    }
                    if (licznik == length) return znak;
                }
            }
            for (int i = 0; i < plansza[0].length; i++)
            {
                znak = PUSTE;
                licznik = 0;
                for (int j = 0; j < plansza.length; j++)
                {
                    if (plansza[j][i] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[j][i] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        znak = PUSTE;
                        licznik = 0;
                    }
                    if (licznik == length) return znak;
                }
            }
            for (int i = 0; i < plansza[0].length; i++)
            {
                int x = 0;
                int y = i;
                znak = PUSTE;
                licznik = 0;
                while (x < plansza.length && y < plansza[0].length)
                {
                    if (plansza[x][y] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[x][y] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        znak = PUSTE;
                        licznik = 0;
                    }
                    if (licznik == length) return znak;
                    x++;
                    y++;
                }
            }
            for (int i = 0; i < plansza[0].length; i++)
            {
                int x = 0;
                int y = i;
                znak = PUSTE;
                licznik = 0;
                while (x < plansza.length && y >= 0)
                {
                    if (plansza[x][y] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[x][y] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        znak = PUSTE;
                        licznik = 0;
                    }
                    if (licznik == length) return znak;
                    x++;
                    y--;
                }
            }
            for (int i = 1; i < plansza.length; i++)
            {
                int x = i;
                int y = 0;
                znak = PUSTE;
                licznik = 0;
                while (x < plansza.length && y < plansza[0].length)
                {
                    if (plansza[x][y] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[x][y] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        znak = PUSTE;
                        licznik = 0;
                    }
                    if (licznik == length) return znak;
                    x++;
                    y++;
                }
            }
            for (int i = 1; i < plansza.length; i++)
            {
                int x = i;
                int y = plansza[0].length - 1;
                znak = PUSTE;
                licznik = 0;
                while (x < plansza.length && y >= 0)
                {
                    if (plansza[x][y] == KOLKO)
                    {
                        if (znak == KOLKO) licznik++;
                        else
                        {
                            znak = KOLKO;
                            licznik = 1;
                        }
                    }
                    else if (plansza[x][y] == KRZYZYK)
                    {
                        if (znak == KRZYZYK) licznik++;
                        else
                        {
                            znak = KRZYZYK;
                            licznik = 1;
                        }
                    }
                    else
                    {
                        znak = PUSTE;
                        licznik = 0;
                    }
                    if (licznik == length) return znak;
                    x++;
                    y--;
                }
            }
            return PUSTE;
	    }
	    public boolean czyRemis()
	    {
	        for (int i = 0; i < plansza.length; i++)
            {
                for (int j = 0; j < plansza[i].length; j++)
                {
                    if (plansza[i][j] == PUSTE) return false;
                }
            }
            return true;
	    }
	}
}
