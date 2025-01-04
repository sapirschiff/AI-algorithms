import java.util.*;

public class BFS {
    private static int visitedNodes = 0;

      /**
     *
     * @param initialState  
     * @param boardHandler  the file deal with the board
     * @param recordOpenList if to output the open list
     * @return 
     */

    public static State search( State initialState,  Board boardHandler,  boolean recordOpenList) {
        visitedNodes= 0;

        Queue<State> openList =new LinkedList<>();
        Set<String> openSet= new HashSet<>();
        Set<String> closedList =new HashSet<> ();

        // Adding the initial state to Open List and Open Set
        openList.add( initialState);
        openSet.add ( getBoardString(initialState.getBoard()));

        while (!openList.isEmpty()) {

            if (recordOpenList ) {

                printOpenList( openList);
            }

            State current= openList.poll();
            String currentBoardStr =getBoardString( current.getBoard());
            openSet.remove(currentBoardStr );

            closedList.add (currentBoardStr );

            List<State> nextStates = boardHandler.getNextStates( current);
            visitedNodes +=nextStates.size();
            // Go over all the created inheritors
            for (State next : nextStates) {
                //check if this is the goal
                if (boardHandler.isGoalState( next)) {

                    return next;
                }

                String nextBoardStr = getBoardString(next.getBoard() );

                // Check if the next state is not in the Closed List and not in the Open List
                if (!closedList.contains (nextBoardStr) && !openSet.contains( nextBoardStr)) {
                    openList.add( next);
                    openSet.add (nextBoardStr );
                }
            }
        }  return null;
    }
   //print to the theminal
    private static void printOpenList(Queue<State> openList) {
        System.out.println( "------- Open List -------");
        int count =1;
        for (State state : openList) {
            System.out.println("State " + count + ":");
            printBoard( state.getBoard());
            count ++;
        }
        System.out.println("-------------------------\n");
    }

    
    private static void printBoard(char [][] board) {
        for (char[]  row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
  // do the board to be strings

    private static String getBoardString( char [][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char c : row) {
                sb.append( c);
            }
        }
        return sb.toString();
    }

    // all the that chacked - NUM in the output file

    public static int getVisitedNodes() {
        return visitedNodes;
    }
}