package minmax;
import java.util.*;

public class MinMax
{
    public static Stan minmax(Stan aktualny, int tree)
    {
        return alfabeta(aktualny, tree, Integer.MIN_VALUE, Integer.MAX_VALUE).stan;
    }
    private static pair alfabeta(Stan s, int d, int alfa, int beta)
    {
        Stan next = s;
        if (s.finalState() || d == 0) return new pair(s.funkcjaPrzystosowania(), s);
        if (s.player())
        {
            Stan[] sp = s.stanyPotomne();
            for (Stan i : sp)
            {
                pair result = alfabeta(i, d - 1, alfa, beta);
                if (result.value > alfa)
                {
                    alfa = result.value;
                    next = i;
                }
                if (alfa >= beta)
                {
                    return new pair(beta, next);
                }
            }
            return new pair(alfa, next);
        }
        else
        {
            Stan[] sp = s.stanyPotomne();
            for (Stan i : sp)
            {
                pair result = alfabeta(i, d - 1, alfa, beta);
                if (result.value < beta)
                {
                    beta = result.value;
                    next = i;
                }
                if (alfa >= beta)
                {
                    return new pair(alfa, next);
                }
            }
            return new pair(beta, next);
        }
    }
}
class pair
{
    public int value;
    public Stan stan;
    public pair(int v, Stan s) {value = v; stan = s;}
}
