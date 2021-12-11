package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import model.GameData;

/**
 * @author Maxpicca
 * @version 创建时间：2020-12-12
 * @Description 客户端程序，处理数据发收并判断客户是否退出
 */

public class TetrisClient {
	private Socket client;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	public TetrisClient(int port, GameData mydata, GameData sbdata) {
		try {
			client = new Socket("localhost", port);
			System.out.println("成功连接服务器");
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());
			// 客户端先写后读
			while (true) {
				// DBUG
				// System.out.println("\tclient");
				oos.writeObject(mydata);
				oos.flush();
				// 重置文件头
				oos.reset();
				if (mydata.isExit()) {
					System.out.println("客户端退出了");
					// sbdata.init();
					sbdata.setState(false);
					oos.close();
					ois.close();
					client.close();
					return;
				}
				GameData dataFromServer = (GameData) ois.readObject();
				// NOTE 不能直接使用"="，否则会产生一个新的对象，sbdata的指向地址会被更改，
				// 导致sbdata不能在原地址下进行修改，只能调用原对象的方法进行赋值，才能在原地址上修改
				sbdata.copy(dataFromServer);
				// DBUG
//				Thread.sleep(5000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "游戏异常退出");
			System.exit(0);
		}
	}

}
