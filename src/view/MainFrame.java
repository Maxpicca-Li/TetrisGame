package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import model.BlockBase;
import model.GameData;

/**
 *  单机版主界面
* @author Maxpicca
* @version 创建时间：2020-11-27
* @Description 单机版主界面
*/

public class MainFrame extends AbstractFrame {
	private static final long serialVersionUID = 8456560429229699542L;
	private int height=60+GamePanel.ROWS*BlockBase.BOX;
	private int width=20+(GamePanel.COLS+GamePanel.COLS/2)*BlockBase.BOX;	
	GamePanel gamePanel;
	RightPanel rightPanel;	
	Container mainPanel;
	
	GameData gamedata;
	
	
	public MainFrame(GameData gameData) {
		
		this.gamedata = gameData;
		
		setGamePanel();
		setRightPanel();
		this.setTitle("俄罗斯方块单机版");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// 自由布局很重要！
		setLayout(null);
		
		// 获取屏幕中心位置
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int x = (screenSize.width - width)/2;
		int y = (screenSize.height - height)/2;
		setBounds(x,y,width, height);
		
		mainPanel = this.getLayeredPane();
		mainPanel.setLayout(null);
		setBack(); 
	}
		
		
	private void setBack() {
		ImageIcon imageIcon = new ImageIcon("material/imgMainFrame.jpg");
		JLabel bg = new JLabel(imageIcon);
		bg.setBounds(0,0,width, height);
		this.getContentPane().add(bg);
	}
	private void setGamePanel() {
		gamePanel = new GamePanel(this.gamedata);
		gamePanel.setLocation(10, 10);
		this.add(gamePanel);
		// gamePanel.repaint();
	}
	private void setRightPanel() {
		rightPanel = new RightPanel(gamedata);
		rightPanel.setBounds(10+GamePanel.COLS*BlockBase.BOX+20, 0,4*BlockBase.BOX+10,height);
		this.add(rightPanel);
	}
	
	@Override
	public void updatePanel() {
		gamePanel.repaint();
		rightPanel.repaint();
	}
	@Override
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	@Override
	public JButton getStartBtn() {
		return rightPanel.getStartBtn();
	}
	@Override
	public JButton getHomeBtn() {
		return rightPanel.getHomeBtn();
	}
	@Override
	public JButton getPauseBtn() {
		return rightPanel.getPauseBtn();
	}
	@Override
	public JCheckBox getVoiceCheck() {
		return rightPanel.getVoiceCheck();
	}
	@Override
	public GameData getSbdata() {
		return null;
	}
	/*public static void main(String[]args) {
		GameData gameData = new GameData();
		MainFrame mainFrame = new MainFrame(gameData);		
		mainFrame.setVisible(true);
//		mainFrame.setFocusable(true);
	}*/

}
