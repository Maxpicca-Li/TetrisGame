package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Block;
import model.BlockBase;
import model.GameData;

/**
* @author Maxpicca
* @version 创建时间：2020-12-11
* @Description 对战版中一个显示面板的实现
*/

public class OnePanel extends JPanel {

	private static final long serialVersionUID = 715089164709976646L;
	private GamePanel gamePanel;
	private GameData gameData;
	private static int width = GamePanel.COLS*BlockBase.BOX;
	private static int height = 40 + (GamePanel.ROWS+3)*BlockBase.BOX;
	private String idName="玩家";
	

	public OnePanel(GameData gamedata) {
		setLayout(null);
		gameData = gamedata;
		setGamePanel();
		setSize(width,height);
		// 设置背景透明
		this.setOpaque(false);
	}
	
	private void setGamePanel() {
		gamePanel = new GamePanel(this.gameData);
		gamePanel.setLocation(0, 2*BlockBase.BOX+10);
		this.add(gamePanel);
	}
	
	/**
	 *
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// 这一步必须加上,super...,否则repaint会出错.
		super.paintComponent(g);
		Font font = new Font("微软雅黑", Font.PLAIN, BlockBase.BOX*2/3);
		
		// 等级和分数
		g.setFont(font); 
		g.setColor(Color.black);
		g.drawString("等级："+gameData.level, 0, BlockBase.BOX);
		g.drawString("分数："+gameData.score, 0, BlockBase.BOX*2);
		
		// 玩家信息
		g.setColor(Color.blue);
		g.draw3DRect(10,(2+GamePanel.ROWS)*BlockBase.BOX+20 , width-25, BlockBase.BOX, true);
//		g.draw3DRect(0,height-BlockBase.BOX, width, BlockBase.BOX, true);
		g.drawString(idName, 4*BlockBase.BOX, (3+GamePanel.ROWS)*BlockBase.BOX+10);
		
		// 画当前画布
		gamePanel.repaint();
		
		// 画下一个方块信息
		// g.setColor(new Color(0,0,0,30));
		// g.drawRect(beginX, 0, 4*BlockBase.BOX, 2*BlockBase.BOX);
		Point[] ps=gameData.getNextPoints();
		g.setColor(gameData.getNextColor());
		for(Point p:ps) {
			int x = (Block.BEGIN_X+p.x)*BlockBase.BOX;
			int y = p.y*BlockBase.BOX;
			g.fill3DRect(x, y, BlockBase.BOX, BlockBase.BOX, true);
		}
		
	}
	
	public void setIdName(String idName) {
		this.idName = idName;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	/*public static void main(String[]args) {
		GameData gameData = new GameData();
		OnePanel onePanel = new OnePanel(gameData);
		JFrame mainFrame = new JFrame();
		onePanel.setLocation(0, 0);
		mainFrame.setLayout(null);
		mainFrame.add(onePanel);
		mainFrame.setSize(500,500);
		
		mainFrame.setVisible(true);
		mainFrame.setFocusable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/
}
