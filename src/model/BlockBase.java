package model;

import java.awt.Color;
import java.awt.Point;

/**
 * @author Maxpicca
 * @version 创建时间：2020-11-30
 * @Description 存储初始Block的点数组和颜色数组，为程序的Block库
 */

public class BlockBase {
	/**
	 * 一个Block需要4个方块
	 */
	public static final int CNT = 4;
	/**
	 * 7种方格
	 */
	public static final int NUM = 7;
	/**
	 * 一个box的大小
	 */
	public static final int BOX = 35;
	/**
	 * 默认颜色，背景透明
	 */
	public static final Color DEFAULT_COLOR = new Color(0, 0, 0, 0); 
	/**
	 *俄罗斯方块基本形状，设置中心点，且索引为0，方便旋转算法实现
	 */
	public static final Point[][] B_SHAPE = {
			// 长形
			{ new Point(0, 0), new Point(-1, 0), new Point(1, 0), new Point(2, 0) },
			// 方形
			{ new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1) },
			// T形
			{ new Point(0, 0), new Point(-1, 0), new Point(1, 0), new Point(0, 1) },
			// Z形
			{ new Point(0, 0), new Point(-1, 0), new Point(0, 1), new Point(1, 1) },
			// S形
			{ new Point(0, 0), new Point(-1, 1), new Point(0, 1), new Point(1, 0) },
			// J形
			{ new Point(0, 0), new Point(-1, 0), new Point(-1, 1), new Point(1, 0) },
			// L形
			{ new Point(0, 0), new Point(-1, 0), new Point(1, 0), new Point(1, 1) }, 
		};
	public static final Color[] B_COLOR = { Color.RED, new Color(128,255,255), 
			Color.green, Color.yellow, Color.magenta, Color.orange, Color.pink};
}
