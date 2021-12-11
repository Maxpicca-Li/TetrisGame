package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import model.Block;
import model.BlockBase;
import model.GameData;

/**
* @author Maxpicca
* @version 创建时间：2020-11-29
* @Description 单机版右边提示栏
*/

public class RightPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5585644259444757459L;
	private JButton startBtn;
	private JButton pauseBtn;
	private JButton homeBtn;
	private JCheckBox voiceCheck;
	private Font itFont;
	private GameData gameData;
	private Point[] nextPoints;
	private Color nextColor;
	public RightPanel(GameData data) {
		nextPoints = new Point[BlockBase.CNT];
		gameData = data;
		
		itFont = new Font("楷体", Font.PLAIN, 20);
		setLayout(null);
		setOpaque(false);
		
		startBtn = new JButton("开始");
		startBtn.setBounds(0, 10+10*BlockBase.BOX, 4*BlockBase.BOX, BlockBase.BOX);
		pauseBtn = new JButton("暂停");
		pauseBtn.setBounds(0, 10+11*BlockBase.BOX, 4*BlockBase.BOX, BlockBase.BOX);
		pauseBtn.setEnabled(false);
		homeBtn = new JButton("返回主页");
		homeBtn.setBounds(0, 10+12*BlockBase.BOX, 4*BlockBase.BOX, BlockBase.BOX);
		voiceCheck = new JCheckBox("游戏声效");
		voiceCheck.setBounds(0, 10+13*BlockBase.BOX, 4*BlockBase.BOX, BlockBase.BOX);
		voiceCheck.setOpaque(false);
		startBtn.setFont(itFont);
		pauseBtn.setFont(itFont);
		homeBtn.setFont(itFont);
		voiceCheck.setFont(itFont);
		add(startBtn);
		add(pauseBtn);
		add(homeBtn);
		add(voiceCheck);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
		Color oldColor = g.getColor();
		g.setColor(new Color(0,0,1,30));
		g.fillRect(0, 10+BlockBase.BOX, 4*BlockBase.BOX, 4*BlockBase.BOX);
		nextPoints = gameData.getNextPoints();
		nextColor = gameData.getNextColor();
		
		// 画下一个方块
		g.setColor(nextColor);
		// 判断差量
		int dx = 0, dy = 0;
		// 提示方块的初始位置
		int initx=1,inity=1;
		for(Point p:nextPoints) {
			dx = p.x<dx?p.x:dx;
			dy = p.y<dy?p.y:dy;
		}
		if(dx<0) {
			initx = -dx;
		}
		if(dy<0) {
			inity = -dy;
		}
		for(Point p:nextPoints) {
			int x = 0 + (initx + p.x)*BlockBase.BOX;
			int y = 10+BlockBase.BOX + (inity + p.y)*BlockBase.BOX;
			g.fill3DRect(x, y, BlockBase.BOX, BlockBase.BOX, true);
		}
		
		g.setFont(itFont);
		g.setColor(Color.black);
		
		g.drawString("下一个方块：", 0, BlockBase.BOX);
		g.drawString("等级："+gameData.level,0, 10+6*BlockBase.BOX);
		g.drawString("分数："+gameData.score,0, 10+8*BlockBase.BOX);
		g.setColor(oldColor);
	}
	
	public JButton getStartBtn() {
		return startBtn;
	}
	public JButton getHomeBtn() {
		return homeBtn;
	}
	public JButton getPauseBtn() {
		return pauseBtn;
	}
	public JCheckBox getVoiceCheck() {
		return voiceCheck;
	}

	
//	public static void main(String[] args) {
//		JFrame test = new JFrame();
//		RightPanel rightPanel = new RightPanel();	
//		test.add(rightPanel);
//		test.setVisible(true);
//		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	 
}