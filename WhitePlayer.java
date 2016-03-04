import java.util.*;
public class WhitePlayer {
   Node currentNode = null;
   int boardSize = -1;
   int maxTimePerMove = -1;

   public WhitePlayer(String player_name, int boardSize, int maxTimePerMove){
      currentNode = new Node(new Board(boardSize));
      this.boardSize = boardSize;
      this.maxTimePerMove = maxTimePerMove;    
   }
//write this method and any other methods that you need
   Move getMove(){
   //alphaBeta(currentState, searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
      return null;
   }		 
   void update(Move m){
      currentNode.board.grid[m.getX1()][m.getY1()] = 1;
      currentNode.board.grid[m.getX2()][m.getY2()] = 1;
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
         List<Move> firstPassRes = new ArrayList();
         //first stone
         for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
               if(grid[i][j] == 0 && 
               ((isInGrid(i+1,j) && grid[i+1][j] > 0) ||
                (isInGrid(i-1,j) && grid[i-1][j] > 0) ||
                (isInGrid(i,j+1) && grid[i][j+1] > 0) ||
                (isInGrid(i,j-1) && grid[i][j-1] > 0))){
                  firstPassRes.add(new Move(i,j,-1,-1));
               }
            }
         }
         //second stone
         for(int k = 0; k < firstPassRes.size(); k++){
            Move curMove = firstPassRes.get(k);
            grid[curMove.getX1()][curMove.getY1()] = 2;        
            for(int i = 0; i < boardSize; i++) {
               for(int j = 0; j < boardSize; j++) {
                  if(grid[i][j] == 0 && 
                  ((isInGrid(i+1,j) && grid[i+1][j] > 0)||
                  (isInGrid(i-1,j) && grid[i-1][j] > 0) ||
                  (isInGrid(i,j+1) && grid[i][j+1] > 0) ||
                  (isInGrid(i,j-1) && grid[i][j-1] > 0))){
                     result.add(new Move(curMove.getX1(),curMove.getX2(),i,j));
                  }
               }
            }
            grid[curMove.getX1()][curMove.getY1()] = 0;
         }
         return result;
      }
      
      protected void applyMove(Move move){
         grid[move.getX1()][move.getY1()] = 2;
         grid[move.getX2()][move.getY2()] = 2;
         return;
      }
      
      private boolean isInGrid(int i, int j) {
      //Check if a position is valid in the grid
         if(i < 0 ||j < 0) 
            return false;
         if(i >= boardSize || j >= boardSize) 
            return false;
         return true;
      }
   }
}