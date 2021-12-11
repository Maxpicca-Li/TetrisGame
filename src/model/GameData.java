package model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

import view.GamePanel;

/**
 * @author Maxpicca
 * @version 创建时间：2020-12-4
 * @Description 游戏的所有数据，所有数据操作的处理中心
 */

public class GameData implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 426415483431223301L;
	public int score = 0;
	public int level = 0;
	private int MYLevel = 10;
	private Block currBlock, nextBlock;
	private Color[][] nowMap;
	private boolean state = false;
	private boolean isPause = false;
	private boolean isExit = false;
	
	public GameData() {
		init();
	}

	public GameData(GameData data) {
		copy(data);
	}

	public void init() {
		score = 0;
		level = 0;
		// x是COLS y是ROWS 且加上出场需要两行，
		nowMap = new Color[GamePanel.COLS][GamePanel.ROWS];
		// Map初始化
		for (int i = 0; i < nowMap.length; i++) {
			for (int j = 0; j < nowMap[i].length; j++) {
				nowMap[i][j] = BlockBase.DEFAULT_COLOR;
			}
		}
		nextBlock = new Block(getRandomBlock());
		currBlock = new Block(getRandomBlock());
	}
	
	public void copy(GameData data) {
		this.currBlock = new Block(data.getCurrBlock());
		this.nextBlock = new Block(data.getNextBlock());
		this.score = data.score;
		level = data.level;
		state = data.getState();
		isPause = data.isPause;
		Color[][] dataMap = data.getNowMap();
		for (int i = 0; i < nowMap.length; i++) {
			for (int j = 0; j < nowMap[i].length; j++) {
				this.nowMap[i][j] = dataMap[i][j];
			}
		}
	}
	
	public void moveLeft() {
		for (Point p : currBlock.points) {
			if (p.x - 1 < 0 || isBlock(p.x - 1, p.y)) {
				return;
			}
		}
		currBlock.moveLeft();
	}

	public void moveRight() {
		for (Point p : currBlock.points) {
			if (p.x + 1 >= GamePanel.COLS || isBlock(p.x + 1, p.y)) {
				return;
			}
		}
		currBlock.moveRight();
	}

	public void moveDown() {
		if(!canDown()) {
			return;
		}
		currBlock.moveDown();
	}

	/**
	 * Enter键径直下落
	 */
	public void moveDirect() {
		while (true) {
			if(!canDown()) {
				return;
			}
			currBlock.moveDown();
		}
	}

	public void rotate() {
		int realX = currBlock.points[0].x, realY = currBlock.points[0].y;
		Point[] ps = new Point[4];
		boolean flag = true;
		for (int i = 0; i < ps.length; i++) {
			int x = currBlock.points[i].x - currBlock.points[0].x;
			int y = currBlock.points[i].y - currBlock.points[0].y;
			ps[i] = new Point(x, y);
			if (!ps[i].equals(BlockBase.B_SHAPE[1][i])) {
				flag = false;
			}
		}
		// 如果是方形
		if (flag) {
			return;
		}
		if (currBlock.points.equals(BlockBase.B_SHAPE[1])) {
			return;
		}
		for (Point p : currBlock.points) {
			int x = (p.y - realY) + realX;
			int y = -(p.x - realX) + realY;
			if (x < 0 || x >= GamePanel.COLS || y >= GamePanel.ROWS || isBlock(x, y)) {
				return;
			}
		}
		currBlock.rotate();
	}

	public Block getCurrBlock() {
		return currBlock;
	}

	public void setCurrBlock(Block currBlock) {
		this.currBlock = new Block(currBlock);
	}

	/**
	 * Block成固态了，因为写程序的时候不动脑子
	 * 
	 * @return
	 */
	public Point[] getNextPoints() {
		// 如果我不需要旋转系数的话，这一个函数就白写了，自上而下还是做的不好
		int cnt = BlockBase.CNT;
		int beginX = currBlock.BEGIN_X, beginY = currBlock.BEGIN_Y;
		Point[] ps = new Point[cnt];
		for (int i = 0; i < cnt; i++) {
			int x = nextBlock.points[i].x - beginX;
			int y = nextBlock.points[i].y - beginY;
			ps[i] = new Point(x, y);
		}
		return ps;
	}

	public Color getNextColor() {
		return nextBlock.color;
	}

	public void setNextBlock(Block nextBlock) {
		this.nextBlock = nextBlock;
	}

	/**
	 * 累计消行+填充颜色
	 */
	public void fixMap() {
		for (Point p : currBlock.points) {
			// 判断是否超界
			if (p.x < 0 || p.x >= nowMap.length || p.y < 0 || p.y >= nowMap[0].length)
				continue;
			nowMap[p.x][p.y] = currBlock.color;
		}
		doClear();
		currBlock = new Block(nextBlock);
		nextBlock = new Block(getRandomBlock());
	}
	
	/**
	 * 消行逻辑
	 * @return
	 */
	public void doClear() {
		for (int j = nowMap[0].length - 1; j >= 1;) {
			boolean flag = true;
			// 第二层循环时遍历x,即各个列!
			for (int i = 0; i < nowMap.length; i++) {
				if (!isBlock(i, j))
					flag = false;
			}
			if (flag) {
				score++;
				for (int k = j; k >= 0; k--) {
					for (int i = 0; i < nowMap.length; i++) {
						if (k == 0)
							nowMap[i][k] = BlockBase.DEFAULT_COLOR;
						else
							nowMap[i][k] = nowMap[i][k - 1];
					}
				}
			} else {
				j--;
			}
		}
		level = score / MYLevel;
	}
	public Color[][] getNowMap() {
		return nowMap;
	}

	public boolean canDown() {
		for (Point p : currBlock.points) {
			if (p.y + 1 >= GamePanel.ROWS || isBlock(p.x, p.y + 1)) {
				fixMap();
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断当前位置是否有方块，也可判断是否超界的问题; 有则返回true
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isBlock(int x, int y) {
		if (x < 0 || x >= nowMap.length || y < 0 || y >= nowMap[0].length)
			return false;
		return !nowMap[x][y].equals(BlockBase.DEFAULT_COLOR);
	}

	/**
	 * 产生随机方块（绝对位置）
	 * 
	 * @return block
	 */
	public Block getRandomBlock() {
		Random r = new Random();
		int shapeIndex = Math.abs(r.nextInt()) % BlockBase.NUM;
		int colorIndex = Math.abs(r.nextInt()) % BlockBase.NUM;
		Block block = new Block(BlockBase.B_SHAPE[shapeIndex], BlockBase.B_COLOR[colorIndex]);
		/*
		 * 获取旋转系数，为了对战版显示方便，故不设置旋转系数
		 * int rotateIndex = Math.abs(r.nextInt()) % 4; for (int i = 0; i < rotateIndex;
		 * i++) { block.rotate(); }
		 */
		return block;
	}

	public Block getNextBlock() {
		return nextBlock;
	}

	public boolean isPause() {
		return isPause;
	}

	public int getMYLevel() {
		return MYLevel;
	}

	public void setMYLevel(int mYLevel) {
		MYLevel = mYLevel;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setNowMap(Color[][] nowMap) {
		this.nowMap = nowMap;
	}

	public void setPause(boolean b) {
		this.isPause = b;
	}

	public boolean getState() {
		for (int c = 0; c < nowMap.length; c++) {
			if (isBlock(c, 0))
				state = false;
		}
		return state;
	}

	public void setState(boolean b) {
		state = b;
	}
	
	public boolean isExit() {
		return isExit;
	}

	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}
}
