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
   
   //1: Black Player 2: White Player
   void update(Move m){
      currentNode.board.grid[m.getX1()][m.getY1()] = 1;
      currentNode.board.grid[m.getX2()][m.getY2()] = 1;
   }

   private int[] alphaBeta(Node curNode, int searchDepth, int alpha, int beta, boolean maxPlayer){
      int[] tempVal = {0,0};//{curValue, locOfValue}
      
      if(searchDepth == 0 || curNode.legalMoves.size() == 0){
         tempVal[0] = curNode.evalNode();
         return tempVal;
      }
      
      if(maxPlayer){
         tempVal[0] = Integer.MIN_VALUE;
         
         for(int i = 0; i < curNode.legalMoves.size(); i++){        
            tempVal[0] = Math.max(tempVal[0], alphaBeta(curNode.children.get(i), searchDepth - 1, alpha, beta, false)[0]);
            tempVal[1] = i;
            alpha = Math.max(alpha, tempVal[0]);
            
            if(beta <= alpha){
               break;
            }
         }
         
         return tempVal;
      }else{//minPlayer
         tempVal[0] = Integer.MAX_VALUE;
         
         for(int i = 0; i < curNode.legalMoves.size(); i++){    
            tempVal[0] = Math.min(tempVal[0], alphaBeta(curNode.children.get(i), searchDepth - 1, alpha, beta, true)[0]);
            tempVal[1] = i;
            beta = Math.min(beta, tempVal[0]);
            
            if(beta <= alpha){
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
      
      //Breaking longer chains of opponets increases value
      //Increasing chains of your own also increases value
      public int evalNode(){
         int[][] curGrid = board.getGrid();
         int result = 0;
         int tmpRes = 0;
         int maxRes = 0;
         Move mov;
         Move bestMov;
         
         for(int i=0;i<legalMoves.size();i++){
            mov = legalMoves.get(i);
            tmpRes = nodeHeu(new int[]{mov.getX1(), mov.getY1()});
            
            if(tmpRes > maxRes){
               bestMov = mov;
               maxRes = tmpRes;
            }
            
            result += tmpRes;
         }
                
         return result;
      }
      
      //Breaking longer chains of opponets increases value
      //Increasing chains of your own also increases value
      public int nodeHeu(int[] testPos){
         int[][] curGrid = board.getGrid();
         int result = 0;
         int tmpRes = 0;
          
         result += calcValue(chainLength(curGrid, testPos, 0), chainLength(curGrid, testPos, 4));
         result += calcValue(chainLength(curGrid, testPos, 2), chainLength(curGrid, testPos, 6));
         result += calcValue(chainLength(curGrid, testPos, 1), chainLength(curGrid, testPos, 5));
         result += calcValue(chainLength(curGrid, testPos, 3), chainLength(curGrid, testPos, 7));
                  
         return result;
      }
      
      //returning -1 means game over
      private int calcValue(int[] one, int[] two){
         int result = 0;
         int tmpLen = 0;
         
         if(one[0] == two[0]){
            tmpLen = one[1] + two[1];
            
            /*if(tmpLen >= 5){
               result += -1;
            }else */if((tmpLen + one[2] + two[2]) >= 5){ //check if it is even possible to 
               result += tmpLen;
            }  
         }else{
            /*if(one[1] == 5){
               result += -1;
            }else */if((one[1] + one[2]) >= 5){ //check if it is even possible to 
               result += one[1];
            }  
            
            /*if(two[1] == 5){
               result += -1;
            }else */if((two[1] + two[2]) >= 5){ //check if it is even possible to 
               result += two[1];
            }  
         }
         
         return result;
      }
      
      //direction = 0: D, 1:L-D, 2:L, 3: L-U, 4: U, 5: R-U, 6: R, 7: R-D
      private int[] chainLength(int[][] grid, int[] loc, int direction){
         int lMod = 0; //U-D
         int rMod = 0; //L-R
         int len = 0; //Length of chain of nodes specified
         int capped = 0;
         
         switch (direction) {
            case 0:  lMod = -1;
                     break;
            case 1:  lMod = -1;
                     rMod = -1;
                     break;
            case 2:  rMod = -1;
                     break;
            case 3:  lMod = 1;
                     rMod = -1;
                     break;
            case 4:  lMod = 1;
                     break;
            case 5:  lMod = 1;
                     rMod = 1;
                     break;
            case 6:  rMod = 1;
                     break;
            case 7:  lMod = -1;
                     rMod = 1;
                     break;
            default: return new int[]{0,0,0};
         }
         
         int searchId = grid[loc[0] + lMod][loc[1] + rMod];
         int currentId = searchId;
         
         if(searchId != 0){
            while(currentId == searchId){
               len++;
               loc[0] += rMod;
               loc[1] += lMod;
               currentId = grid[loc[0]][loc[1]];
            }
         }
         
         //gives all empty spaces after chain up to 6 that are open
         while((currentId == 0) || ((capped + len) == 6)){
            capped++;
            loc[0] += rMod;
            loc[1] += lMod;
            currentId = grid[loc[0]][loc[1]];
         }
         
         return new int[]{searchId, len, capped};
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
         for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
               if(grid[i][j] == 0 && 
               ((isInGrid(i+1,j) && grid[i+1][j] > 0) ||
                (isInGrid(i-1,j) && grid[i-1][j] > 0) ||
                (isInGrid(i,j+1) && grid[i][j+1] > 0) ||
                (isInGrid(i,j-1) && grid[i][j-1] > 0) ||
                (isInGrid(i-1,j-1) && grid[i-1][j-1] > 0) ||
                (isInGrid(i+1,j+1) && grid[i+1][j+1] > 0) ||
                (isInGrid(i-1,j+1) && grid[i-1][j+1] > 0) ||
                (isInGrid(i+1,j-1) && grid[i+1][j-1] > 0))){
                  firstPassRes.add(new Move(i,j,-1,-1));
               }
            }
         }
         
         //second stone
         for(int k = 0; k < firstPassRes.size(); k++){
            Move curMove = firstPassRes.get(k);
            grid[curMove.getX1()][curMove.getY1()] = 2;
            
            for(int i = 0; i < boardSize; i++){
               for(int j = 0; j < boardSize; j++){
                  if(grid[i][j] == 0 && 
                  ((isInGrid(i+1,j) && grid[i+1][j] > 0) ||
                   (isInGrid(i-1,j) && grid[i-1][j] > 0) ||
                   (isInGrid(i,j+1) && grid[i][j+1] > 0) ||
                   (isInGrid(i,j-1) && grid[i][j-1] > 0) ||
                   (isInGrid(i-1,j-1) && grid[i-1][j-1] > 0) ||
                   (isInGrid(i+1,j+1) && grid[i+1][j+1] > 0) ||
                   (isInGrid(i-1,j+1) && grid[i-1][j+1] > 0) ||
                   (isInGrid(i+1,j-1) && grid[i+1][j-1] > 0))){
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
      
      protected int[][] getGrid(){
         return grid;
      }
      
      private boolean isInGrid(int i, int j){
      //Check if a position is valid in the grid
         if(i < 0 ||j < 0) 
            return false;
         if(i >= boardSize || j >= boardSize) 
            return false;
         return true;
      }
   }
}