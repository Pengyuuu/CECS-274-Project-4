/**
 * Name: Eric Truong
 * Date: December 2, 2019
 * Description: Read in 4 mazes, prompt user if they want to solve it with DFS or BFS, then print solved maze
 */

import java.io.*;
import java.util.Scanner;
import java.awt.Point;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class MazeGame {

    public static void main (String[] args){

        boolean repeat = true;

        //actual game
        while(repeat) {
            System.out.print("Choose a maze 1-4: ");

            int choice = CheckInput.getIntRange(1, 4);

            char[][] maze = readMaze(choice);

            displayMaze(maze);

            System.out.println("How would you like to solve this maze? \n" +
                    "1.DFS \n" +
                    "2.BFS");

            int selection = CheckInput.getIntRange(1, 2);

            pathWay(selection, maze);

            repeat = repeatGame();
        }
    }

    /**
     * Read in a file and put it into a 2D character array
     * @param choice    user's choice
     * @return          2D character array of maze
     */
    public static char[][] readMaze(int choice){

        String selection = "" + choice;

        int counter = 0;

        int rows = 0;

        int columns = 0;

        //read first line of file to get dimensions
        try{

            Scanner read = new Scanner(new File("maze" + selection + ".txt"));

            do{
                String line = read.nextLine();

                if(counter == 0){

                    String [] dimensions = line.split(" ");

                    rows = Integer.parseInt(dimensions[0]);

                    columns = Integer.parseInt(dimensions[1]);

                    counter++;

                }
            } while(read.hasNext());

            read.close();

        } catch(FileNotFoundException fnf){

            System.out.println("File was not found");

        }

        //create array and read in the maze
        char [][] maze = new char [rows][columns];

        counter = 0;

        int i = -1;

        try{

            Scanner read = new Scanner(new File("maze" + selection + ".txt"));

            do{
                String line = read.nextLine();

                if(counter > 0){

                    for(int j = 0; j < columns; j++){

                        maze[i][j] = line.charAt(j);

                    }
                }

                i++;

                counter++;

            } while(read.hasNext());

            read.close();

        } catch(FileNotFoundException fnf) {

            System.out.println("File was not found");
        }

        return maze;
    }

    /**
     * Displays the maze
     * @param maze  2D character array of maze
     */
    public static void displayMaze(char[][] maze){

        for(int i = 0; i < maze.length; i++){

            System.out.println();

            for(int j = 0; j < maze[0].length; j++){
                System.out.print(maze[i][j]);
            }
        }

        System.out.println();
    }

    /**
     * Finds the starting point of the maze
     * @param maze  2D character array of maze
     * @return      starting point
     */
    public static Point findStart(char[][] maze){

        Point start = new Point();

        for(int i = 0; i < maze.length; i++){

            for(int j = 0; j < maze[0].length; j++){

                if(maze[i][j] == 's'){

                    start = new Point(i, j);
                }
            }
        }

        return start;
    }

    /**
     * Solve's maze using stacks
     * @param maze  2D character array of maze
     */
    public static void solveDFS(char[][] maze){

        Stack <Point> points = new Stack <Point> ();

        points.push(findStart(maze));

        boolean finish = true;

        //psuh in and pop points till it finds the finish
        while(finish){

            Point check = points.pop();

            int row = check.x;

            int column = check.y;

            if(maze[row][column] == 'f'){

                finish = false;
            }
            else{

                maze[row][column] = '.';

                if(!(maze[row + 1][column] == '*' || maze[row + 1][column] == '.')){

                    Point next = new Point(row + 1, column);

                    points.push(next);
                }

                if(!(maze[row - 1][column] == '*'  || maze[row - 1][column] == '.')){

                    Point next = new Point(row - 1, column);

                    points.push(next);
                }

                if(!(maze[row][column + 1] == '*'  || maze[row][column + 1] == '.')){

                    Point next = new Point(row, column + 1);

                    points.push(next);
                }

                if(!(maze[row][column - 1] == '*'  || maze[row][column - 1] == '.')){

                    Point next = new Point(row, column - 1);

                    points.push(next);
                }
            }
        }
    }

    /**
     * Solves maze using queue
     * @param maze  2D character array of maze
     */
    public static void solveBFS(char[][] maze){

        Queue <Point> queuePoints = new LinkedList <Point> ();

        queuePoints.add(findStart(maze));

        boolean finish = true;

        //add in and remove points till it finds the end
        while(finish){

            Point check = queuePoints.remove();

            int row = check.x;

            int column = check.y;

            if(maze[row][column] == 'f'){

                finish = false;
            }
            else {

                maze[row][column] = '.';

                if (!(maze[row + 1][column] == '*' || maze[row + 1][column] == '.')) {

                    Point next = new Point(row + 1, column);

                    queuePoints.add(next);
                }

                if (!(maze[row - 1][column] == '*' || maze[row - 1][column] == '.')) {

                    Point next = new Point(row - 1, column);

                    queuePoints.add(next);
                }

                if (!(maze[row][column + 1] == '*' || maze[row][column + 1] == '.')) {

                    Point next = new Point(row, column + 1);

                    queuePoints.add(next);
                }

                if (!(maze[row][column - 1] == '*' || maze[row][column - 1] == '.')) {

                    Point next = new Point(row, column - 1);

                    queuePoints.add(next);
                }
            }
        }
    }

    /**
     * Player chooses to solve using two options
     * @param selection player's choice
     * @param maze      maze player wants to solve
     */
    public static void pathWay(int selection, char[][] maze){

        if(selection == 1){

            solveDFS(maze);

            displayMaze(maze);
        }
        if(selection == 2){

            solveBFS(maze);

            displayMaze(maze);
        }
    }

    /**
     * Asks user if they want to play again
     * @return  true if player wants to play, false otherwise
     */
    public static boolean repeatGame(){

        System.out.print("Would you like to play again? Y/N: ");

        boolean answer = CheckInput.getYesNo();

        if(answer == true){

            boolean continuation = true;

            return continuation;
        }
        else{

            boolean continuation = false;

            System.out.println("Goodbye!");

            return continuation;
        }

    }
}
