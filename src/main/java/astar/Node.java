package astar;

import java.util.LinkedList;
import java.util.Objects;


public class Node implements Comparable<Node> {
    // 父节点
    public Node parentNode;
    // 开始地点数值
    private int costFromStart;
    // 目标地点数值
    private int costToTarget;
    private int index;          //结点索引，先列后行
    private int x, y;     //结点坐标

    private float px;                //中心位置世界坐标X坐标
    private float py;                //中心位置世界坐标高度
    private float pz;                //中心位置世界坐标Z坐标（二维坐标中的Y轴）

    private boolean walkable = true;        //是否为障碍物
    private boolean tankOccupy = false;        //是否有坦克占据


    /**
     * 以注入坐标点方式初始化Node
     */
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 返回路径成本
     *
     * @param node node
     * @return 成本
     */
    public int getCost(Node node) {
        // 获得坐标点间差值 公式：(x1, y1)-(x2, y2)
        int m = node.x - x;
        int n = node.y - y;
        // 取两节点间欧几理德距离（直线距离）做为估价值，用以获得成本
        return (int) Math.sqrt(m * m + n * n);
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y, costFromStart, costToTarget);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    /**
     * 比较两点以获得最小成本对象
     */
    public int compareTo(Node node) {
        int a1 = costFromStart + costToTarget;
        int a2 = node.costFromStart + node.costToTarget;
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

        // 上下左右各点间移动区域(对于斜视地图，可以开启注释的偏移部分，此时将评估8个方位)
        // 上
        limit.add(new Node(x, y - 1));
        // 右上
        limit.add(new Node(x + 1, y - 1));
        // 右
        limit.add(new Node(x + 1, y));
        // 右下
        limit.add(new Node(x + 1, y + 1));
        // 下
        limit.add(new Node(x, y + 1));
        // 左下
        limit.add(new Node(x - 1, y + 1));
        // 左
        limit.add(new Node(x - 1, y));
        // 左上
        limit.add(new Node(x - 1, y - 1));

        return limit;
    }

    public int getCostFromStart() {
        return costFromStart;
    }

    public void setCostFromStart(int costFromStart) {
        this.costFromStart = costFromStart;
    }

    public int getCostToTarget() {
        return costToTarget;
    }

    public void setCostToTarget(int costToTarget) {
        this.costToTarget = costToTarget;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getPx() {
        return px;
    }

    public void setPx(float px) {
        this.px = px;
    }

    public float getPy() {
        return py;
    }

    public void setPy(float py) {
        this.py = py;
    }

    public float getPz() {
        return pz;
    }

    public void setPz(float pz) {
        this.pz = pz;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isTankOccupy() {
        return tankOccupy;
    }

    public void setTankOccupy(boolean tankOccupy) {
        this.tankOccupy = tankOccupy;
    }
}