package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.BlockBase;
import model.GameData;

/**
* @author Maxpicca
* @version 创建时间：2020-12-11
* @Description 对战版主界面
*/

public class DoubleFrame extends AbstractFrame{

	private static final long serialVersionUID = 1L;
	private OnePanel myPanel;
	private OnePanel sbPanel;
	private GameData mydata;
	private GameData sbdata;
	private int width;
	private int height;
	private int btnWidth = 3;
	private JButton startBtn;
	private JButton pauseBtn;
	private JButton homeBtn;
	private JCheckBox voiceCheck;
	
	public DoubleFrame(GameData myd,GameData sbd) {
		setTitle("俄罗斯方块对战版");
		setLayout(null);
		
		this.mydata = myd;
		this.sbdata = sbd;
		myPanel = new OnePanel(mydata);
		myPanel.setIdName("Me");
		sbPanel = new OnePanel(sbdata);
		sbPanel.setIdName("SomeBody");
		width = myPanel.getWidth()*2 + btnWidth*BlockBase.BOX+60;
		height = myPanel.getHeight()+50;
		myPanel.setLocation(10, 10);
		sbPanel.setLocation(30+(GamePanel.COLS+btnWidth)*BlockBase.BOX, 10);
		
		int btnX = 20+myPanel.getWidth();
		int btnY = 10+15*BlockBase.BOX;
		startBtn = new JButton("开始");
		startBtn.setBounds(btnX, btnY, btnWidth*BlockBase.BOX, BlockBase.BOX);
		pauseBtn = new JButton("暂停");
		pauseBtn.setBounds(btnX, btnY+1*BlockBase.BOX, btnWidth*BlockBase.BOX, BlockBase.BOX);
		pauseBtn.setEnabled(false);
		homeBtn = new JButton("主页");
		homeBtn.setBounds(btnX, btnY+2*BlockBase.BOX, btnWidth*BlockBase.BOX, BlockBase.BOX);
		voiceCheck = new JCheckBox("声效");
		voiceCheck.setBounds(btnX, btnY+3*BlockBase.BOX, btnWidth*BlockBase.BOX, BlockBase.BOX);
		voiceCheck.setOpaque(false);
		setComponent(startBtn);
		setComponent(pauseBtn);
		setComponent(homeBtn);
		setComponent(voiceCheck);
		
		add(myPanel);
		add(sbPanel);
		setBack();
		
		// 设置中心位置
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int x = (screenSize.width - width)/2;
		int y = (screenSize.height - height)/2;
		this.setBounds(x,y,width, height);
		setBounds(x, y, width, height);
		setResizable(false);
	}
	
	private void setComponent(Component comp) {
		Font f = new Font("楷体", Font.PLAIN, BlockBase.BOX/2);
		comp.setFont(f);
		this.add(comp);
	}
	private void setBack() {
		ImageIcon imageIcon = new ImageIcon("material/imgDoubleFrame.png");
		JLabel bg = new JLabel(imageIcon);
		bg.setBounds(0,0,width, height);
		this.getContentPane().add(bg);
	}
	
	
	@Override
	public void updatePanel() {
		myPanel.repaint();
		sbPanel.repaint();
	}
	@Override
	public JButton getStartBtn() {
		return startBtn;
	}
	@Override
	public JButton getPauseBtn() {
		return pauseBtn;
	}
	@Override
	public JButton getHomeBtn() {
		return homeBtn;
	}
	@Override
	public JCheckBox getVoiceCheck() {
		return voiceCheck;
	}
	@Override
	public GamePanel getGamePanel() {
		return myPanel.getGamePanel();
	}
	@Override
	public GameData getSbdata() {
		return sbdata;
	}
	
	/*
	   public static void main(String[]args) throws InterruptedException {
		GameData data1 = new GameData(), data2 = new GameData();
		DoubleFrame mainFrame = new DoubleFrame(data1,data2);
		mainFrame.setVisible(true);
		mainFrame.setFocusable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Thread.sleep(5000);// 5s
		mainFrame.updatePanel();
	}
	*/
}
