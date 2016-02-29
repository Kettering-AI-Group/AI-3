import java.util.*;
public class WhitePlayer {
// write this class.. Add variables and anything else you need
   public WhitePlayer(String player_name, int boardSize, int maxTimePerMove){
           
   }
//write this method and any other methods that you need
   Move getMove(){
   //alphaBeta(currentState, searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
      return null;
   }		 
   void update(Move m){
   	 
   }

   private int[] alphaBeta(Node curNode, int searchDepth, int alpha, int beta, boolean maxPlayer) {
      int[] tempVal = {0,0};//{curValue, locOfValue}
      if(searchDepth == 0 || curNode.legalMoves.size() == 0) {
         tempVal[0] = curNode.evalNode();
         return tempVal;
      }
      if(maxPlayer == true) {
         tempVal[0] = Integer.MIN_VALUE;
         for(int i = 0; i < curNode.legalMoves.size(); i++) {        
            tempVal[0] = Math.max(tempVal[0], alphaBeta(curNode.children.get(i), searchDepth - 1, alpha, beta, false)[0]);
            tempVal[1] = i;
            alpha = Math.max(alpha, tempVal[0]);
            if(beta <= alpha) {
               break;
            }
         }
         return tempVal;
      } 
      else {
         tempVal[0] = Integer.MAX_VALUE;
         for(int i = 0; i < curNode.legalMoves.size(); i++) {    
            tempVal[0] = Math.min(tempVal[0], alphaBeta(curNode.children.get(i), searchDepth - 1, alpha, beta, true)[0]);
            tempVal[1] = i;
            beta = Math.min(beta, tempVal[0]);
            if(beta <= alpha) {
               break;
            }
         }
         return tempVal;
      }
   }
   
   class Node {
      Board board;
      List<Node> children;
      List<Move> legalMoves;
   
      protected Node(Board myBoard){
         board = myBoard;
         children = new ArrayList();
         legalMoves = board.genMoves();
      }
      
      private void genChild(Move move){
         Board newBoard = board;
         newBoard.applyMove(move);
         Node child = new Node(newBoard);
         children.add(child);
      }
      
      private int evalNode(){
         int result = -1;
         return result;
      }
   
   }
   
   class Board {
      int[][] grid;
   
      protected Board(int boardSize){
         grid = new int[boardSize][boardSize];
      }
      
      protected List<Move> genMoves(){
         List<Move> result = new ArrayList();
         return result;
      }
      
      protected void applyMove(Move move){
         grid[move.getX1()][move.getY1()] = 2;
         grid[move.getX2()][move.getY2()] = 2;
         return;
      }
   }
}