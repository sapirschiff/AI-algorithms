import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class DFID {
    private static final State CUTOFF =new State(null, 0,"", -1);
    private static final State FAIL =new State(null, 0,"", -1);
    private static int visitedNodes =0;

    public static State search(State initialState,Board boardHandler, boolean recordOpenList) {
        int[] nodesCreated = {0 }; // Array to track the number of created nodes

        for (int depth =1; ; depth++) { // Iteratively increase the depth limit until a solution is found

            Set<String> H =new HashSet<>();
            State result =limitedDFS(initialState, boardHandler, depth, H, nodesCreated, recordOpenList);
// If a solution is found, return it
            if (result !=CUTOFF) {
                visitedNodes= nodesCreated[0];

                return result;
            }
        }
    }
//Limited Depth-First Search (DFS) with a depth limit.
    private static State limitedDFS(State curr,Board boardHandler, int limit, Set<String> H, int[] nodesCreated, boolean recordOpenList) {
        if (boardHandler.isGoalState(curr) ) { return curr;
        }

        if (limit ==0) {return CUTOFF;
        }

        if (recordOpenList) {

            System.out.println("Current Board:");
            printBoard(curr.getBoard());
        }

        H.add(getBoardString(curr.getBoard()) );
        boolean isCutoff =false;

        List<State> operators =boardHandler.getNextStates(curr);
        nodesCreated[0] +=operators.size();  
// Iterate through each next state
        for (State next : operators) {
            String nextBoardStr =getBoardString(next.getBoard());


            if ( H.contains(nextBoardStr)) {
                continue;
            }

            State result =limitedDFS(next,boardHandler, limit - 1, H, nodesCreated, recordOpenList);

            if (result== CUTOFF) { isCutoff =true;
            }
            else if (result !=FAIL) {
                return result;
            }
        }
        // Remove the current state from the set after exploring it
        H.remove(getBoardString(curr.getBoard()));
        return isCutoff ? CUTOFF : FAIL;
    }

    private static void printBoard(char[][] board) {
        if (board ==null) 
        return;
        for (char[] row : board) {

            for (char c : row) {
                System.out.print(c +  " ");
            }

            System.out.println();
        }
        System.out.println();
    }

    private static String getBoardString(char[][] board) {
        if (board ==null) return "";
        StringBuilder sb =new StringBuilder();
        for (char[] row : board) {
            for (char c : row) {
                sb.append( c);
            }
        }
        return sb.toString();
    }

    public static int getVisitedNodes() {
        return visitedNodes;
    }
}