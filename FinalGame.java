import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;
public class FinalGame extends Applet implements ActionListener
{
    Panel p_screen; //to hold all of the screens
    Panel screen1, screen2, screen3, screen4, screen5; //the two screens
    CardLayout cdLayout = new CardLayout ();
    JLabel title2;
    JButton undo;
    int row = 8, col = 8, lastx = -1, lasty = -1, stop = 1, stop1 = 1, whiteCheck, blackCheck, tempx, tempy, x, y, tempx2, temp, temp2, WcastleK, WcastleQ, BcastleK, BcastleQ, EnPassant, EPtemp;
    String turn = "White";
    String bw[] [] = new String [row] [col], sh[] [] = new String [row] [col];
    char wC[] [] = new char [8] [8], bC[] [] = new char [8] [8];
    int pieces[] [] = {{10, 8, 9, 11, 12, 9, 8, 10}, {7, 7, 7, 7, 7, 7, 7, 7}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1}, {4, 2, 3, 5, 6, 3, 2, 4}};
    int pieces2[] [] = {{10, 8, 9, 11, 12, 9, 8, 10}, {7, 7, 7, 7, 7, 7, 7, 7}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1}, {4, 2, 3, 5, 6, 3, 2, 4}};
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
	JButton reset = new JButton ("Reset Board");
	reset.setActionCommand ("reset");
	reset.addActionListener (this);
	undo = new JButton ("Undo");
	undo.setVisible (false);
	undo.setEnabled (false);
	undo.setActionCommand ("undo");
	undo.addActionListener (this);
	Panel p2 = new Panel (new GridLayout (3, 1));
	p2.add (next);
	p2.add (reset);
	p2.add (undo);
	screen3.add (title);
	screen3.add (title2);
	screen3.add (p);
	screen3.add (p2);
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
	setup2 ();
    }


    public void setup2 ()
    {
	for (int i = 0 ; i < row ; i++)
	    for (int j = 0 ; j < col ; j++)
		sh [i] [j] = "N";
    }


    public void setup3 ()
    {

	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		if (turn.equals ("White"))
		    wC [i] [j] = 'N';
		else
		    bC [i] [j] = 'N';
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


    public void reset ()
    {
	setup ();
	setup2 ();
	setup3 ();
	for (int i = 0 ; i < row ; i++)
	    for (int j = 0 ; j < col ; j++)
		pieces [i] [j] = pieces2 [i] [j];
	turn = "White";
	undo.setVisible (false);
	undo.setEnabled (false);
	WcastleQ = 0;
	WcastleK = 0;
	BcastleQ = 0;
	BcastleK = 0;
	redraw ();
	lastx = -1;
    }


    public void actionPerformed (ActionEvent e)
    { //moves between the screens
	if (e.getActionCommand ().equals ("s1"))
	    cdLayout.show (p_screen, "1");
	else if (e.getActionCommand ().equals ("s2"))
	    cdLayout.show (p_screen, "2");
	else if (e.getActionCommand ().equals ("s3"))
	    cdLayout.show (p_screen, "3");
	else if (e.getActionCommand ().equals ("undo"))
	{
	    if (pieces [x] [y] == 6 || pieces [x] [y] == 12)
	    {
		if (pieces [x] [y] == 6)
		{
		    WcastleQ = 0;
		    WcastleK = 0;
		}
		else if (pieces [x] [y] == 12)
		{
		    BcastleQ = 0;
		    BcastleK = 0;
		}
		if ((lasty - y) == 2)
		{
		    pieces [x] [y - 2] = pieces [x] [y + 1];
		    pieces [x] [y + 1] = 0;
		}
		else if ((lasty - y) == -2)
		{
		    pieces [x] [y + 1] = pieces [x] [y - 1];
		    pieces [x] [y - 1] = 0;
		}
	    }
	    if (pieces [x] [y] == 1 && EnPassant == 1 && y == EPtemp && x == 2)
		pieces [3] [EPtemp] = 7;
	    else if (pieces [x] [y] == 7 && EnPassant == 1 && y == EPtemp && x == 5)
		pieces [4] [EPtemp] = 1;
	    if ((temp == 1 && x == 0) || (temp == 7 && x == 7))
		pieces [tempx2] [lasty] = temp;
	    else
		pieces [tempx2] [lasty] = pieces [x] [y];
	    pieces [x] [y] = temp2;
	    if (turn.equals ("White"))
		turn = "Black";
	    else
		turn = "White";
	    undo.setVisible (false);
	    undo.setEnabled (false);
	    redraw ();
	}
	else if (e.getActionCommand ().equals ("reset"))
	    reset ();
	else
	{
	    int pos = Integer.parseInt (e.getActionCommand ());
	    x = pos / row;
	    y = pos % row;
	    if (lastx == -1)
	    {
		if (turn.equals ("White") && pieces [x] [y] != 0 && pieces [x] [y] < 7)
		{
		    lastx = x;
		    lasty = y;
		    validMove (x, y);
		    if (canMove ())
		    {
			lastx = -1;
			noMove ();
		    }
		    else
			redraw ();
		    isCheck ();
		}
		else if (turn.equals ("Black") && pieces [x] [y] > 6)
		{
		    lastx = x;
		    lasty = y;
		    validMove (x, y);
		    if (canMove ())
		    {
			lastx = -1;
			noMove ();
		    }
		    else
			redraw ();
		    isCheck ();
		}
		else
		{
		    wrongTurn (turn);
		}
		undo.setVisible (false);
		undo.setEnabled (false);
	    }
	    else
	    {
		if (sh [x] [y].equals ("H"))
		{
		    castleStuff ();
		    EnPassantStuff ();
		    temp2 = pieces [x] [y];
		    pieces [x] [y] = pieces [lastx] [lasty];
		    temp = pieces [lastx] [lasty];
		    pieces [lastx] [lasty] = 0;
		    if ((pieces [x] [y] == 1 && x == 0) || (pieces [x] [y] == 7 && x == 7))
			promotionPawn ();
		    if (turn.equals ("White"))
			turn = "Black";
		    else
			turn = "White";
		    undo.setVisible (true);
		    undo.setEnabled (true);
		    isCheckMate ();
		    isStalemate ();
		    title2.setText (turn + "'s Turn");
		}
		else
		    invalidMove ();
		setup2 ();
		tempx2 = lastx;
		lastx = -1;
		redraw ();
	    }
	}
    }


    public void castleStuff ()
    {
	if (pieces [lastx] [lasty] == 4)
	{
	    if (lasty == 0)
		WcastleQ = 1;
	    else if (lasty == 7)
		WcastleK = 1;
	}
	if (pieces [lastx] [lasty] == 10)
	{
	    if (lasty == 0)
		BcastleQ = 1;
	    else if (lasty == 7)
		BcastleK = 1;
	}
	if (pieces [lastx] [lasty] == 6)
	{
	    if ((lasty - y) == 2)
	    {
		pieces [x] [y + 1] = pieces [x] [y - 2];
		pieces [x] [y - 2] = 0;
	    }
	    else if ((lasty - y) == -2)
	    {
		pieces [x] [y - 1] = pieces [x] [y + 1];
		pieces [x] [y + 1] = 0;
	    }
	    WcastleQ = 1;
	    WcastleK = 1;
	}
	if (pieces [lastx] [lasty] == 12)
	{
	    if ((lasty - y) == 2)
	    {
		pieces [x] [y + 1] = pieces [x] [y - 2];
		pieces [x] [y - 2] = 0;
	    }
	    else if ((lasty - y) == -2)
	    {
		pieces [x] [y - 1] = pieces [x] [y + 1];
		pieces [x] [y + 1] = 0;
	    }
	    BcastleQ = 1;
	    BcastleK = 1;
	}
    }


    public void EnPassantStuff ()
    {
	if (pieces [lastx] [lasty] == 1 && EnPassant == 1 && y == EPtemp && x == 2)
	    pieces [x + 1] [EPtemp] = 0;
	else if (pieces [lastx] [lasty] == 7 && EnPassant == 1 && y == EPtemp && x == 5)
	    pieces [x - 1] [EPtemp] = 0;
	else
	{
	    if (Math.abs (x - lastx) == 2 && (pieces [lastx] [lasty] == 1 || pieces [lastx] [lasty] == 7))
	    {
		EnPassant = 1;
		EPtemp = lasty;
	    }
	    else
		EnPassant = 0;
	}
    }


    public void promotionPawn ()
    {
	String input = JOptionPane.showInputDialog ("What do you wish to promote to?  (Queen, Bishop, Knight, or Rook)");
	if (turn.equals ("White"))
	{
	    if (input.equalsIgnoreCase ("Knight"))
		pieces [x] [y] = 2;
	    else if (input.equalsIgnoreCase ("Bishop"))
		pieces [x] [y] = 3;
	    else if (input.equalsIgnoreCase ("Rook"))
		pieces [x] [y] = 4;
	    else if (input.equalsIgnoreCase ("Queen"))
		pieces [x] [y] = 5;
	    else
		promotionPawn ();
	}
	else
	{
	    if (input.equalsIgnoreCase ("Knight"))
		pieces [x] [y] = 8;
	    else if (input.equalsIgnoreCase ("Bishop"))
		pieces [x] [y] = 9;
	    else if (input.equalsIgnoreCase ("Rook"))
		pieces [x] [y] = 10;
	    else if (input.equalsIgnoreCase ("Queen"))
		pieces [x] [y] = 11;
	    else
		promotionPawn ();
	}
    }


    public void isStalemate ()
    {
	if (!moveable ())
	{
	    int num = stalemate ();
	    if (num == 0)
		reset ();
	    else
		System.exit (0);
	}
    }


    public void isCheck ()
    {
	setup3 ();
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		if (pieces [i] [j] == 1)
		    whitePawnCheck (i, j);
		else if (pieces [i] [j] == 2)
		    whiteKnightCheck (i, j);
		else if (pieces [i] [j] == 3)
		    whiteBishopCheck (i, j);
		else if (pieces [i] [j] == 4)
		    whiteRookCheck (i, j);
		else if (pieces [i] [j] == 5)
		{
		    whiteBishopCheck (i, j);
		    whiteRookCheck (i, j);
		}
		else if (pieces [i] [j] == 7)
		    blackPawnCheck (i, j);
		else if (pieces [i] [j] == 8)
		    blackKnightCheck (i, j);
		else if (pieces [i] [j] == 9)
		    blackBishopCheck (i, j);
		else if (pieces [i] [j] == 10)
		    blackRookCheck (i, j);
		else if (pieces [i] [j] == 11)
		{
		    blackBishopCheck (i, j);
		    blackRookCheck (i, j);
		}
		else if (pieces [i] [j] == 6)
		{
		    whiteKingCheck (i, j);
		    if (turn.equals ("White"))
		    {
			tempx = i;
			tempy = j;
		    }
		}
		else if (pieces [i] [j] == 12)
		{
		    blackKingCheck (i, j);
		    if (turn.equals ("Black"))
		    {
			tempx = i;
			tempy = j;
		    }
		}
	    }
	}
    }


    public void isCheckMate ()
    {
	isCheck ();
	if (turn.equals ("White") && wC [tempx] [tempy] == 'C')
	{
	    if (tempx - 1 >= 0 && tempy - 1 >= 0 && (wC [tempx - 1] [tempy - 1] != 'C' && (pieces [tempx - 1] [tempy - 1] == 0 || pieces [tempx - 1] [tempy - 1] > 6)))
		check ();
	    else if (tempx - 1 >= 0 && (wC [tempx - 1] [tempy] != 'C' && (pieces [tempx - 1] [tempy] == 0 || pieces [tempx - 1] [tempy] > 6)))
		check ();
	    else if (tempx - 1 >= 0 && tempy + 1 < 8 && (wC [tempx - 1] [tempy + 1] != 'C' && (pieces [tempx - 1] [tempy + 1] == 0 || pieces [tempx - 1] [tempy + 1] > 6)))
		check ();
	    else if (tempy - 1 >= 0 && (wC [tempx] [tempy - 1] != 'C' && (pieces [tempx] [tempy - 1] == 0 || pieces [tempx] [tempy - 1] > 6)))
		check ();
	    else if (tempy + 1 < 8 && (wC [tempx] [tempy + 1] != 'C' && (pieces [tempx] [tempy + 1] == 0 || pieces [tempx] [tempy + 1] > 6)))
		check ();
	    else if (tempx + 1 < 8 && tempy - 1 >= 0 && (wC [tempx + 1] [tempy - 1] != 'C' && (pieces [tempx + 1] [tempy - 1] == 0 || pieces [tempx + 1] [tempy - 1] > 6)))
		check ();
	    else if (tempx + 1 < 8 && (wC [tempx + 1] [tempy] != 'C' && (pieces [tempx + 1] [tempy] == 0 || pieces [tempx + 1] [tempy] > 6)))
		check ();
	    else if (tempx + 1 < 8 && tempy + 1 < 8 && (wC [tempx + 1] [tempy + 1] != 'C' && (pieces [tempx + 1] [tempy + 1] == 0 || pieces [tempx + 1] [tempy + 1] > 6)))
		check ();
	    else if (moveable ())
		check ();
	    else
	    {
		if (turn.equals ("White"))
		    turn = "Black";
		else
		    turn = "White";
		int num = checkMate ();
		if (num == 1)
		    System.exit (0);
		else
		    reset ();
	    }
	}
	else if (turn.equals ("Black") && bC [tempx] [tempy] == 'C')
	{
	    if (tempx - 1 >= 0 && tempy - 1 >= 0 && (bC [tempx - 1] [tempy - 1] != 'C' && (pieces [tempx - 1] [tempy - 1] == 0 || pieces [tempx - 1] [tempy - 1] < 7)))
		check ();
	    else if (tempx - 1 >= 0 && (bC [tempx - 1] [tempy] != 'C' && (pieces [tempx - 1] [tempy] == 0 || pieces [tempx - 1] [tempy] < 7)))
		check ();
	    else if (tempx - 1 >= 0 && tempy + 1 < 8 && (bC [tempx - 1] [tempy + 1] != 'C' && (pieces [tempx - 1] [tempy + 1] == 0 || pieces [tempx - 1] [tempy + 1] < 7)))
		check ();
	    else if (tempy - 1 >= 0 && (bC [tempx] [tempy - 1] != 'C' && (pieces [tempx] [tempy - 1] == 0 || pieces [tempx] [tempy - 1] < 7)))
		check ();
	    else if (tempy + 1 < 8 && (bC [tempx] [tempy + 1] != 'C' && (pieces [tempx] [tempy + 1] == 0 || pieces [tempx] [tempy + 1] < 7)))
		check ();
	    else if (tempx + 1 < 8 && tempy - 1 >= 0 && (bC [tempx + 1] [tempy - 1] != 'C' && (pieces [tempx + 1] [tempy - 1] == 0 || pieces [tempx + 1] [tempy - 1] < 7)))
		check ();
	    else if (tempx + 1 < 8 && (bC [tempx + 1] [tempy] != 'C' && (pieces [tempx + 1] [tempy] == 0 || pieces [tempx + 1] [tempy] < 7)))
		check ();
	    else if (tempx + 1 < 8 && tempy + 1 < 8 && (bC [tempx + 1] [tempy + 1] != 'C' && (pieces [tempx + 1] [tempy + 1] == 0 || pieces [tempx + 1] [tempy + 1] < 7)))
		check ();
	    else if (moveable ())
		check ();
	    else
	    {
		if (turn.equals ("White"))
		    turn = "Black";
		else
		    turn = "White";
		int num = checkMate ();
		if (num == 1)
		    System.exit (0);
		else
		    reset ();
	    }
	}
    }


    public boolean moveable ()
    {
	int temporaryX = lastx;
	int temporaryY = lasty;
	boolean check = false;
	for (int i = 0 ; i < row ; i++)
	    for (int j = 0 ; j < col ; j++)
	    {
		lastx = i;
		lasty = j;
		if (pieces [i] [j] != 0 && pieces [i] [j] < 7 && turn.equals ("White") || (pieces [i] [j] > 6 && turn.equals ("Black")))
		{
		    validMove (i, j);
		    if (!canMove ())
			check = true;
		}
	    }
	lastx = temporaryX;
	lasty = temporaryY;
	return check;
    }


    public void showpos ()
    {
	setup2 ();
	if (pieces [lastx] [lasty] == 1)
	    whitePawn ();
	else if (pieces [lastx] [lasty] == 7)
	    blackPawn ();
	else if (pieces [lastx] [lasty] == 2)
	    whiteKnight ();
	else if (pieces [lastx] [lasty] == 8)
	    blackKnight ();
	else if (pieces [lastx] [lasty] == 3)
	    whiteBishop ();
	else if (pieces [lastx] [lasty] == 9)
	    blackBishop ();
	else if (pieces [lastx] [lasty] == 4)
	    whiteRook ();
	else if (pieces [lastx] [lasty] == 10)
	    blackRook ();
	else if (pieces [lastx] [lasty] == 5)
	{
	    whiteRook ();
	    whiteBishop ();
	}
	else if (pieces [lastx] [lasty] == 11)
	{
	    blackRook ();
	    blackBishop ();
	}
	else if (pieces [lastx] [lasty] == 6)
	    whiteKing ();
	else if (pieces [lastx] [lasty] == 12)
	    blackKing ();
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


    public boolean canMove ()
    {
	boolean isValid = true;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		if (sh [i] [j] == "H")
		    isValid = false;
	    }
	}
	return isValid;
    }


    public void validMove (int x2, int y2)
    {
	showpos ();
	int temporary = pieces [x2] [y2];
	int temporary2 = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		if (sh [i] [j] == "H")
		{
		    temporary2 = pieces [i] [j];
		    pieces [i] [j] = temporary;
		    pieces [x2] [y2] = 0;
		    isCheck ();
		    if (turn.equals ("White") && wC [tempx] [tempy] == 'C')
			sh [i] [j] = "N";
		    if (turn.equals ("Black") && bC [tempx] [tempy] == 'C')
			sh [i] [j] = "N";
		    pieces [i] [j] = temporary2;
		    pieces [x2] [y2] = temporary;
		}
	    }
	}
    }


    public int stalemate ()
    {
	int input2 = JOptionPane.showConfirmDialog (null, "It is a stalemate so it is a draw.\nDo you want to play the game again?",
		"It is a stalemate!", JOptionPane.YES_NO_OPTION);
	return input2;
    }


    public void invalidMove ()
    {
	JOptionPane.showMessageDialog (null, "That is an invalid move.", "alert",
		JOptionPane.ERROR_MESSAGE);
    }


    public void wrongTurn (String turn)
    {
	JOptionPane.showMessageDialog (null, "Select one of " + turn + "'s pieces!", "alert",
		JOptionPane.ERROR_MESSAGE);
    }


    public void noMove ()
    {
	JOptionPane.showMessageDialog (null, "That piece can not move.", "alert",
		JOptionPane.ERROR_MESSAGE);
    }


    public void inCheck ()
    {
	JOptionPane.showMessageDialog (null, "If you move that piece the king would be in check!", "alert",
		JOptionPane.ERROR_MESSAGE);
    }


    public void check ()
    {
	JOptionPane.showMessageDialog (null, "Check!", "alert",
		JOptionPane.ERROR_MESSAGE);
    }


    public int checkMate ()
    {
	int input2 = JOptionPane.showConfirmDialog (null, turn + " wins! \nDo you want to play the game again?",
		"Checkmate!", JOptionPane.YES_NO_OPTION);
	return input2;
    }


    public void blackPawnCheck (int xSpot, int ySpot)
    {
	if (xSpot + 1 < 8 && ySpot - 1 >= 0)
	    wC [xSpot + 1] [ySpot - 1] = 'C';
	if (xSpot + 1 < 8 && ySpot + 1 < 8)
	    wC [xSpot + 1] [ySpot + 1] = 'C';
    }


    public void whitePawnCheck (int xSpot, int ySpot)
    {
	if (xSpot - 1 >= 0 && ySpot - 1 >= 0)
	    bC [xSpot - 1] [ySpot - 1] = 'C';
	if (xSpot - 1 >= 0 && ySpot + 1 < 8)
	    bC [xSpot - 1] [ySpot + 1] = 'C';
    }


    public void blackKnightCheck (int xSpot, int ySpot)
    {
	if (xSpot - 1 >= 0 && ySpot - 2 >= 0)
	    wC [xSpot - 1] [ySpot - 2] = 'C';
	if (xSpot - 1 >= 0 && ySpot + 2 < 8)
	    wC [xSpot - 1] [ySpot + 2] = 'C';
	if (xSpot + 1 < 8 && ySpot - 2 >= 0)
	    wC [xSpot + 1] [ySpot - 2] = 'C';
	if (xSpot + 1 < 8 && ySpot + 2 < 8)
	    wC [xSpot + 1] [ySpot + 2] = 'C';
	if (xSpot - 2 >= 0 && ySpot - 1 >= 0)
	    wC [xSpot - 2] [ySpot - 1] = 'C';
	if (xSpot - 2 >= 0 && ySpot + 1 < 8)
	    wC [xSpot - 2] [ySpot + 1] = 'C';
	if (xSpot + 2 < 8 && ySpot - 1 >= 0)
	    wC [xSpot + 2] [ySpot - 1] = 'C';
	if (xSpot + 2 < 8 && ySpot + 1 < 8)
	    wC [xSpot + 2] [ySpot + 1] = 'C';
    }


    public void whiteKnightCheck (int xSpot, int ySpot)
    {
	if (xSpot - 1 >= 0 && ySpot - 2 >= 0)
	    bC [xSpot - 1] [ySpot - 2] = 'C';
	if (xSpot - 1 >= 0 && ySpot + 2 < 8)
	    bC [xSpot - 1] [ySpot + 2] = 'C';
	if (xSpot + 1 < 8 && ySpot - 2 >= 0)
	    bC [xSpot + 1] [ySpot - 2] = 'C';
	if (xSpot + 1 < 8 && ySpot + 2 < 8)
	    bC [xSpot + 1] [ySpot + 2] = 'C';
	if (xSpot - 2 >= 0 && ySpot - 1 >= 0)
	    bC [xSpot - 2] [ySpot - 1] = 'C';
	if (xSpot - 2 >= 0 && ySpot + 1 < 8)
	    bC [xSpot - 2] [ySpot + 1] = 'C';
	if (xSpot + 2 < 8 && ySpot - 1 >= 0)
	    bC [xSpot + 2] [ySpot - 1] = 'C';
	if (xSpot + 2 < 8 && ySpot + 1 < 8)
	    bC [xSpot + 2] [ySpot + 1] = 'C';
    }


    public void blackBishopCheck (int xSpot, int ySpot)
    {
	while (stop1 != 0)
	{
	    if (xSpot - stop >= 0 && ySpot - stop >= 0 && pieces [xSpot - stop] [ySpot - stop] == 0)
		wC [xSpot - stop] [ySpot - stop] = 'C';
	    else if (xSpot - stop >= 0 && ySpot - stop >= 0 && pieces [xSpot - stop] [ySpot - stop] < 7)
	    {
		wC [xSpot - stop] [ySpot - stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}
	softReset ();
	while (stop1 != 0)
	{
	    if (xSpot + stop < 8 && ySpot - stop >= 0 && pieces [xSpot + stop] [ySpot - stop] == 0)
		wC [xSpot + stop] [ySpot - stop] = 'C';
	    else if (xSpot + stop < 8 && ySpot - stop >= 0 && pieces [xSpot + stop] [ySpot - stop] < 7)
	    {
		wC [xSpot + stop] [ySpot - stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (xSpot - stop >= 0 && ySpot + stop < 8 && pieces [xSpot - stop] [ySpot + stop] == 0)
		wC [xSpot - stop] [ySpot + stop] = 'C';
	    else if (xSpot - stop >= 0 && ySpot + stop < 8 && pieces [xSpot - stop] [ySpot + stop] < 7)
	    {
		wC [xSpot - stop] [ySpot + stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (xSpot + stop < 8 && ySpot + stop < 8 && pieces [xSpot + stop] [ySpot + stop] == 0)
		wC [xSpot + stop] [ySpot + stop] = 'C';
	    else if (xSpot + stop < 8 && ySpot + stop < 8 && pieces [xSpot + stop] [ySpot + stop] < 7)
	    {
		wC [xSpot + stop] [ySpot + stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
    }


    public void whiteBishopCheck (int xSpot, int ySpot)
    {
	while (stop1 != 0)
	{
	    if (xSpot - stop >= 0 && ySpot - stop >= 0 && pieces [xSpot - stop] [ySpot - stop] == 0)
		bC [xSpot - stop] [ySpot - stop] = 'C';
	    else if (xSpot - stop >= 0 && ySpot - stop >= 0 && pieces [xSpot - stop] [ySpot - stop] > 6)
	    {
		bC [xSpot - stop] [ySpot - stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (xSpot + stop < 8 && ySpot - stop >= 0 && pieces [xSpot + stop] [ySpot - stop] == 0)
		bC [xSpot + stop] [ySpot - stop] = 'C';
	    else if (xSpot + stop < 8 && ySpot - stop >= 0 && pieces [xSpot + stop] [ySpot - stop] > 6)
	    {
		bC [xSpot + stop] [ySpot - stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (xSpot - stop >= 0 && ySpot + stop < 8 && pieces [xSpot - stop] [ySpot + stop] == 0)
		bC [xSpot - stop] [ySpot + stop] = 'C';
	    else if (xSpot - stop >= 0 && ySpot + stop < 8 && pieces [xSpot - stop] [ySpot + stop] > 6)
	    {
		bC [xSpot - stop] [ySpot + stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (xSpot + stop < 8 && ySpot + stop < 8 && pieces [xSpot + stop] [ySpot + stop] == 0)
		bC [xSpot + stop] [ySpot + stop] = 'C';
	    else if (xSpot + stop < 8 && ySpot + stop < 8 && pieces [xSpot + stop] [ySpot + stop] > 6)
	    {
		bC [xSpot + stop] [ySpot + stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
    }


    public void blackRookCheck (int xSpot, int ySpot)
    {
	while (stop1 != 0)
	{
	    if (xSpot - stop >= 0 && pieces [xSpot - stop] [ySpot] == 0)
		wC [xSpot - stop] [ySpot] = 'C';
	    else if (xSpot - stop >= 0 && pieces [xSpot - stop] [ySpot] < 7)
	    {
		wC [xSpot - stop] [ySpot] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (xSpot + stop < 8 && pieces [xSpot + stop] [ySpot] == 0)
		wC [xSpot + stop] [ySpot] = 'C';
	    else if (xSpot + stop < 8 && pieces [xSpot + stop] [ySpot] < 7)
	    {
		wC [xSpot + stop] [ySpot] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (ySpot - stop >= 0 && pieces [xSpot] [ySpot - stop] == 0)
		wC [xSpot] [ySpot - stop] = 'C';
	    else if (ySpot - stop >= 0 && pieces [xSpot] [ySpot - stop] < 7)
	    {
		wC [xSpot] [ySpot - stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (ySpot + stop < 8 && pieces [xSpot] [ySpot + stop] == 0)
		wC [xSpot] [ySpot + stop] = 'C';
	    else if (ySpot + stop < 8 && pieces [xSpot] [ySpot + stop] < 7)
	    {
		wC [xSpot] [ySpot + stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
    }


    public void whiteRookCheck (int xSpot, int ySpot)
    {
	while (stop1 != 0)
	{
	    if (xSpot - stop >= 0 && pieces [xSpot - stop] [ySpot] == 0)
		bC [xSpot - stop] [ySpot] = 'C';
	    else if (xSpot - stop >= 0 && pieces [xSpot - stop] [ySpot] > 6)
	    {
		bC [xSpot - stop] [ySpot] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (xSpot + stop < 8 && pieces [xSpot + stop] [ySpot] == 0)
		bC [xSpot + stop] [ySpot] = 'C';
	    else if (xSpot + stop < 8 && pieces [xSpot + stop] [ySpot] > 6)
	    {
		bC [xSpot + stop] [ySpot] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}
	softReset ();
	while (stop1 != 0)
	{
	    if (ySpot - stop >= 0 && pieces [xSpot] [ySpot - stop] == 0)
		bC [xSpot] [ySpot - stop] = 'C';
	    else if (ySpot - stop >= 0 && pieces [xSpot] [ySpot - stop] > 6)
	    {
		bC [xSpot] [ySpot - stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}
	softReset ();
	while (stop1 != 0)
	{
	    if (ySpot + stop < 8 && pieces [xSpot] [ySpot + stop] == 0)
		bC [xSpot] [ySpot + stop] = 'C';
	    else if (ySpot + stop < 8 && pieces [xSpot] [ySpot + stop] > 6)
	    {
		bC [xSpot] [ySpot + stop] = 'C';
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}
	softReset ();
    }


    public void blackKingCheck (int xSpot, int ySpot)
    {
	if (xSpot - 1 >= 0 && ySpot - 1 >= 0)
	    wC [xSpot - 1] [ySpot - 1] = 'C';
	if (xSpot + 1 < 8 && ySpot - 1 >= 0)
	    wC [xSpot + 1] [ySpot - 1] = 'C';
	if (xSpot - 1 >= 0 && ySpot + 1 < 8)
	    wC [xSpot - 1] [ySpot + 1] = 'C';
	if (xSpot + 1 < 8 && ySpot + 1 < 8)
	    wC [xSpot + 1] [ySpot + 1] = 'C';
	if (xSpot - 1 >= 0)
	    wC [xSpot - 1] [ySpot] = 'C';
	if (xSpot + 1 < 8)
	    wC [xSpot + 1] [ySpot] = 'C';
	if (ySpot - 1 >= 0)
	    wC [xSpot] [ySpot - 1] = 'C';
	if (ySpot + 1 < 8)
	    wC [xSpot] [ySpot + 1] = 'C';
    }


    public void whiteKingCheck (int xSpot, int ySpot)
    {
	if (xSpot - 1 >= 0 && ySpot - 1 >= 0)
	    bC [xSpot - 1] [ySpot - 1] = 'C';
	if (xSpot + 1 < 8 && ySpot - 1 >= 0)
	    bC [xSpot + 1] [ySpot - 1] = 'C';
	if (xSpot - 1 >= 0 && ySpot + 1 < 8)
	    bC [xSpot - 1] [ySpot + 1] = 'C';
	if (xSpot + 1 < 8 && ySpot + 1 < 8)
	    bC [xSpot + 1] [ySpot + 1] = 'C';
	if (xSpot - 1 >= 0)
	    bC [xSpot - 1] [ySpot] = 'C';
	if (xSpot + 1 < 8)
	    bC [xSpot + 1] [ySpot] = 'C';
	if (ySpot - 1 >= 0)
	    bC [xSpot] [ySpot - 1] = 'C';
	if (ySpot + 1 < 8)
	    bC [xSpot] [ySpot + 1] = 'C';
    }


    public void blackPawn ()
    {
	if (lastx == 1 && pieces [lastx + 2] [lasty] == 0 && pieces [lastx + 1] [lasty] == 0)
	    sh [lastx + 2] [lasty] = "H";
	if (lastx + 1 >= 0 && pieces [lastx + 1] [lasty] == 0)
	    sh [lastx + 1] [lasty] = "H";
	if (lastx + 1 < 8 && lasty - 1 >= 0 && pieces [lastx + 1] [lasty - 1] < 7 && pieces [lastx + 1] [lasty - 1] != 0)
	    sh [lastx + 1] [lasty - 1] = "H";
	if (lastx + 1 < 8 && lasty + 1 < 8 && pieces [lastx + 1] [lasty + 1] < 7 && pieces [lastx + 1] [lasty + 1] != 0)
	    sh [lastx + 1] [lasty + 1] = "H";
	if (lastx == 4 && lasty - 1 >= 0 && lasty - 1 == EPtemp)
	    sh [lastx + 1] [lasty - 1] = "H";
	if (lastx == 4 && lasty + 1 < 8 && lasty + 1 == EPtemp)
	    sh [lastx + 1] [lasty + 1] = "H";
    }


    public void whitePawn ()
    {
	if (lastx == 6 && pieces [lastx - 2] [lasty] == 0 && pieces [lastx - 1] [lasty] == 0)
	    sh [lastx - 2] [lasty] = "H";
	if (lastx - 1 >= 0 && pieces [lastx - 1] [lasty] == 0)
	    sh [lastx - 1] [lasty] = "H";
	if (lastx - 1 >= 0 && lasty - 1 >= 0 && pieces [lastx - 1] [lasty - 1] > 6)
	    sh [lastx - 1] [lasty - 1] = "H";
	if (lastx - 1 >= 0 && lasty + 1 < 8 && pieces [lastx - 1] [lasty + 1] > 6)
	    sh [lastx - 1] [lasty + 1] = "H";
	if (lastx == 3 && lasty - 1 >= 0 && lasty - 1 == EPtemp)
	    sh [lastx - 1] [lasty - 1] = "H";
	if (lastx == 3 && lasty + 1 < 8 && lasty + 1 == EPtemp)
	    sh [lastx - 1] [lasty + 1] = "H";
    }


    public void blackKnight ()
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


    public void whiteKnight ()
    {
	if (lastx - 1 >= 0 && lasty - 2 >= 0 && (pieces [lastx - 1] [lasty - 2] == 0 || pieces [lastx - 1] [lasty - 2] > 6))
	    sh [lastx - 1] [lasty - 2] = "H";
	if (lastx - 1 >= 0 && lasty + 2 < 8 && (pieces [lastx - 1] [lasty + 2] == 0 || pieces [lastx - 1] [lasty + 2] > 6))
	    sh [lastx - 1] [lasty + 2] = "H";
	if (lastx + 1 < 8 && lasty - 2 >= 0 && (pieces [lastx + 1] [lasty - 2] == 0 || pieces [lastx + 1] [lasty - 2] > 6))
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


    public void blackBishop ()
    {
	while (stop1 != 0)
	{
	    if (lastx - stop >= 0 && lasty - stop >= 0 && pieces [lastx - stop] [lasty - stop] == 0)
		sh [lastx - stop] [lasty - stop] = "H";
	    else if (lastx - stop >= 0 && lasty - stop >= 0 && pieces [lastx - stop] [lasty - stop] < 7)
	    {
		sh [lastx - stop] [lasty - stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lastx + stop < 8 && lasty - stop >= 0 && pieces [lastx + stop] [lasty - stop] == 0)
		sh [lastx + stop] [lasty - stop] = "H";
	    else if (lastx + stop < 8 && lasty - stop >= 0 && pieces [lastx + stop] [lasty - stop] < 7)
	    {
		sh [lastx + stop] [lasty - stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lastx - stop >= 0 && lasty + stop < 8 && pieces [lastx - stop] [lasty + stop] == 0)
		sh [lastx - stop] [lasty + stop] = "H";
	    else if (lastx - stop >= 0 && lasty + stop < 8 && pieces [lastx - stop] [lasty + stop] < 7)
	    {
		sh [lastx - stop] [lasty + stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lastx + stop < 8 && lasty + stop < 8 && pieces [lastx + stop] [lasty + stop] == 0)
		sh [lastx + stop] [lasty + stop] = "H";
	    else if (lastx + stop < 8 && lasty + stop < 8 && pieces [lastx + stop] [lasty + stop] < 7)
	    {
		sh [lastx + stop] [lasty + stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
    }


    public void whiteBishop ()
    {
	while (stop1 != 0)
	{
	    if (lastx - stop >= 0 && lasty - stop >= 0 && pieces [lastx - stop] [lasty - stop] == 0)
		sh [lastx - stop] [lasty - stop] = "H";
	    else if (lastx - stop >= 0 && lasty - stop >= 0 && pieces [lastx - stop] [lasty - stop] > 6)
	    {
		sh [lastx - stop] [lasty - stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lastx + stop < 8 && lasty - stop >= 0 && pieces [lastx + stop] [lasty - stop] == 0)
		sh [lastx + stop] [lasty - stop] = "H";
	    else if (lastx + stop < 8 && lasty - stop >= 0 && pieces [lastx + stop] [lasty - stop] > 6)
	    {
		sh [lastx + stop] [lasty - stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lastx - stop >= 0 && lasty + stop < 8 && pieces [lastx - stop] [lasty + stop] == 0)
		sh [lastx - stop] [lasty + stop] = "H";
	    else if (lastx - stop >= 0 && lasty + stop < 8 && pieces [lastx - stop] [lasty + stop] > 6)
	    {
		sh [lastx - stop] [lasty + stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lastx + stop < 8 && lasty + stop < 8 && pieces [lastx + stop] [lasty + stop] == 0)
		sh [lastx + stop] [lasty + stop] = "H";
	    else if (lastx + stop < 8 && lasty + stop < 8 && pieces [lastx + stop] [lasty + stop] > 6)
	    {
		sh [lastx + stop] [lasty + stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
    }


    public void blackRook ()
    {
	while (stop1 != 0)
	{
	    if (lastx - stop >= 0 && pieces [lastx - stop] [lasty] == 0)
		sh [lastx - stop] [lasty] = "H";
	    else if (lastx - stop >= 0 && pieces [lastx - stop] [lasty] < 7)
	    {
		sh [lastx - stop] [lasty] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lastx + stop < 8 && pieces [lastx + stop] [lasty] == 0)
		sh [lastx + stop] [lasty] = "H";
	    else if (lastx + stop < 8 && pieces [lastx + stop] [lasty] < 7)
	    {
		sh [lastx + stop] [lasty] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lasty - stop >= 0 && pieces [lastx] [lasty - stop] == 0)
		sh [lastx] [lasty - stop] = "H";
	    else if (lasty - stop >= 0 && pieces [lastx] [lasty - stop] < 7)
	    {
		sh [lastx] [lasty - stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lasty + stop < 8 && pieces [lastx] [lasty + stop] == 0)
		sh [lastx] [lasty + stop] = "H";
	    else if (lasty + stop < 8 && pieces [lastx] [lasty + stop] < 7)
	    {
		sh [lastx] [lasty + stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
    }


    public void whiteRook ()
    {
	while (stop1 != 0)
	{
	    if (lastx - stop >= 0 && pieces [lastx - stop] [lasty] == 0)
		sh [lastx - stop] [lasty] = "H";
	    else if (lastx - stop >= 0 && pieces [lastx - stop] [lasty] > 6)
	    {
		sh [lastx - stop] [lasty] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lastx + stop < 8 && pieces [lastx + stop] [lasty] == 0)
		sh [lastx + stop] [lasty] = "H";
	    else if (lastx + stop < 8 && pieces [lastx + stop] [lasty] > 6)
	    {
		sh [lastx + stop] [lasty] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lasty - stop >= 0 && pieces [lastx] [lasty - stop] == 0)
		sh [lastx] [lasty - stop] = "H";
	    else if (lasty - stop >= 0 && pieces [lastx] [lasty - stop] > 6)
	    {
		sh [lastx] [lasty - stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
	while (stop1 != 0)
	{
	    if (lasty + stop < 8 && pieces [lastx] [lasty + stop] == 0)
		sh [lastx] [lasty + stop] = "H";
	    else if (lasty + stop < 8 && pieces [lastx] [lasty + stop] > 6)
	    {
		sh [lastx] [lasty + stop] = "H";
		stop1 = 0;
	    }
	    else
		stop1 = 0;
	    stop++;
	}


	softReset ();
    }


    public void blackKing ()
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
	if (BcastleK != 1)
	{
	    isCheck ();
	    if (bC [lastx] [lasty] != 'C' && bC [lastx] [lasty + 1] != 'C' && bC [lastx] [lasty + 2] != 'C' && pieces [lastx] [lasty + 1] == 0 && pieces [lastx] [lasty + 2] == 0)
		sh [lastx] [lasty + 2] = "H";
	}
	if (BcastleQ != 1)
	{
	    isCheck ();
	    if (bC [lastx] [lasty] != 'C' && bC [lastx] [lasty - 1] != 'C' && bC [lastx] [lasty - 2] != 'C' && pieces [lastx] [lasty - 1] == 0 && pieces [lastx] [lasty - 2] == 0 && pieces [lastx] [lasty - 3] == 0)
		sh [lastx] [lasty - 2] = "H";
	}
    }


    public void whiteKing ()
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
	if (WcastleK != 1)
	{
	    isCheck ();
	    if (wC [lastx] [lasty] != 'C' && wC [lastx] [lasty + 1] != 'C' && wC [lastx] [lasty + 2] != 'C' && pieces [lastx] [lasty + 1] == 0 && pieces [lastx] [lasty + 2] == 0)
		sh [lastx] [lasty + 2] = "H";
	}
	if (WcastleQ != 1)
	{
	    isCheck ();
	    if (wC [lastx] [lasty] != 'C' && wC [lastx] [lasty - 1] != 'C' && wC [lastx] [lasty - 2] != 'C' && pieces [lastx] [lasty - 1] == 0 && pieces [lastx] [lasty - 2] == 0 && pieces [lastx] [lasty - 3] == 0)
		sh [lastx] [lasty - 2] = "H";
	}
    }


    public void softReset ()
    {
	stop = 1;
	stop1 = 1;
    }
}


