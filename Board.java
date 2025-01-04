import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private static final int SIZE =3;
    private static final int CostRED =10;
    private static final int CostBLUE =1;
    private static final int CostGREEN= 3;
    private char[][] goalBoard;
    private int lastFromI= -1;
    private int lastFromJ= -1;
    private int lastToI =-1;
    private int lastToJ =-1;

    // constractor to init the goal board

    public Board(char[][] goalBoard) {
        this.goalBoard = goalBoard;
    }

    public static int numState=0;// counter
    
// deep copy of a borad
    public char[][] copyBoard(char[][] board) {
        char[][] copyB = new char[SIZE][SIZE];

        for (int i =0; i <SIZE; i++) {
            copyB[i] =Arrays.copyOf(board[i], board[i].length);
        }
        return copyB;
    }

    public int getMoveCost(char value) {
        switch (value) {
            case 'B': return CostBLUE;
            case 'R': return CostRED;
            case 'G': return CostGREEN;
            default:return 0;
        }
    }
// Generates all possible next states from the current state.
    public List<State> getNextStates( State curr) {
        List<State> nextStates =new ArrayList<>();
        numState=0;
        char[][] board =curr.getBoard();
        for (int i =0; i <SIZE; i++) {
            for (int j =0; j <SIZE; j++) {
                if (board[i][j]!= '_' && board[i][j] != 'X') {

                    movePoint(nextStates,board, i,j, curr);
                }
            }
        }
        return nextStates ;

    }
   //Checks if the move is the opposite of the last move.

    private boolean isOppositeMove(int fromI,int fromJ, int toI,int toJ) {
        if (lastFromI== -1) { return false; }

        return (fromI == lastToI && fromJ ==lastToJ &&toI == lastFromI && toJ== lastFromJ);
    }

    private void movePoint(List<State> states, char[][] board, int i, int j, State curr) {
        int[][] directions = {{-1,0},  {0,1}, {1,0}, {0,-1}};
        for (int[] direction : directions) {
            int newI =(i + direction[0] + SIZE) % SIZE;
            int newJ =(j + direction[1] +  SIZE) % SIZE;

            if (isOppositeMove(i, j, newI, newJ)) { continue; }

            if (board[newI][newJ]== '_') {
                char[][] newBoard =copyBoard(board);
                newBoard[newI][newJ] =board[i][j];
                newBoard[i][j] ='_';
                int costOfMove =getMoveCost( board[i][j]);
                String newPath =curr.getPath() +
                        String.format( "(%d,%d):%c:(%d,%d)--",
                                i + 1, j + 1, board[i][j], newI + 1, newJ + 1);

                lastFromI =i;
                lastFromJ =j;
                lastToI =newI;
                lastToJ =newJ;

                State newState =new State(newBoard, curr.getCost() + costOfMove, newPath, -1);
                states.add( newState);
                numState ++;
            }
        }
    }

    public boolean isGoalState( State curr) {
        char[][] currentBoard =curr.getBoard();
        for (int i =0; i <SIZE; i++) {
            for (int j= 0; j <SIZE; j++) {
                if (currentBoard[i][j] !=goalBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    public char[][] getGoalBoard() { return copyBoard(goalBoard);}


    private int calculateTileWeight( char tile) {
       
        if (tile== 'R') return 10; //red
        if (tile== 'B') return 1;  // blue
        if (tile == 'G') return 3;  // green
        return 0;                   // empty
    }
    
    public int heuristic(char[][] currentTiles, char[][] targetTiles) {

        int costSum =0;
    
        for (int i = 0; i <currentTiles.length; i++) {
            for (int j = 0; j <currentTiles[i].length; j++) {
                char currentChar =currentTiles[i][j];

                char targetChar =targetTiles[i][j];
    
                // if this is no '_' and no 'X'
                if (currentChar !=targetChar && currentChar != '_') {
                    int tileWeight =calculateTileWeight(currentChar);

                    costSum +=tileWeight;
                }

                if (isAligned( currentChar, targetChar, i,j,targetTiles)) {
                    costSum -=1; // if the place is good
                }
    
        
            }
        }
        return costSum;
    }
    

    private boolean isAligned(char currentTile,  char targetTile, int row, int  col, char[][] targetTiles) {
        int[] targetPosition =findGoalPosition(targetTile, targetTiles);
        return row ==targetPosition[0] || col ==targetPosition[1];
    }

    private int[] findGoalPosition(char tile,char[][] goalTiles) {
        for (int row= 0; row <goalTiles.length; row++) {
            for (int col= 0; col <goalTiles[row].length; col++) {
                if ( goalTiles[row][col] ==tile) {
                    return new int[] {row, col}; //we find the good place
                }
            }
        }
        return new int[]{-1, -1}; //if this is no happend but is no need to be happend
    }
    
    
    
    
}