package app;

import controller.HomeController;
import view.HomeFrame;

/**
 * @author Maxpicca
 * @version 创建时间：2020-12-4
 * @Description 程序总入口
 */

public class TetrisApp {
	public static void main(String[] args) {
		HomeFrame homeFrame = new HomeFrame();
		HomeController homeController = new HomeController(homeFrame);
		homeFrame.setVisible(true);
		
	}
}

