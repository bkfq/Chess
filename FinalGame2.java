import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;
public class FinalGame2 extends Applet implements ActionListener
{
    Panel p_screen; //to hold all of the screens
    Panel screen1, screen2, screen3, screen4, screen5; //the two screens
    CardLayout cdLayout = new CardLayout ();
    JLabel title2;
    int stop1, stop2, stop3, stop4, stop5, stop6, stop7, stop8;
    int row = 8, col = 8, lastx = -1, lasty = -1, turn = 1;
    String bw[] [] = new String [row] [col], sh[] [] = new String [row] [col];
    int pieces[] [] = {{10, 8, 9, 11, 12, 9, 8, 10}, {7, 7, 7, 7, 7, 7, 7, 7}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1}, {4, 2, 3, 5, 6, 3, 2, 4}};
    //int pieces [0][0] = 10, pieces [0][1] = 8, pieces [0] [2] = 9, pieces [0] [3] = 11, pieces [0] [4] = 12, pieces [0] [5] = 9, pieces [0] [6] = 8, pieces [0] [7] = 10, pieces [1] [0] = 7, pieces [1] [1] = 7, pieces [1] [2] = 7, pieces [1] [3] = 7, pieces [1] [4] = 7, pieces [1] [5] = 7, pieces [1] [6] = 7, pieces [1] [7] = 7, pieces [6] [0] = 1, pieces [6] [1] = 1, pieces [6] [2] = 1, pieces [6] [3] = 1, pieces [6] [4] = 1, pieces [6] [5] = 1, pieces [6] [6] = 1, pieces [6] [7] = 1, pieces [7] [0] = 4, pieces [7] [1] = 2, pieces [7] [2] = 3, pieces [7] [3] = 5, pieces [7] [4] = 6, pieces [7] [5] = 2, pieces [7] [6] = 3, pieces [7] [7] = 4;
    JButton b[] = new JButton [row * col];
    int a[] [] = {{0, 0, 0, 0, 0}, {1, 0, 2, 0, 0}, {1, 0, 0, 2, 2}, {3, 3, 2, 0, 0}, {0, 2, 2, 0, 0}, {0, 3, 3, 0, 0}};

    public void init ()
    {
	p_screen = new Panel ();
	p_screen.setLayout (cdLayout);
	setup ();
	screen1 ();
	screen2 ();
	screen3 ();
	resize (800, 600);
	setLayout (new BorderLayout ());
	add ("Center", p_screen);
    }


    public void screen1 ()
    { //screen 1 is set up.
	screen1 = new Panel ();
	screen1.setBackground (Color.white);
	JLabel title = new JLabel ("Welcome to My Game!");
	JButton inst = new JButton ("Instructions");
	inst.setActionCommand ("s2");
	inst.addActionListener (this);
	JButton play = new JButton ("Play Game");
	play.setActionCommand ("s3");
	play.addActionListener (this);
	screen1.add (title);
	screen1.add (inst);
	screen1.add (play);
	p_screen.add ("1", screen1);
    }


    public void screen2 ()
    { //screen 2 is set up.
	screen2 = new Panel ();
	screen2.setBackground (Color.orange);
	JLabel title = new JLabel ("Instructions");
	JTextArea text = new JTextArea (40, 40);
	text.setBackground (Color.white);
	text.append ("This is the first instruction.\n");
	text.append ("And this is the second.\n");
	text.append ("And this is the third.\n");
	JButton next = new JButton ("Next");
	next.setActionCommand ("s3");
	next.addActionListener (this);
	screen2.add (title);
	screen2.add (text);
	screen2.add (next);
	p_screen.add ("2", screen2);
    }


    public void screen3 ()
    { //screen 3 is set up.
	screen3 = new Panel ();
	screen3.setBackground (Color.green);
	JLabel title = new JLabel ("Game Screen");
	title2 = new JLabel ("White's Turn");
	Panel p = new Panel (new GridLayout (row, col));
	for (int i = 0 ; i < row * col ; i++)
	{
	    b [i] = new JButton ("");
	    b [i].setPreferredSize (new Dimension (61, 61));
	    b [i].addActionListener (this);
	    b [i].setActionCommand ("" + i);
	    p.add (b [i]);
	}
	redraw ();
	JButton next = new JButton ("Instructions");
	next.setActionCommand ("s2");
	next.addActionListener (this);
	screen3.add (title);
	screen3.add (title2);
	screen3.add (p);
	screen3.add (next);
	p_screen.add ("3", screen3);
    }


    public void setup ()
    {
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		if ((i + j) % 2 == 0)
		    bw [i] [j] = "W";
		else
		    bw [i] [j] = "B";
		sh [i] [j] = "N";
	    }
	}
    }


    public void redraw ()
    {
	int m = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		b [m].setIcon (createImageIcon (sh [i] [j] + pieces [i] [j] + bw [i] [j] + ".jpg"));
		m++;
	    }
	}
    }


    public void actionPerformed (ActionEvent e)
    { //moves between the screens
	if (e.getActionCommand ().equals ("s1"))
	    cdLayout.show (p_screen, "1");
	else if (e.getActionCommand ().equals ("s2"))
	    cdLayout.show (p_screen, "2");
	else if (e.getActionCommand ().equals ("s3"))
	    cdLayout.show (p_screen, "3");
	else
	{
	    int pos = Integer.parseInt (e.getActionCommand ());
	    int x = pos / row;
	    int y = pos % row;
	    if (lastx == -1)
	    {
		if (turn == 1 && pieces [x] [y] != 0 && pieces [x] [y] < 7)
		{
		    lastx = x;
		    lasty = y;
		    showpos ();
		}
		if (turn == 0 && pieces [x] [y] > 6)
		{
		    lastx = x;
		    lasty = y;
		    showpos ();
		}
		else
		{
		}
	    }
	    else
	    {
		if (sh [x] [y].equals ("H"))
		{
		    pieces [x] [y] = pieces [lastx] [lasty];
		    pieces [lastx] [lasty] = 0;
		    if (turn == 1)
		    {
			turn = 0;
			title2.setText ("Black's Turn");
		    }
		    else
		    {
			turn = 1;
			title2.setText ("White's Turn");
		    }
		}
		for (int i = 0 ; i < row ; i++)
		{
		    for (int j = 0 ; j < col ; j++)
		    {
			sh [i] [j] = "N";
		    }
		}
		lastx = -1;
		redraw ();
	    }
	}
    }


    public void showpos ()
    {
	stop1 = 1;
	stop2 = 1;
	stop3 = 1;
	stop4 = 1;
	stop5 = 1;
	stop6 = 1;
	stop7 = 1;
	stop8 = 1;
	if (pieces [lastx] [lasty] == 1)
	{
	    if (lastx == 6 && pieces [lastx - 2] [lasty] == 0 && pieces [lastx - 1] [lasty] == 0)
		sh [lastx - 2] [lasty] = "H";
	    if (lastx - 1 >= 0 && pieces [lastx - 1] [lasty] == 0)
		sh [lastx - 1] [lasty] = "H";
	    if (lastx - 1 >= 0 && lasty - 1 >= 0 && pieces [lastx - 1] [lasty - 1] > 6)
		sh [lastx - 1] [lasty - 1] = "H";
	    if (lastx - 1 >= 0 && lasty + 1 < 8 && pieces [lastx - 1] [lasty + 1] > 6)
		sh [lastx - 1] [lasty + 1] = "H";
	}
	else if (pieces [lastx] [lasty] == 7)
	{
	    if (lastx == 1 && pieces [lastx + 2] [lasty] == 0 && pieces [lastx + 1] [lasty] == 0)
		sh [lastx + 2] [lasty] = "H";
	    if (lastx + 1 >= 0 && pieces [lastx + 1] [lasty] == 0)
		sh [lastx + 1] [lasty] = "H";
	    if (lastx + 1 < 8 && lasty - 1 >= 0 && pieces [lastx + 1] [lasty - 1] < 7 && pieces [lastx + 1] [lasty - 1] != 0)
		sh [lastx + 1] [lasty - 1] = "H";
	    if (lastx + 1 < 8 && lasty + 1 < 8 && pieces [lastx + 1] [lasty + 1] < 7 && pieces [lastx + 1] [lasty + 1] != 0)
		sh [lastx + 1] [lasty + 1] = "H";
	}
	else if (pieces [lastx] [lasty] == 2)
	{
	    if (lastx - 1 >= 0 && lasty - 2 >= 0 && (pieces [lastx - 1] [lasty - 2] == 0 || pieces [lastx - 1] [lasty - 2] > 6))
		sh [lastx - 1] [lasty - 2] = "H";
	    if (lastx - 1 >= 0 && lasty + 2 < 8 && (pieces [lastx - 1] [lasty + 2] == 0 || pieces [lastx - 1] [lasty + 2] > 6))
		sh [lastx - 1] [lasty + 2] = "H";
	    if (lastx + 1 < 8 && lasty - 2 >= 1 && (pieces [lastx + 1] [lasty - 2] == 0 || pieces [lastx + 1] [lasty - 2] > 6))
		sh [lastx + 1] [lasty - 2] = "H";
	    if (lastx + 1 < 8 && lasty + 2 < 8 && (pieces [lastx + 1] [lasty + 2] == 0 || pieces [lastx + 1] [lasty + 2] > 6))
		sh [lastx + 1] [lasty + 2] = "H";
	    if (lastx - 2 >= 0 && lasty - 1 >= 0 && (pieces [lastx - 2] [lasty - 1] == 0 || pieces [lastx - 2] [lasty - 1] > 6))
		sh [lastx - 2] [lasty - 1] = "H";
	    if (lastx - 2 >= 0 && lasty + 1 < 8 && (pieces [lastx - 2] [lasty + 1] == 0 || pieces [lastx - 2] [lasty + 1] > 6))
		sh [lastx - 2] [lasty + 1] = "H";
	    if (lastx + 2 < 8 && lasty - 1 >= 0 && (pieces [lastx + 2] [lasty - 1] == 0 || pieces [lastx + 2] [lasty - 1] > 6))
		sh [lastx + 2] [lasty - 1] = "H";
	    if (lastx + 2 < 8 && lasty + 1 < 8 && (pieces [lastx + 2] [lasty + 1] == 0 || pieces [lastx + 2] [lasty + 1] > 6))
		sh [lastx + 2] [lasty + 1] = "H";
	}
	else if (pieces [lastx] [lasty] == 8)
	{
	    if (lastx - 1 >= 0 && lasty - 2 >= 0 && (pieces [lastx - 1] [lasty - 2] == 0 || pieces [lastx - 1] [lasty - 2] < 7))
		sh [lastx - 1] [lasty - 2] = "H";
	    if (lastx - 1 >= 0 && lasty + 2 < 8 && (pieces [lastx - 1] [lasty + 2] == 0 || pieces [lastx - 1] [lasty + 2] < 7))
		sh [lastx - 1] [lasty + 2] = "H";
	    if (lastx + 1 < 8 && lasty - 2 >= 0 && (pieces [lastx + 1] [lasty - 2] == 0 || pieces [lastx + 1] [lasty - 2] < 7))
		sh [lastx + 1] [lasty - 2] = "H";
	    if (lastx + 1 < 8 && lasty + 2 < 8 && (pieces [lastx + 1] [lasty + 2] == 0 || pieces [lastx + 1] [lasty + 2] < 7))
		sh [lastx + 1] [lasty + 2] = "H";
	    if (lastx - 2 >= 0 && lasty - 1 >= 0 && (pieces [lastx - 2] [lasty - 1] == 0 || pieces [lastx - 2] [lasty - 1] < 7))
		sh [lastx - 2] [lasty - 1] = "H";
	    if (lastx - 2 >= 0 && lasty + 1 < 8 && (pieces [lastx - 2] [lasty + 1] == 0 || pieces [lastx - 2] [lasty + 1] < 7))
		sh [lastx - 2] [lasty + 1] = "H";
	    if (lastx + 2 < 8 && lasty - 1 >= 0 && (pieces [lastx + 2] [lasty - 1] == 0 || pieces [lastx + 2] [lasty - 1] < 7))
		sh [lastx + 2] [lasty - 1] = "H";
	    if (lastx + 2 < 8 && lasty + 1 < 8 && (pieces [lastx + 2] [lasty + 1] == 0 || pieces [lastx + 2] [lasty + 1] < 7))
		sh [lastx + 2] [lasty + 1] = "H";
	}
	else if (pieces [lastx] [lasty] == 3)
	{
	    for (int i = 1 ; i < row ; i++)
	    {
		if (lastx - i >= 0 && lasty - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty - i] == 0)
		    sh [lastx - i] [lasty - i] = "H";
		else if (lastx - i >= 0 && lasty - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty - i] > 6)
		{
		    sh [lastx - i] [lasty - i] = "H";
		    stop1 = 0;
		}
		else
		    stop1 = 0;
		if (lastx + i < 8 && lasty - i >= 0 && stop2 == 1 && pieces [lastx + i] [lasty - i] == 0)
		    sh [lastx + i] [lasty - i] = "H";
		else if (lastx + i < 8 && lasty - i >= 0 && stop2 == 1 && pieces [lastx + i] [lasty - i] > 6)
		{
		    sh [lastx + i] [lasty - i] = "H";
		    stop2 = 0;
		}
		else
		    stop2 = 0;
		if (lastx - i >= 0 && lasty + i < 8 && stop3 == 1 && pieces [lastx - i] [lasty + i] == 0)
		    sh [lastx - i] [lasty + i] = "H";
		else if (lastx - i >= 0 && lasty + i < 8 && stop3 == 1 && pieces [lastx - i] [lasty + i] > 6)
		{
		    sh [lastx - i] [lasty + i] = "H";
		    stop3 = 0;
		}
		else
		    stop3 = 0;
		if (lastx + i < 8 && lasty + i < 8 && stop4 == 1 && pieces [lastx + i] [lasty + i] == 0)
		    sh [lastx + i] [lasty + i] = "H";
		else if (lastx + i < 8 && lasty + i < 8 && stop4 == 1 && pieces [lastx + i] [lasty + i] > 6)
		{
		    sh [lastx + i] [lasty + i] = "H";
		    stop4 = 0;
		}
		else
		    stop4 = 0;
	    }
	}
	else if (pieces [lastx] [lasty] == 9)
	{

	    for (int i = 1 ; i < row ; i++)
	    {
		if (lastx - i >= 0 && lasty - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty - i] == 0)
		    sh [lastx - i] [lasty - i] = "H";
		else if (lastx - i >= 0 && lasty - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty - i] < 7)
		{
		    sh [lastx - i] [lasty - i] = "H";
		    stop1 = 0;
		}
		else
		    stop1 = 0;
		if (lastx + i < 8 && lasty - i >= 0 && stop2 == 1 && pieces [lastx + i] [lasty - i] == 0)
		    sh [lastx + i] [lasty - i] = "H";
		else if (lastx + i < 8 && lasty - i >= 0 && stop2 == 1 && pieces [lastx + i] [lasty - i] < 7)
		{
		    sh [lastx + i] [lasty - i] = "H";
		    stop2 = 0;
		}
		else
		    stop2 = 0;
		if (lastx - i >= 0 && lasty + i < 8 && stop3 == 1 && pieces [lastx - i] [lasty + i] == 0)
		    sh [lastx - i] [lasty + i] = "H";
		else if (lastx - i >= 0 && lasty + i < 8 && stop3 == 1 && pieces [lastx - i] [lasty + i] < 7)
		{
		    sh [lastx - i] [lasty + i] = "H";
		    stop3 = 0;
		}
		else
		    stop3 = 0;
		if (lastx + i < 8 && lasty + i < 8 && stop4 == 1 && pieces [lastx + i] [lasty + i] == 0)
		    sh [lastx + i] [lasty + i] = "H";
		else if (lastx + i < 8 && lasty + i < 8 && stop4 == 1 && pieces [lastx + i] [lasty + i] < 7)
		{
		    sh [lastx + i] [lasty + i] = "H";
		    stop4 = 0;
		}
		else
		    stop4 = 0;
	    }
	}
	else if (pieces [lastx] [lasty] == 4)
	{
	    for (int i = 1 ; i < row ; i++)
	    {
		if (lastx - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty] == 0)
		    sh [lastx - i] [lasty] = "H";
		else if (lastx - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty] > 6)
		{
		    sh [lastx - i] [lasty] = "H";
		    stop1 = 0;
		}
		else
		    stop1 = 0;
		if (lastx + i < 8 && stop2 == 1 && pieces [lastx + i] [lasty] == 0)
		    sh [lastx + i] [lasty] = "H";
		else if (lastx + i < 8 && stop2 == 1 && pieces [lastx + i] [lasty] > 6)
		{
		    sh [lastx + i] [lasty] = "H";
		    stop2 = 0;
		}
		else
		    stop2 = 0;
		if (lasty - i >= 0 && stop3 == 1 && pieces [lastx] [lasty - i] == 0)
		    sh [lastx] [lasty - i] = "H";
		else if (lasty - i >= 0 && stop3 == 1 && pieces [lastx] [lasty - i] > 6)
		{
		    sh [lastx] [lasty - i] = "H";
		    stop3 = 0;
		}
		else
		    stop3 = 0;
		if (lasty + i < 8 && stop4 == 1 && pieces [lastx] [lasty + i] == 0)
		    sh [lastx] [lasty + i] = "H";
		else if (lasty + i < 8 && stop4 == 1 && pieces [lastx] [lasty + i] > 6)
		{
		    sh [lastx] [lasty + i] = "H";
		    stop4 = 0;
		}
		else
		    stop4 = 0;
	    }
	}
	else if (pieces [lastx] [lasty] == 10)
	{
	    for (int i = 1 ; i < row ; i++)
	    {
		if (lastx - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty] == 0)
		    sh [lastx - i] [lasty] = "H";
		else if (lastx - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty] < 7)
		{
		    sh [lastx - i] [lasty] = "H";
		    stop1 = 0;
		}
		else
		    stop1 = 0;
		if (lastx + i < 8 && stop2 == 1 && pieces [lastx + i] [lasty] == 0)
		    sh [lastx + i] [lasty] = "H";
		else if (lastx + i < 8 && stop2 == 1 && pieces [lastx + i] [lasty] < 7)
		{
		    sh [lastx + i] [lasty] = "H";
		    stop2 = 0;
		}
		else
		    stop2 = 0;
		if (lasty - i >= 0 && stop3 == 1 && pieces [lastx] [lasty - i] == 0)
		    sh [lastx] [lasty - i] = "H";
		else if (lasty - i >= 0 && stop3 == 1 && pieces [lastx] [lasty - i] < 7)
		{
		    sh [lastx] [lasty - i] = "H";
		    stop3 = 0;
		}
		else
		    stop3 = 0;
		if (lasty + i < 8 && stop4 == 1 && pieces [lastx] [lasty + i] == 0)
		    sh [lastx] [lasty + i] = "H";
		else if (lasty + i < 8 && stop4 == 1 && pieces [lastx] [lasty + i] < 7)
		{
		    sh [lastx] [lasty + i] = "H";
		    stop4 = 0;
		}
		else
		    stop4 = 0;
	    }
	}
	else if (pieces [lastx] [lasty] == 5)
	{
	    stop1 = 1;
	    for (int i = 1 ; i < row ; i++)
	    {
		if (lastx - i >= 0 && lasty - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty - i] == 0)
		    sh [lastx - i] [lasty - i] = "H";
		else if (lastx - i >= 0 && lasty - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty - i] > 6)
		{
		    sh [lastx - i] [lasty - i] = "H";
		    stop1 = 0;
		}
		else
		    stop1 = 0;
		if (lastx + i < 8 && lasty - i >= 0 && stop2 == 1 && pieces [lastx + i] [lasty - i] == 0)
		    sh [lastx + i] [lasty - i] = "H";
		else if (lastx + i < 8 && lasty - i >= 0 && stop2 == 1 && pieces [lastx + i] [lasty - i] > 6)
		{
		    sh [lastx + i] [lasty - i] = "H";
		    stop2 = 0;
		}
		else
		    stop2 = 0;
		if (lastx - i >= 0 && lasty + i < 8 && stop3 == 1 && pieces [lastx - i] [lasty + i] == 0)
		    sh [lastx - i] [lasty + i] = "H";
		else if (lastx - i >= 0 && lasty + i < 8 && stop3 == 1 && pieces [lastx - i] [lasty + i] > 6)
		{
		    sh [lastx - i] [lasty + i] = "H";
		    stop3 = 0;
		}
		else
		    stop3 = 0;
		if (lastx + i < 8 && lasty + i < 8 && stop4 == 1 && pieces [lastx + i] [lasty + i] == 0)
		    sh [lastx + i] [lasty + i] = "H";
		else if (lastx + i < 8 && lasty + i < 8 && stop4 == 1 && pieces [lastx + i] [lasty + i] > 6)
		{
		    sh [lastx + i] [lasty + i] = "H";
		    stop4 = 0;
		}
		else
		    stop4 = 0;
		if (lastx - i >= 0 && stop5 == 1 && pieces [lastx - i] [lasty] == 0)
		    sh [lastx - i] [lasty] = "H";
		else if (lastx - i >= 0 && stop5 == 1 && pieces [lastx - i] [lasty] > 6)
		{
		    sh [lastx - i] [lasty] = "H";
		    stop5 = 0;
		}
		else
		    stop5 = 0;
		if (lastx + i < 8 && stop6 == 1 && pieces [lastx + i] [lasty] == 0)
		    sh [lastx + i] [lasty] = "H";
		else if (lastx + i < 8 && stop6 == 1 && pieces [lastx + i] [lasty] > 6)
		{
		    sh [lastx + i] [lasty] = "H";
		    stop6 = 0;
		}
		else
		    stop6 = 0;
		if (lasty - i >= 0 && stop7 == 1 && pieces [lastx] [lasty - i] == 0)
		    sh [lastx] [lasty - i] = "H";
		else if (lasty - i >= 0 && stop7 == 1 && pieces [lastx] [lasty - i] > 6)
		{
		    sh [lastx] [lasty - i] = "H";
		    stop7 = 0;
		}
		else
		    stop7 = 0;
		if (lasty + i < 8 && stop8 == 1 && pieces [lastx] [lasty + i] == 0)
		    sh [lastx] [lasty + i] = "H";
		else if (lasty + i < 8 && stop8 == 1 && pieces [lastx] [lasty + i] > 6)
		{
		    sh [lastx] [lasty + i] = "H";
		    stop8 = 0;
		}
		else
		    stop8 = 0;
	    }
	}
	else if (pieces [lastx] [lasty] == 11)
	{
	    for (int i = 1 ; i < row ; i++)
	    {
		if (lastx - i >= 0 && lasty - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty - i] == 0)
		    sh [lastx - i] [lasty - i] = "H";
		else if (lastx - i >= 0 && lasty - i >= 0 && stop1 == 1 && pieces [lastx - i] [lasty - i] < 7)
		{
		    sh [lastx - i] [lasty - i] = "H";
		    stop1 = 0;
		}
		else
		    stop1 = 0;
		if (lastx + i < 8 && lasty - i >= 0 && stop2 == 1 && pieces [lastx + i] [lasty - i] == 0)
		    sh [lastx + i] [lasty - i] = "H";
		else if (lastx + i < 8 && lasty - i >= 0 && stop2 == 1 && pieces [lastx + i] [lasty - i] < 7)
		{
		    sh [lastx + i] [lasty - i] = "H";
		    stop2 = 0;
		}
		else
		    stop2 = 0;
		if (lastx - i >= 0 && lasty + i < 8 && stop3 == 1 && pieces [lastx - i] [lasty + i] == 0)
		    sh [lastx - i] [lasty + i] = "H";
		else if (lastx - i >= 0 && lasty + i < 8 && stop3 == 1 && pieces [lastx - i] [lasty + i] < 7)
		{
		    sh [lastx - i] [lasty + i] = "H";
		    stop3 = 0;
		}
		else
		    stop3 = 0;
		if (lastx + i < 8 && lasty + i < 8 && stop4 == 1 && pieces [lastx + i] [lasty + i] == 0)
		    sh [lastx + i] [lasty + i] = "H";
		else if (lastx + i < 8 && lasty + i < 8 && stop4 == 1 && pieces [lastx + i] [lasty + i] < 7)
		{
		    sh [lastx + i] [lasty + i] = "H";
		    stop4 = 0;
		}
		else
		    stop4 = 0;
		if (lastx - i >= 0 && stop5 == 1 && pieces [lastx - i] [lasty] == 0)
		    sh [lastx - i] [lasty] = "H";
		else if (lastx - i >= 0 && stop5 == 1 && pieces [lastx - i] [lasty] < 7)
		{
		    sh [lastx - i] [lasty] = "H";
		    stop5 = 0;
		}
		else
		    stop5 = 0;
		if (lastx + i < 8 && stop6 == 1 && pieces [lastx + i] [lasty] == 0)
		    sh [lastx + i] [lasty] = "H";
		else if (lastx + i < 8 && stop6 == 1 && pieces [lastx + i] [lasty] < 7)
		{
		    sh [lastx + i] [lasty] = "H";
		    stop6 = 0;
		}
		else
		    stop6 = 0;
		if (lasty - i >= 0 && stop7 == 1 && pieces [lastx] [lasty - i] == 0)
		    sh [lastx] [lasty - i] = "H";
		else if (lasty - i >= 0 && stop7 == 1 && pieces [lastx] [lasty - i] < 7)
		{
		    sh [lastx] [lasty - i] = "H";
		    stop7 = 0;
		}
		else
		    stop7 = 0;
		if (lasty + i < 8 && stop8 == 1 && pieces [lastx] [lasty + i] == 0)
		    sh [lastx] [lasty + i] = "H";
		else if (lasty + i < 8 && stop8 == 1 && pieces [lastx] [lasty + i] < 7)
		{
		    sh [lastx] [lasty + i] = "H";
		    stop8 = 0;
		}
		else
		    stop8 = 0;
	    }
	}
	else if (pieces [lastx] [lasty] == 6)
	{
	    if (lastx - 1 >= 0 && lasty - 1 >= 0 && (pieces [lastx - 1] [lasty - 1] == 0 || pieces [lastx - 1] [lasty - 1] > 6))
		sh [lastx - 1] [lasty - 1] = "H";
	    if (lastx + 1 < 8 && lasty - 1 >= 0 && (pieces [lastx + 1] [lasty - 1] == 0 || pieces [lastx + 1] [lasty - 1] > 6))
		sh [lastx + 1] [lasty - 1] = "H";
	    if (lastx - 1 >= 0 && lasty + 1 < 8 && (pieces [lastx - 1] [lasty + 1] == 0 || pieces [lastx - 1] [lasty + 1] > 6))
		sh [lastx - 1] [lasty + 1] = "H";
	    if (lastx + 1 < 8 && lasty + 1 < 8 && (pieces [lastx + 1] [lasty + 1] == 0 || pieces [lastx + 1] [lasty + 1] > 6))
		sh [lastx + 1] [lasty + 1] = "H";
	    if (lastx - 1 >= 0 && (pieces [lastx - 1] [lasty] == 0 || pieces [lastx - 1] [lasty] > 6))
		sh [lastx - 1] [lasty] = "H";
	    if (lastx + 1 < 8 && (pieces [lastx + 1] [lasty] == 0 || pieces [lastx + 1] [lasty] > 6))
		sh [lastx + 1] [lasty] = "H";
	    if (lasty - 1 >= 0 && (pieces [lastx] [lasty - 1] == 0 || pieces [lastx] [lasty - 1] > 6))
		sh [lastx] [lasty - 1] = "H";
	    if (lasty + 1 < 8 && (pieces [lastx] [lasty + 1] == 0 || pieces [lastx] [lasty + 1] > 6))
		sh [lastx] [lasty + 1] = "H";
	}
	else if (pieces [lastx] [lasty] == 12)
	{
	    if (lastx - 1 >= 0 && lasty - 1 >= 0 && (pieces [lastx - 1] [lasty - 1] == 0 || pieces [lastx - 1] [lasty - 1] < 7))
		sh [lastx - 1] [lasty - 1] = "H";
	    if (lastx + 1 < 8 && lasty - 1 >= 0 && (pieces [lastx + 1] [lasty - 1] == 0 || pieces [lastx + 1] [lasty - 1] < 7))
		sh [lastx + 1] [lasty - 1] = "H";
	    if (lastx - 1 >= 0 && lasty + 1 < 8 && (pieces [lastx - 1] [lasty + 1] == 0 || pieces [lastx - 1] [lasty + 1] < 7))
		sh [lastx - 1] [lasty + 1] = "H";
	    if (lastx + 1 < 8 && lasty + 1 < 8 && (pieces [lastx + 1] [lasty + 1] == 0 || pieces [lastx + 1] [lasty + 1] < 7))
		sh [lastx + 1] [lasty + 1] = "H";
	    if (lastx - 1 >= 0 && (pieces [lastx - 1] [lasty] == 0 || pieces [lastx - 1] [lasty] < 7))
		sh [lastx - 1] [lasty] = "H";
	    if (lastx + 1 < 8 && (pieces [lastx + 1] [lasty] == 0 || pieces [lastx + 1] [lasty] < 7))
		sh [lastx + 1] [lasty] = "H";
	    if (lasty - 1 >= 0 && (pieces [lastx] [lasty - 1] == 0 || pieces [lastx] [lasty - 1] < 7))
		sh [lastx] [lasty - 1] = "H";
	    if (lasty + 1 < 8 && (pieces [lastx] [lasty + 1] == 0 || pieces [lastx] [lasty + 1] < 7))
		sh [lastx] [lasty + 1] = "H";
	}
	char isValid = 'Y';
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		if (sh [i] [j] == "H")
		    isValid = 'N';
	    }
	}
	if (isValid == 'Y')
	    lastx = -1;
	else
	    redraw ();
    }


    protected static ImageIcon createImageIcon (String path)
    {
	java.net.URL imgURL = FinalGame.class.getResource (path);
	if (imgURL != null)
	{
	    return new ImageIcon (imgURL);
	}
	else
	{
	    System.err.println ("Couldn't find file: " + path);
	    return null;
	}
    }
}


