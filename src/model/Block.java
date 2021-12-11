package model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
/**
* @author Maxpicca
* @version 创建时间：2020-11-29
* @Description 俄罗斯方块，由点数组和颜色组成，游戏的数据单元
*/

public class Block implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -7544692261116765895L;
	public Color color;
	public Point[] points;

	/**
	 * 方块出场的中心位置设置
	 */
	 public static final int BEGIN_X = 4,BEGIN_Y = 0;

	/**
	 * 点集初始化
	 * @param points 点集
	 * @param color 颜色
	 */
	public Block(Point[] points,Color color) {
		this.points = new Point[BlockBase.CNT];
		for(int i=0;i<BlockBase.CNT;i++) {
			this.points[i]=new Point(points[i].x+BEGIN_X,points[i].y+BEGIN_Y);
		}
		this.color = new Color(color.getRGB());;
	}
	
	/**
	 * 拷贝构造函数
	 * @param block
	 */
	public Block(Block block) {
		this.points = new Point[BlockBase.CNT];
		for(int i=0;i<BlockBase.CNT;i++) {
			this.points[i]=new Point(block.points[i].x,block.points[i].y);
		}
		this.color = block.color;
	}
	
	public void moveLeft() {
		for(Point p:points) {
			p.x--;
		}
	}
	public void moveRight() {
		for(Point p:points) {
			p.x++;
		}
	}
	public void moveDown() {
		for(Point p:points) {
			p.y++;
		}
	}
	/**
	 * (beginX,beginY)是方块的中心初始为（0，0）的坐标，(p.x-realX,p.y-realY)为各点的相对坐标，(p.y-realY, -(p.x-realX))为其旋转后的相对坐标
	 * 	旋转y --> -x, x --> y
	 */
	public void rotate() {
		int realX = points[0].x,realY = points[0].y;
		for(Point p:points) {
			int x = (p.y-realY) +realX;
			int y = -(p.x-realX) +realY;
			p.x = x;
			p.y = y;
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}
	
}
