package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Block;
import model.GameData;

/**
 * @author Maxpicca
 * @version 创建时间：2020-12-11
 * @Description 服务端程序，处理数据收发和用户线程管理
 */

public class TetrisServer {
	private ServerSocket server = null;
	private Socket client;
	private ArrayList<GameThread> threads;

	public TetrisServer(int port) {
		threads = new ArrayList<GameThread>();
		try {
			server = new ServerSocket(port);
			System.out.println("服务器创建成功，等待连接");
			while (true) {
				client = server.accept();
				int num = threads.size();
				GameThread gt = new GameThread(client);
				gt.start();
				if (num == 2) {
					System.out.println("房间人数已满，请等待。。。。");
					continue;
				}
				System.out.println("第 " + (num + 1) + " 个用户进入房间。");
				threads.add(gt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (GameThread gt : threads) {
				gt.setCanRun(false);
			}
			JOptionPane.showMessageDialog(null, "游戏异常退出");
			System.exit(0);
		}
	}

	class GameThread extends Thread {
		private Socket s = null;
		private ObjectInputStream ois = null;
		private ObjectOutputStream oos = null;
		// 确保其他程序使用的是最新的值
		private volatile boolean canRun = true;

		public GameThread(Socket s) throws Exception {
			super();
			// 利用线程进行通信
			this.s = s;
			// NOTE 服务器先读后写，还需要先定义oos,再定义ois
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		}

		@Override
		public void run() {
			try {
				while (canRun) {
					// DBUG
					// System.out.println("server");
					GameData dataFromClient = (GameData) ois.readObject();
					if (dataFromClient.isExit()) {
						ois.close();
						oos.close();
						System.out.println("线程退出，并移除一个客户端");
						threads.remove(this);
						s.close();
						this.canRun = false;
						return;
					}
					for (GameThread gt : threads) {
						// 这里的oos是调用的arraylist里面的oos哦，不是this的oos
						// 给非我的其他客户端写数据
						if (gt != this) {
							gt.oos.writeObject(dataFromClient);
							gt.oos.flush();
							gt.oos.reset();
						}
					}
//					Thread.sleep(5000);
				}
			} catch (Exception e) {
				e.printStackTrace();
				canRun = false;
				threads.remove(this);
				JOptionPane.showMessageDialog(null, "游戏异常退出");
				System.exit(0);
			}
		}

		public void setCanRun(boolean canRun) {
			this.canRun = canRun;
		}
	}

}
