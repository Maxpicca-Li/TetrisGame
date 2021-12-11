package view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import model.GameData;

/**
* @author Maxpicca
* @version 创建时间：2020-12-12
* @Description 抽象界面，用以实现不同Frame与GameController的统一接口
*/

public abstract class AbstractFrame extends JFrame {

	private static final long serialVersionUID = -1452097375449794111L;
	private JButton startBtn;
	private JButton pauseBtn;
	private JButton homeBtn;
	private JCheckBox voiceCheck;
	private GameData sbData;
	public GamePanel gamePanel;
	
	public AbstractFrame() {
		
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
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	public void updatePanel() {
		;
	}
	public GameData getSbdata() {
		return sbData;
	}
}
