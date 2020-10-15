package com.assignment.controller;

import com.assignment.controller.entity.MinerData;

import java.util.ArrayList;
import java.util.List;

public class Util {
    private static int countMinerAroundBoard(boolean[][] data, int row, int col) {
        int i, j;
        int nearMinium = 0;
        for (i = -1; i < 2; i++) {
            for (j = -1; j < 2; j++) {
                if (row + i >= 0 && row + i < data.length && col + j >= 0 && col + j < data[0].length) {
                    if(i == row && col == j){
                        continue;
                    }
                    if(data[row][col] == true){
                        return 1;
                    }
                    if (data[row + i][col + j] == true){
                        nearMinium += 1;
                    }
                }
            }
        }
        return nearMinium;
    }

    public static int getTotalMiner(List<List<Boolean>> data){
        int count = 0;
        for(List<Boolean> d : data){
            for(Boolean miner : d){
                if(miner == true){
                    count++;
                }
            }
        }
        return count;
    }

    public static MinerData getBoardResult(List<List<Boolean>> data){
        int[][] result = new int[data.size()][data.get(0).size()];
        boolean[][] board = buildBoolean2D(data);
        for(int i = 0; i < data.size(); i++){
            for(int j = 0; j < data.get(i).size(); j++){
                result[i][j] = countMinerAroundBoard(board, i, j);
            }
        }
        return response(result);
    }

    private static boolean[][] buildBoolean2D(List<List<Boolean>> data){
        boolean[][] boardGame = new boolean[data.size()][data.get(0).size()];
        for(int i = 0; i < data.size(); i++){
            for(int j = 0; j < data.get(i).size(); j++){
                boardGame[i][j] = data.get(i).get(j);
            }
        }
        return boardGame;
    }

    private static MinerData response(int[][] result){
        MinerData data = new MinerData();
        for(int i = 0; i < result.length; i++){
            List<Integer> line = new ArrayList<>();
            for(int j = 0; j < result[i].length; j++){
                line.add(result[i][j]);
            }
            data.add(line);
        }
        return data;
    }
}
