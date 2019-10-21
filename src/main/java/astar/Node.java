package astar;

import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;


public class Node implements Comparable<Node> {
	// 坐标
	public Point _pos;
	
	// 开始地点数值
	private int _costFromStart;
	
	// 目标地点数值
	private int _costToObject;
	
	// 父节点
	public Node _parentNode;
	
	/**
	 * 以注入坐标点方式初始化Node
	 *
	 * @param _pos pos
	 */
	public Node(Point _pos) {
		this._pos = _pos;
	}
	
	/**
	 * 返回路径成本
	 *
	 * @param node node
	 * @return 成本
	 */
	public int getCost(Node node) {
		// 获得坐标点间差值 公式：(x1, y1)-(x2, y2)
		int m = node._pos.x - _pos.x;
		int n = node._pos.y - _pos.y;
		// 取两节点间欧几理德距离（直线距离）做为估价值，用以获得成本
		return (int) Math.sqrt(m * m + n * n);
	}
	
	
	@Override
	public int hashCode(){
		return Objects.hash(_pos,_costFromStart,_costToObject,_parentNode);
	}
	
	@Override
	public boolean equals(Object o){
		if(this==o){
			return true;
		}
		if(o==null || getClass()!=o.getClass()){
			return false;
		}
		Node node=(Node)o;
		return _costFromStart==node._costFromStart && _costToObject==node._costToObject && _pos.equals(node._pos) && _parentNode.equals(node._parentNode);
	}
	
	/**
	 * 比较两点以获得最小成本对象
	 */
	public int compareTo(Node node) {
		int a1 = _costFromStart + _costToObject;
		int a2 = node._costFromStart + node._costToObject;
		if (a1 < a2) {
			return -1;
		} else if (a1 == a2) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * 获得上下左右各点间移动限制区域
	 *
	 * @return 四个临点
	 */
	public LinkedList<Node> getLimit() {
		LinkedList<Node> limit = new LinkedList<>();
		int x = _pos.x;
		int y = _pos.y;
		// 上下左右各点间移动区域(对于斜视地图，可以开启注释的偏移部分，此时将评估8个方位)
		// 上
		limit.add(new Node(new Point(x, y - 1)));
		// 右上
		// limit.add(new Node(new Point(x+1, y-1)));
		// 右
		limit.add(new Node(new Point(x + 1, y)));
		// 右下
		// limit.add(new Node(new Point(x+1, y+1)));
		// 下
		limit.add(new Node(new Point(x, y + 1)));
		// 左下
		// limit.add(new Node(new Point(x-1, y+1)));
		// 左
		limit.add(new Node(new Point(x - 1, y)));
		// 左上
		// limit.add(new Node(new Point(x-1, y-1)));
		
		return limit;
	}
	
	public int get_costFromStart() {
		return _costFromStart;
	}
	
	public void set_costFromStart(int _costFromStart) {
		this._costFromStart = _costFromStart;
	}
	
	public int get_costToObject() {
		return _costToObject;
	}
	
	public void set_costToObject(int _costToObject) {
		this._costToObject = _costToObject;
	}

}