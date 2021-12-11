package view;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Block;
import model.BlockBase;
import model.GameData;

/**
 * @author Maxpicca
 * @version 创建时间：2020-11-27
 * @Description 俄罗斯方块主游戏界面
 */

public class GamePanel extends JPanel {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1860979716621182121L;
	/**
	 * 游戏面板的实际行设置，便于放置初始的两个
	 */
	public static final int INITROWS = 22;
	public static final int ROWS = 20;
	public static final int COLS = 10;
	
	private static int width = GamePanel.COLS*BlockBase.BOX;
	private static int height = GamePanel.ROWS*BlockBase.BOX;
	
	private Block currBlock;
	private GameData gamedata;
	private Color [][] nowMap;
	private JLabel pauseLabel;
	private boolean isPause = false;
	
	public GamePanel(GameData gamedata) {
		ImageIcon pauseIcon = new ImageIcon("material/imgPause.png");
		pauseLabel = new JLabel(pauseIcon);
		pauseLabel.setSize(pauseIcon.getIconWidth(), pauseIcon.getIconHeight());
		
		nowMap = new Color[GamePanel.COLS][GamePanel.ROWS];
		for(int i=0;i<nowMap.length;i++) {
			for(int j=0;j<nowMap[i].length;j++) {
				nowMap[i][j]=BlockBase.DEFAULT_COLOR;
			}
		}
		// 设置背景透明
		setOpaque(false);
		setSize(width, height);
		this.gamedata = gamedata;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		Color oldColor = g.getColor();
		g.setColor(new Color(0,0,0,255));
		g.fillRect(0, 0, width, height);
		g.setColor(oldColor);
		
//		g.setColor(new Color(0, 0, 0, 255)); // 绘制墙
//        Stroke oldStroke = ((Graphics2D) g).getStroke(); //保存旧笔触  
//        ((Graphics2D) g).setStroke(new BasicStroke(10L)); //设置新笔触  
//        g.drawRect(0, 0, width, height); //绘制矩形  
//        ((Graphics2D) g).setStroke(oldStroke);  //恢复旧笔触  
		
		// 绘制背景
        g.setColor(new Color(255,255,255,70));
		for(int i = 0;i<GamePanel.COLS;i++) {
			for(int j=0;j<GamePanel.ROWS;j++) {
				g.drawRect(i*BlockBase.BOX, j*BlockBase.BOX, BlockBase.BOX, BlockBase.BOX);
			}
		}
		g.setColor(oldColor);
		
		// 绘制currBlock
		currBlock = gamedata.getCurrBlock();
		for(int i=0;i<BlockBase.CNT;i++) {
			g.setColor(currBlock.color);
			g.fill3DRect(currBlock.points[i].x*BlockBase.BOX, currBlock.points[i].y*BlockBase.BOX, BlockBase.BOX, BlockBase.BOX,true);
		}
		
		// 绘制nowMap
		nowMap = gamedata.getNowMap();
		for(int i=0;i<nowMap.length;i++) {
			for(int j=0;j<nowMap[i].length;j++) {
				Color cIndex = nowMap[i][j];
				if(cIndex==BlockBase.DEFAULT_COLOR) {
					continue;
				}
				g.setColor(cIndex);
				g.fill3DRect(i*BlockBase.BOX, j*BlockBase.BOX, BlockBase.BOX, BlockBase.BOX,true);
			}
		}
		// g.fillRect(gamedata.x*BlockBase.SIZE, gamedata.y*BlockBase.SIZE, BlockBase.SIZE, BlockBase.SIZE);
		
		// 绘制暂停
		if(gamedata.isPause()) {
			this.add(pauseLabel);
		}else {
			this.remove(pauseLabel);
		}
	}
}
