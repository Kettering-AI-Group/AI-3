
public class Game{ 
   final int BOARD_SIZE;
   int[][] connect6;
   public Game(int size){
            BOARD_SIZE = size;
            connect6= new int[BOARD_SIZE][BOARD_SIZE];
            for (int i = 0; i < BOARD_SIZE; i++){
                  for (int j = 0; j < BOARD_SIZE; j++){
                           connect6[i][j] = 0;
                  }
            } 
            connect6[BOARD_SIZE/2][BOARD_SIZE/2] = 1;
         
    }

      public void printBoard(){
	   System.out.println(); 
	    
		    System.out.print("           " );
			for (int j = 0; j < BOARD_SIZE; j++)
			   System.out.print(j + "   ");
			   System.out.println(); 
			   System.out.println(); 
            for (int i = 0; i < BOARD_SIZE; i++){
			      System.out.print(i + ":         " );
                  for (int j = 0; j < BOARD_SIZE; j++){
                           System.out.print(connect6[i][j] + "   " ); 
                  }
                           System.out.println(); 
            } 
		System.out.println();
		
     }


     public int update(Move m, String player){
             if((connect6[m.getX1()][m.getY1()] != 0) ||  
                (connect6[m.getX2()][m.getY2()] != 0))
               return(-1); 
			   if (player.equals("black")){
             connect6[m.getX1()][m.getY1()] = 1; 
             connect6[m.getX2()][m.getY2()] = 1;
			 }
               else	{		 
			  connect6[m.getX1()][m.getY1()] = 2; 
              connect6[m.getX2()][m.getY2()] = 2;
             }			 
       //      currentState = new State(connect6, BOARD_SIZE);
             return 0;
    }
    public boolean isIllegalMove(Move m) {
	// write this method
	
	    return(false);
    }
  public boolean isGameOver(){
              // write this method
          return(false);
      
   }

} // end class
