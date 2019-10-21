package astar;

import astar.core.Grid;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class PathFinder {
	
	// 路径优先等级list(此示例中为内部方法)
	private PriorityQueue<Node> _openList;
	
	// 已完成路径的list
	private LinkedList<Node> _closedList;
	
	// 地图描述
	private Grid grid;
	
	
	/**
	 * 以注入地图的2维数组及限制点描述初始化此类
	 *
	 * @param map
	 */
	public PathFinder(Grid map) {
		grid = map;
		_openList = new PriorityQueue<>(512,Node::compareTo);
		_closedList = new LinkedList<>();
	}
	
	/**
	 * A*方式寻径,注入开始坐标及目标坐标后运算,返回可行路径的List
	 *
	 * @param startPos
	 * @param targetPos
	 * @return
	 */
	public List<Node> searchPath(Point startPos, Point targetPos) {
		// 初始化起始节点与目标节点
		Node startNode = new Node(startPos);
		Node objectNode = new Node(targetPos);
		// 设定起始节点参数
		startNode.set_costFromStart(0);
		startNode.set_costToObject(startNode.getCost(objectNode));
		startNode._parentNode = null;
		// 加入运算等级序列
		_openList.offer(startNode);
		// 当运算等级序列中存在数据时，循环处理寻径，直到levelList为空
		while (!_openList.isEmpty()) {
			// 取出并删除最初的元素
			Node firstNode =  _openList.poll();
			// 判定是否和目标node坐标相等
			if (firstNode.equals(objectNode)) {
				// 是的话即可构建出整个行走路线图，运算完毕
				return makePath(firstNode);
			} else {
				// 否则
				// 加入已验证List
				_closedList.add(firstNode);
				// 获得firstNode的移动区域
				LinkedList<Node> _limit = firstNode.getLimit();
				// 遍历
				for (int i = 0; i < _limit.size(); i++) {
					// 获得相邻节点
					Node neighborNode = _limit.get(i);
					// 获得是否满足等级条件
					boolean isOpen = _openList.contains(neighborNode);
					// 获得是否已行走
					boolean isClosed = _closedList.contains(neighborNode);
					// 判断是否无法通行
					boolean canMove = canMove(neighborNode._pos.x, neighborNode._pos.y);
					// 当三者判定皆非时
					if (!isOpen && !isClosed && canMove) {
						// 设定costFromStart
						neighborNode.set_costFromStart(firstNode.get_costFromStart() + 1);
						// 设定costToObject
						neighborNode.set_costToObject(neighborNode.getCost(objectNode));
						// 改变neighborNode父节点
						neighborNode._parentNode = firstNode;
						// 加入level
						_openList.offer(neighborNode);
					}
				}
			}
			
		}
		_closedList.clear();
		// 当while无法运行时，将返回null
		return null;
	}
	
	/**
	 * 判定是否为可通行区域
	 *
	 * @param x x
	 * @param y y
	 * @return 能否移动
	 */
	private boolean canMove(int x, int y) {
		if(x<0||y<0){
			return false;
		}
		astar.core.Node node=grid.getNode(x,y);
		if(node==null){
			return false;
		}
		return grid.getNode(x,y).isWalkable();
	}
	
	/**
	 * 通过Node制造行走路径
	 *
	 * @param node 终点
	 * @return 经过路径
	 */
	private LinkedList<Node> makePath(Node node) {
		LinkedList<Node> path = new LinkedList<>();
		// 当上级节点存在时
		while (node._parentNode != null) {
			// 在第一个元素处添加
			path.addFirst(node);
			// 将node赋值为parent node
			node = node._parentNode;
		}
		// 在第一个元素处添加
		path.addFirst(node);
		return path;
	}
}