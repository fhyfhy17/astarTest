package astar;

import astar.core.AStar;
import astar.core.Grid;
import astar.core.Node;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.List;


public class Main extends Panel {
    final static private int WIDTH = 720;

    final static private int HEIGHT = 720;

    final static private int CS = 8;

    private TestMap map;

    private AStar astar;
    
    private Grid grid;

    // 起始坐标1,1
    private static Point START_POS = new Point(24, 24);

    // 目的坐标10,13
    private static Point OBJECT_POS = new Point(55, 55);

    private Image screen;

    private Graphics graphics;

    private List<Node> path;

    public Main() {

        setSize(WIDTH, HEIGHT);
        setFocusable(true);
        screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = screen.getGraphics();
        map = new TestMap();
//        // 注入地图描述及障碍物描述
		grid=map.createGrid();
		astar = new AStar(grid);
  
//        // searchPath将获得两点间移动路径坐标的List集合
//        // 在实际应用中，利用Thread分步处理List中坐标即可实现自动行走
        path = astar.findPath(new Node(START_POS.x, START_POS.y), new Node(OBJECT_POS.x, OBJECT_POS.y));
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        // 绘制地图
        map.draw(graphics);
        graphics.setColor(Color.RED);
        graphics.fillRect(START_POS.x * CS, START_POS.y * CS, CS, CS);

        graphics.setColor(Color.BLUE);
        graphics.fillRect(OBJECT_POS.x * CS, OBJECT_POS.y * CS, CS, CS);
	
        //三种求 两个点之前的线段经过了哪些格子的算法
		
        //这三种算法理论上都应该是可以的
        
		//List<Node> line=grid.bresenham(START_POS.x,START_POS.y,OBJECT_POS.x,OBJECT_POS.y);
		List<Node> line=grid.supercover(START_POS.x,START_POS.y,OBJECT_POS.x,OBJECT_POS.y);
		
		//Node node1=grid.getNode(START_POS.x,START_POS.y);
		//Node node2=grid.getNode(OBJECT_POS.x,OBJECT_POS.y);
		//List<Node> line=grid.hadBarrier(node1.getPx(),node1.getPz(),node2.getPx(),node2.getPz());
		
		// 绘制路径
        if (line != null) {
            graphics.setColor(Color.YELLOW);
            // 遍历坐标，并一一描绘
            for (Node node : line) {
                // 描绘边框
                graphics.fillRect(node.getX() * CS + 2, node.getY() * CS + 2, CS - 4, CS - 4);
            }
            System.out.println("一共 "+ line.size() +" 个点");
        }
        g.drawImage(screen, 0, 0, this);
    }

    public static void main(String[] args) {

        Frame frame = new Frame();
        frame.setTitle("A*寻路");
        frame.setSize(WIDTH, HEIGHT + 20);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(new Main());
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
