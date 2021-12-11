package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
* @author Maxpicca
* @version 创建时间：2020-12-11
* @Description 游戏主页，响应用户选择
*/

public class HomeFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private JButton beginBtn;
	private JButton creatBtn;
	private JButton enterBtn;
	
	public HomeFrame() {
		int btnWidth = 105, btnHeight = 53;
		beginBtn = new JButton("");
		beginBtn.setBounds(475, 270, btnWidth, btnHeight);
		setButton(beginBtn);
		creatBtn = new JButton("");
		creatBtn.setBounds(475, 340, btnWidth, btnHeight);
		setButton(creatBtn);
		enterBtn = new JButton("");
		enterBtn.setBounds(475, 410, btnWidth, btnHeight);
		setButton(enterBtn);
		
		setTitle("俄罗斯方块之网络对战");
		setBack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	private void setButton(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setBorder(null);
		this.add(btn);
	}
	
	private void setBack() {
		ImageIcon imageIcon = new ImageIcon("material/imgHome.png");
		JLabel bg = new JLabel(imageIcon);
		/*
		 * width = imageIcon.getIconWidth(); height = imageIcon.getIconHeight();
		 */
		width = 1040;
		height = 580;
		this.getContentPane().add(bg);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int x = (screenSize.width - width)/2;
		int y = (screenSize.height - height)/2;
		this.setBounds(x,y,width, height);
	}
	
	public JButton getBeginBtn() {
		return beginBtn;
	}

	public JButton getCreatBtn() {
		return creatBtn;
	}

	public JButton getEnterBtn() {
		return enterBtn;
	}

	public static void main(String[]args) {
		HomeFrame mainFrame = new HomeFrame();		
		mainFrame.setVisible(true);
		mainFrame.setFocusable(true);
	}
}
