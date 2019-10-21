package astar;

import astar.core.Grid;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import astar.core.Node;

@Slf4j
public class TestMap {
    private MapDataTemplate template = new MapDataTemplate();        //地图网格数据
    private Grid grid;

    private Image floorImage;

    private Image wallImage;

    public TestMap() {

        try{
            floorImage = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("floor.png")));
        } catch(IOException e){
            e.printStackTrace();
        }

        try{
            wallImage = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("wall.png")));
        } catch(IOException e){
            e.printStackTrace();
        }



    }


    public Grid createGrid() {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("testMap.json");

            BufferedReader br1 = new BufferedReader(new InputStreamReader(resourceAsStream));
            String s1;

            while ((s1 = br1.readLine()) != null) {

                if (!"".equals(s1)) {
                    sb.append(s1);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        template = JSON.parseObject(sb.toString(), MapDataTemplate.class);


        Grid grid = new Grid();
        grid.setCellSize(template.getCellSize());
        grid.setCols(template.getCols());
        grid.setDeviationX(template.getDeviationX());
        grid.setDeviationZ(template.getDeviationZ());
        grid.setRows(template.getRows());

        Map<Integer, java.util.List<Node>> nodeMap = new HashMap<>();
        for (GridTemplate gridTmp : template.getNodeList()) {
            int x = gridTmp.getX();
            Node node = new Node(gridTmp.getIndex(), x, gridTmp.getZ());
            node.setPx(gridTmp.getPx());
            node.setPy(gridTmp.getPy());
            node.setPz(gridTmp.getPz());
            node.setWalkable(gridTmp.getWalk() == 1);

            List<Node> list = nodeMap.computeIfAbsent(x, k -> new ArrayList<>());
            list.add(node);
        }
        grid.setNodeMap(nodeMap);
        this.grid = grid;
        return grid;
    }


    public void draw(Graphics g) {
        grid.iter(g,floorImage,wallImage);
    }

}