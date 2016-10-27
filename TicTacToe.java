/**
 * @(#)TicTacToe.java
 *
 *
 * @author 
 * @version 1.00 2016/10/27
 */
import javax.swing.*;
import java.awt.Color;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
public class TicTacToe {
        
    /**
     * Creates a new instance of <code>TicTacToe</code>.
     */
    JFrame gameInterface;
    JPanel boardPanel;
    JLabel squaresPanels[][];
    int board[][];
    int currentSide = 1; //1=X
    boolean gameOver=false;
    public TicTacToe() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TicTacToe GAME_RUNNER_OBJECT = new TicTacToe();
        GAME_RUNNER_OBJECT.pseudomain();
    }
    
    public void pseudomain(){
    	board=new int[3][3];
    	createBoardWindow();
    }
    
    public void createBoardWindow(){
    	JFrame gameInterface = new JFrame();
        gameInterface.setTitle("Tic Tac Toe");
        
        gameInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameInterface.setFocusable(true);
        gameInterface.requestFocus();

        JPanel boardPanel = new JPanel();
        boardPanel.setMinimumSize(new Dimension(300,300));
        boardPanel.setMaximumSize(new Dimension(300,300));
        boardPanel.setPreferredSize(new Dimension(300,300));
        boardPanel.setSize(300,300);
        boardPanel.setLayout(new GridLayout(3,3,0,0));
        
    	squaresPanels = new JLabel[3][3];
        for(int i=0; i<3; i++){
        	for(int j=0; j<3; j++){
        		squaresPanels[i][j] = new JLabel();
        		squaresPanels[i][j].setOpaque(true);
        		squaresPanels[i][j].setSize(new Dimension(100,100));
        		squaresPanels[i][j].setMinimumSize(new Dimension(100,100));
        		squaresPanels[i][j].setMaximumSize(new Dimension(100,100));
        		squaresPanels[i][j].setPreferredSize(new Dimension(100,100));
        		squaresPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
        		squaresPanels[i][j].setBackground(Color.WHITE);
        		squaresPanels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				squaresPanels[i][j].setVerticalAlignment(SwingConstants.CENTER);
        		final int tempI = i;
        		final int tempJ = j;
        		squaresPanels[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
        			final int myI=tempI;
        			final int myJ=tempJ;
		            public void mouseClicked(java.awt.event.MouseEvent evt) {
		                clickedOn(myI,myJ);
		            }
		        });
        		boardPanel.add(squaresPanels[i][j]);
        		
        	}
        }
        gameInterface.add(boardPanel);
        gameInterface.pack();
        gameInterface.setResizable(false);
        gameInterface.setVisible(true);
    }
    public void updatePieceDisplay(){
    	for(int i=0; i<3; i++){
        	for(int j=0; j<3; j++){
        		if(board[i][j]==1){
        			try{
        				squaresPanels[i][j].setFont(new Font(squaresPanels[i][j].getFont().getName(), squaresPanels[i][j].getFont().getStyle(), 125));
        				squaresPanels[i][j].setText("X");
        			} catch (NullPointerException e) {
    					e.printStackTrace();
    				}
        		} else if(board[i][j]==-1){
        			try{
        				squaresPanels[i][j].setFont(new Font(squaresPanels[i][j].getFont().getName(), squaresPanels[i][j].getFont().getStyle(), 125));
        				squaresPanels[i][j].setText("O");
        			} catch (NullPointerException e) {
    					e.printStackTrace();
    				}
        		} else {
        			squaresPanels[i][j].setFont(new Font(squaresPanels[i][j].getFont().getName(), squaresPanels[i][j].getFont().getStyle(), 125));
        			squaresPanels[i][j].setText(" ");
        		}
        	}
        }
    }
    private void clickedOn(int i, int j){
    	if (board[i][j]==0&&!gameOver){
    		board[i][j]=currentSide;
    		updatePieceDisplay();
    		int winner=testWinner(board);
    		if(winner==1){
    			System.out.println("X wins!");
    			gameOver=true;
    		}
    		if(winner==-1){
    			System.out.println("O wins!");
    			gameOver=true;
    		}
    		
    		if(!gameOver){
    			currentSide*=-1;
	    		int[] coord = aiWinOrRandom(board, currentSide);
	    		board[coord[0]][coord[1]]=currentSide;
	    		updatePieceDisplay();
	    		winner=testWinner(board);
	    		if(winner==1){
	    			System.out.println("X wins!");
	    			gameOver=true;
	    		}
	    		if(winner==-1){
	    			System.out.println("O wins!");
	    			gameOver=true;
	    		}
	    		currentSide*=-1;
    		}
    		
    	}
    }
    private int testWinner(int[][] arr){
    	for(int i=0; i<3; i++){
    		if(arr[i][0]==arr[i][1]&&arr[i][1]==arr[i][2]&&arr[i][0]!=0){
    			return arr[i][0];
    		}
    		if(arr[0][i]==arr[1][i]&&arr[1][i]==arr[2][i]&&arr[0][i]!=0){
    			return arr[0][i];
    		}
    	}
    	if(((arr[0][0]==arr[1][1]&&arr[0][0]==arr[2][2])||(arr[0][2]==arr[1][1]&&arr[1][1]==arr[2][0]))&&arr[1][1]!=0){
    		return arr[1][1];
    	}
    	return 0;
    }
    public int[] aiRandom(int arrTemp[][], int side){
    	
    	int[][] arr=new int[3][3];
    	for(int i=0; i<3 ;i++){
    		for(int j=0; j<3 ;j++){
    			arr[i][j]=arrTemp[i][j];
    		}
    	}
    	ArrayList<Integer> order = new ArrayList<Integer>();
    	for(int i=0;i<9;i++){
    		order.add(i);
    	}
    	Collections.shuffle(order);
    	for(int i:order){
    		int r=i%3;
    		int c=i/3;
    		if (arr[r][c]==0){
    			int[] out = {r,c};
    			return out;
    		}
    	}
    	int[] out = {-1,-1};
    	return out;
    }
    //If the AI can make a winning move, it will. Otherwise, picks a random move.
    public int[] aiWinOrRandom(int arrTemp[][], int side){
    	int[][] arr=new int[3][3];
    	for(int i=0; i<3 ;i++){
    		for(int j=0; j<3 ;j++){
    			arr[i][j]=arrTemp[i][j];
    		}
    	}
    	ArrayList<Integer> order = new ArrayList<Integer>();
    	for(int i=0;i<9;i++){
    		order.add(i);
    	}
    	Collections.shuffle(order);
    	double maxFit=-99999;
    	int maxR=-1;
    	int maxC=-1;
    	for(int i:order){
    		int r=i%3;
    		int c=i/3;
    		if (arr[r][c]==0){
	    		int[][] arr2=new int[3][3];
		    	for(int a=0; a<3 ;a++){
		    		for(int b=0; b<3 ;b++){
		    			arr2[a][b]=arrTemp[a][b];
		    		}
		    	}
		    	arr2[r][c]=side;
		    	int winner=testWinner(arr2);
		    	double fit=0;
		    	if (testWinner(arr2)==0){
		    		fit=0;
		    	} else {
		    		fit=winner*side;
		    	}
	    		if (fit>maxFit){
					maxR=r;
					maxC=c;
					maxFit=fit;
	    		}
    		}
    	}
    	int[] out = {maxR,maxC};
    	return out;
    }
//    public double searchFit(int arrTemp[][], int side){
//    	int[][] arr=new int[3][3];
//    	for(int i=0; i<3 ;i++){
//    		for(int j=0; j<3 ;j++){
//    			arr[i][j]=arrTemp[i][j];
//    		}
//    	}
//    	
//    	for(int i:order){
//    		int r=i%3;
//    		int c=i/3;
//    		double fit=calculateSearchFit(arr,side);
//    		if (fit>maxFit){
//				maxR=r;
//				maxC=c;
//				maxFit=fit;
//    		}
//    	}
//    }
}
