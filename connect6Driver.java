import java.io.*;
import java.util.*;
/* Driver program for 2-person game containing black and white player.
The first argument is the size of the board. This will be followed by first player (black player)'s 
details. This will be -h or -c followed by a name, representing the name of human or AI computer 
program.This will be following second player's details. 

The interface can also be modified by each team. 
The player classes BlackPlayer and WhitePlayer are supplied by the teams. 

The program takes following arguments
size of the board
-h or -c for first player
name of first player
-h or -c for second player 
name of second player.

Try "java Connect6Driver 7 -h Kanchi -h Tim" 
to see it run

*/


class Connect6Driver{
         static final int MAX_TIME_PER_MOVE = 5;
         static Scanner sc= new Scanner(System.in);
		 
		 public static void main(String[] args){
		     int boardSize = Integer.parseInt(args[0]);
		     Game gameBoard = new Game(boardSize);
             BlackPlayer blackPlayer= null;
             WhitePlayer whitePlayer= null;
		     int maxTimePerMove = MAX_TIME_PER_MOVE;
			 
		   if (args[1].equals("-c"))        
                 blackPlayer= new BlackPlayer(args[2], boardSize, maxTimePerMove);
				 
				 
          if (args[3].equals("-c"))
                whitePlayer = new WhitePlayer(args[4],boardSize, maxTimePerMove); 
				
			
		 
		 
             Move currentMove = null;
             boolean done = false;
             String turn = "white";
			 gameBoard.printBoard();
			 
             while (!done){
               if (turn.equals("black")) 
                   if (blackPlayer != null)              
                      currentMove = blackPlayer.getMove();
                   else
				     
                      currentMove = getHumanMove(args[2]);
               else
                     if (whitePlayer != null)              
                      currentMove = whitePlayer.getMove();
                   else
                      currentMove = getHumanMove(args[4]);
               
                           
                if (gameBoard.isIllegalMove(currentMove)){ 
                      done = true;
                      System.out.println("Illegal move is made");
               }                                
               else{
                     gameBoard.update(currentMove, turn);
                     gameBoard.printBoard();
                     sc.nextLine();
                     if (gameBoard.isGameOver()){
                        done =true;
                        //printMessageAboutTheWinner;
                     } 
                     else{
                         if (turn.equals("black")){
						       whitePlayer.update(currentMove);
                               turn = "white";
						 }
                         else{
						       blackPlayer.update(currentMove);
                               turn = "black";
					     }
                    }
               }
             //  if (move exceeded maxTimePerMove)
              //               done = true;
                             //printAppropriateMessage;
            } //end while
		}
 
         public static Move getHumanMove(String name){
          System.out.println("Hello  " + name + "\n provide x1 y1 x2 y2 for choosing (x1,y1) and (x2,y2)");
          int x1 = sc. nextInt();
          int y1 = sc. nextInt();
          int x2 = sc. nextInt();
          int y2 = sc. nextInt();
          Move m = new Move(x1,y1,x2,y2);
          return(m);
         }

              
           
}// end class
               



