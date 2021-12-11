package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import app.TetrisClient;
import app.TetrisServer;
import model.GameData;
import model.MusicBase;
import view.DoubleFrame;
import view.HomeFrame;
import view.MainFrame;

/**
 * @author Maxpicca
 * @version 创建时间：2020-12-12
 * @Description 首页按钮监听事件
 */

public class HomeController {
	private HomeFrame homeFrame;
	public HomeController(JFrame hFrame){
		homeFrame = (HomeFrame) hFrame;
		homeFrame.getBeginBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameData gameData = new GameData();
				MainFrame mainFrame = new MainFrame(gameData);
				GameController gameCtrl = new GameController(gameData, mainFrame);
				mainFrame.setVisible(true);
				mainFrame.setFocusable(true);
			}
		});

		homeFrame.getCreatBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String p = JOptionPane.showInputDialog(homeFrame, "请输入房间号:", "5500");
				if (p == null) {
					return;
				}
				int port = Integer.parseInt(p);

				new Thread(new Runnable() {
					@Override
					public void run() {
						TetrisServer server = new TetrisServer(port);
					}
				}).start();
			}
		});
		
		homeFrame.getEnterBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 应该是用户输入房间号
				String p = JOptionPane.showInputDialog(homeFrame, "请输入房间号", "5500");
				if (p == null) {
					return;
				}
				int port = Integer.parseInt(p);
				GameData myData = new GameData();
				GameData sbData = new GameData();
				DoubleFrame doubleFrame = new DoubleFrame(myData, sbData);
				try {
					GameController gameCtrl = new GameController(myData, doubleFrame);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "游戏异常退出");
					System.exit(0);
				}
				new Thread(new Runnable() {

					@Override
					public void run() {
						TetrisClient client = new TetrisClient(port, myData, sbData);
					}
				}).start();
				doubleFrame.setVisible(true);
				doubleFrame.setFocusable(true);
			}
		});

	}

}
