/**
 *
 */
package view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.*;
import model.*;

import javax.swing.JFrame;

/**
 * @author Artur
 *
 */
public class View extends JFrame {
	private Controller C;
	private Menu menu;
	private Plansza plansza;
	private JPanel res;
	public View()
	{
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Kolko i krzyzyk");
		menu = new Menu();
	}
	public void setController(Controller c) {C = c;}
	public void showMenu(int h, int w, int l, int t1, int t2)
	{
	    menu.setValues(h, w, l, t1, t2);
		add(menu);
		setVisible(true);
	}
	public void show(int h, int w)
	{
	    setVisible(false);
	    remove(menu);
	    plansza = new Plansza(h, w);
	    add(plansza);
	    setSize(1000, 600);
	    setVisible(true);
	}
	public void disableButtons() {plansza.disable();}
	public void enableButtons() {plansza.enable();}
	public void paint(int[][] p, int turn)
	{
	    plansza.paint(p, turn);
	}
	public void showResult(int result)
	{
	    setVisible(false);
	    remove(plansza);
	    res = new JPanel();
	    res.setLayout(new BorderLayout());
	    JLabel l;
	    if (result == Controller.GRACZ) l = new JLabel("WYGRALES");
	    else if (result == Controller.OPONENT) l = new JLabel("PRZEGRALES");
	    else l = new JLabel("REMIS");
	    res.add(l, BorderLayout.CENTER);
	    JButton b = new JButton("Wroc do menu");
	    b.addActionListener(new ActionListener()
                         {
                            public void actionPerformed(ActionEvent event)
                            {
                                setVisible(false);
                                remove(res);
                                C.showMenu();
                            }
                         });
        res.add(new JPanel().add(b), BorderLayout.SOUTH);
        setSize(400, 600);
        add(res);
        setVisible(true);
	}
	private class Menu extends JPanel
	{
        private ButtonGroup group;
        private JRadioButton gk;
        private JRadioButton kk;
	    private JLabel height;
        private JLabel width;
        private JLabel length;
        private JLabel tree1;
        private JLabel tree2;
        public Menu()
        {
            setLayout(new GridLayout(8, 1));
            group = new ButtonGroup();
            JPanel p1 = new JPanel();
            p1.add(new JLabel("Kolko i krzyzyk"));
            JPanel p2 = new JPanel();
            p2.add(new JLabel("Wysokosc: "));
            p2.add(height = new JLabel());
            JButton button1 = new JButton("-");
            JButton button2 = new JButton("+");
            button1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent event)
                        {
                            int result = C.decreaseHeight();
                            height.setText("" + result);
                        }
                    });
            button2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                        int result = C.increaseHeight();
                        height.setText("" + result);
                    }
                });
            p2.add(button1);
            p2.add(button2);
            JPanel p3 = new JPanel();
            p3.add(new JLabel("Szerokosc: "));
            p3.add(width = new JLabel());
            button1 = new JButton("-");
            button2 = new JButton("+");
            button1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent event)
                        {
                            int result = C.decreaseWidth();
                            width.setText("" + result);
                        }
                    });
            button2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                        int result = C.increaseWidth();
                        width.setText("" + result);
                    }
                });
            p3.add(button1);
            p3.add(button2);
            JPanel p4 = new JPanel();
            p4.add(new JLabel("Dlugosc: "));
            p4.add(length = new JLabel());
            button1 = new JButton("-");
            button2 = new JButton("+");
            button1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent event)
                        {
                            int result = C.decreaseLength();
                            length.setText("" + result);
                        }
                    });
            button2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                        int result = C.increaseLength();
                        length.setText("" + result);
                    }
                });
            p4.add(button1);
            p4.add(button2);
            JPanel p5 = new JPanel();
            p5.add(new JLabel("Ilosc stopni drzewa(1): "));
            p5.add(tree1 = new JLabel());
            button1 = new JButton("-");
            button2 = new JButton("+");
            button1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent event)
                        {
                            int result = C.decreaseTreeFirst();
                            tree1.setText("" + result);
                        }
                    });
            button2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                        int result = C.increaseTreeFirst();
                        tree1.setText("" + result);
                    }
                });
            p5.add(button1);
            p5.add(button2);
            JPanel p6 = new JPanel();
            p6.add(new JLabel("Ilosc stopni drzewa(2): "));
            p6.add(tree2 = new JLabel());
            button1 = new JButton("-");
            button2 = new JButton("+");
            button1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent event)
                        {
                            int result = C.decreaseTreeSecond();
                            tree2.setText("" + result);
                        }
                    });
            button2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                        int result = C.increaseTreeSecond();
                        tree2.setText("" + result);
                    }
                });
            p6.add(button1);
            p6.add(button2);
            JPanel p7 = new JPanel();
            gk = new JRadioButton("Gracz-Komputer", true);
            group.add(gk);
            kk = new JRadioButton("Komputer-Komputer", false);
            group.add(kk);
            p7.add(gk);
            p7.add(kk);
            JPanel p8 = new JPanel();
            button1 = new JButton("Uruchom");
            button1.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                        if (gk.isSelected()) C.play(Controller.PLAYER_COMPUTER);
                        else C.play(Controller.COMPUTER_COMPUTER);
                    }
                });
            p8.add(button1);
            add(p1);
            add(p2);
            add(p3);
            add(p4);
            add(p5);
            add(p6);
            add(p7);
            add(p8);
        }
        public void setValues(int h, int w, int l, int t1, int t2)
        {
            height.setText("" + h);
            width.setText("" + w);
            length.setText("" + l);
            tree1.setText("" + t1);
            tree2.setText("" + t2);
        }
	}
	private class Plansza extends JPanel
	{
	    private JButton[][] buttons;
	    private JButton next;
	    public Plansza(int h, int w)
	    {
	        setLayout(new BorderLayout());
	        JPanel p1 = new JPanel();
	        JPanel p2 = new JPanel();
	        next = new JButton("O");
	        next.setEnabled(false);
	        p2.add(next);
	        add(p2, BorderLayout.NORTH);
	        p1.setLayout(new GridLayout(h, w));
	        buttons = new JButton[h][];
	        for (int i = 0; i < h; i++) buttons[i] = new JButton[w];
	        for (int i = 0; i < h; i++)
            {
                for (int j = 0; j < w; j++)
                {
                    buttons[i][j] = new JButton(" ");
                    final int I = i;
                    final int J = j;
                    buttons[i][j].addActionListener(new ActionListener()
                                                    {
                                                        public void actionPerformed(ActionEvent event)
                                                        {
                                                            C.pressed(I, J);
                                                        }
                                                    });
                    p1.add(buttons[i][j]);
                }
            }
            add(p1, BorderLayout.CENTER);
	    }
	    public void disable()
	    {
	        for (JButton[] i : buttons)
                for (JButton j : i)
                {
                    j.setEnabled(false);
                }
	    }
	    public void enable()
	    {
	        for (JButton[] i : buttons)
                for (JButton j : i)
                {
                    if (j.getText().equals(" "))j.setEnabled(true);
                }
	    }
	    public void paint(int[][] p, int turn)
	    {
	        for (int i = 0; i < p.length; i++)
                for (int j = 0; j < p[i].length; j++)
                {
                    if (p[i][j] == Model.StanPlanszy.KOLKO) buttons[i][j].setText("O");
                    else if (p[i][j] == Model.StanPlanszy.KRZYZYK) buttons[i][j].setText("X");
                    else buttons[i][j].setText(" ");
                }
            if (turn == Model.StanPlanszy.KOLKO) next.setText("O");
            else next.setText("X");
	    }
	}
}
