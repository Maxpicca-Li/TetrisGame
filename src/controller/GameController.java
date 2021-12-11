package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.GameData;
import model.MusicBase;
import view.AbstractFrame;
import view.GamePanel;

/**
 * @author Maxpicca
 * @version 创建时间：2020-12-4
 * @Description 游戏逻辑的控制中心，处理有关游戏进行时的监听事件
 */

public class GameController {

	public static final int INITDELAY = 1000;
	public static final int MINDELAY = 100;
	private GameData myData;
	private GameData sbData;
	private AbstractFrame mainFrame;
	public GamePanel gamePanel;
	private JButton startBtn;
	private JButton pauseBtn;
	private JButton homeBtn;
	private JCheckBox voiceCheck;
	private MusicBase music;
	private boolean isPause = false;
	private boolean canPlay = false;
	private boolean sbStart = false;
	private boolean myStart = false;
	private int recordScore;
	/**
	 * 下降线程，设置监听，并辅助gamePanel夺回焦点
	 */
	private Timer timer;

	public GameController(GameData gameData, AbstractFrame mainFrame) {
		this.myData = gameData;
		this.mainFrame = mainFrame;
		this.gamePanel = mainFrame.getGamePanel();
		this.startBtn = mainFrame.getStartBtn();
		this.pauseBtn = mainFrame.getPauseBtn();
		this.homeBtn = mainFrame.getHomeBtn();
		this.voiceCheck = mainFrame.getVoiceCheck();
		this.sbData = mainFrame.getSbdata();
		this.music = new MusicBase();
		this.recordScore = gameData.score;
		timer = new Timer(INITDELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 界面重获焦点
				if (!gamePanel.isFocusable()) {
					gamePanel.setFocusable(true);
				}
				if (!gamePanel.isFocusOwner()) {
					gamePanel.requestFocus(true);
				}
				
				// 第一种：他方结束了
				if(sbData!=null) {
					if(sbData.getState()) {
						sbStart = true;
					}
					if(sbStart && !sbData.getState()) {
						doOver();
						return ;
					}
				}
				// 第二种：我方结束了
				if(myStart && !myData.getState()) {
					doOver();
					return ;
				}
				
				// 游戏进行中
				if (gameData.getState()) {
					myStart = true;
					gameData.moveDown();
					if (gameData.score != recordScore && canPlay) {
						recordScore = gameData.score;
						music.soundScore.play();
					} else if (!gameData.canDown() && canPlay) {
						music.soundHit.play();
					}
				}
				
				// 等级变化，从0开始算
				int x = INITDELAY / (gameData.level + 1);
				if (x < MINDELAY) {
					x = MINDELAY;
				}
				timer.setDelay(x);
				mainFrame.updatePanel();
			}
		});
		
		gamePanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!myData.getState()) {
					return;
				}
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (canPlay) {
						music.soundMove.play();
					}
					gameData.moveLeft();
					break;
				case KeyEvent.VK_RIGHT:
					if (canPlay) {
						music.soundMove.play();
					}
					gameData.moveRight();
					break;
				case KeyEvent.VK_DOWN:
					gameData.moveDown();
					if (!gameData.canDown() && canPlay) {
						music.soundHit.play();
					}
					break;
				case KeyEvent.VK_UP:
					if (canPlay) {
						music.soundMove.play();
					}
					gameData.rotate();
					break;
				case KeyEvent.VK_ENTER:
					if (canPlay) {
						music.soundDirect.play();
					}
					gameData.moveDirect();
					break;
				case KeyEvent.VK_SPACE:
					doPause();
				default:
					break;
				}
				mainFrame.updatePanel();
			}
		});

		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 游戏开始了，点击"终止"
				if (myData.getState()) {
					// 对方是根据我的state判断的
					myData.setState(false);
					timer.stop();
					doOver();
					return ;
				}
				// 游戏未开始，点击"开始"
				doStart();
			}
		});

		pauseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doPause();
			}
		});

		homeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doExit();
			}
		});

		voiceCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (voiceCheck.isSelected()) {
					canPlay = true;
					music.soundBgm.loop();
				} else {
					canPlay = false;
					music.soundBgm.stop();
				}
			}
		});

		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				doExit();
			}
		});
	}

	public void doPause() {
		if (canPlay) {
			music.soundPause.play();
		}
		isPause = !isPause;
		myData.setPause(isPause);
		if (isPause) {
			pauseBtn.setText("继续");
			timer.stop();
		} else {
			pauseBtn.setText("暂停");
			timer.start();
		}
		mainFrame.updatePanel();
	}

	public void doStart() {
		timer.start();
		myData.setState(true);
		pauseBtn.setEnabled(true);
		startBtn.setText("终止");
		return;
	}

	public void doOver() {
		timer.stop();
		if (sbData == null) {
			JOptionPane.showMessageDialog(mainFrame, "Game Over！\n分数："+myData.getScore());
			myStart = false;
			overHelp();
			return;
		}
		if (sbData.score > myData.score) {
			JOptionPane.showMessageDialog(mainFrame, "你输了。\n我方分数："+myData.getScore()+"\n对方分数："+sbData.getScore());
		} else if (sbData.score < myData.score) {
			JOptionPane.showMessageDialog(mainFrame, "你赢了。\n我方分数："+myData.getScore()+"\n对方分数："+sbData.getScore());
		} else {
			JOptionPane.showMessageDialog(mainFrame, "平局。\n分数："+myData.getScore());
		}
		sbStart = false;
		recordScore = 0;
		overHelp();
		return;
	}

	public void overHelp() {
		
		if (canPlay) {
			music.soundOver.play();
		}
		timer.stop();
		myData.setState(false);
		myData.init();
		myData.setPause(false);
		mainFrame.updatePanel();
		startBtn.setText("开始");
		startBtn.setEnabled(true);
		pauseBtn.setEnabled(false);
	}

	private void doExit() {
		timer.stop();
		music.soundBgm.stop();
		myData.setExit(true);
		mainFrame.dispose();
	}
}
