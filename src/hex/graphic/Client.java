package hex.graphic;

import hex.Board;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Client
{
	private JFrame frame;
	private Panel panel;
	public Client(Board board)
	{
		panel=new Panel();
		panel.setBoard(board);
		double [] c=panel.coordinate(board.getSize()-1,board.getSize()-1);
		c[0]+= Panel.size *Math.sqrt(3)/2+ Panel.gap;
		c[1]+= Panel.size + Panel.gap;
		panel.setBounds(0,0,(int)Math.round(c[0]),(int)Math.round(c[1]));
		frame=new JFrame("Hex");
		frame.setLayout(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension((int)Math.round(c[0]),(int)Math.round(c[1])));
		frame.pack();
		frame.setVisible(true);
		frame.add(panel);

	}
	public void repaint()
	{
		panel.repaint();
	}

}
