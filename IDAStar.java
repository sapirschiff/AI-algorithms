import java.util.HashSet;
import java.util.Stack;

// Iterative Deepening A* (IDA*) search algorithm.
public class IDAStar {
    private static int visitedNodes =0;
    private static int minF =Integer.MAX_VALUE;

    public static State search (State initialState, Board boardHandler, boolean recordOpenList) {
        visitedNodes =0;
 
        initialState.calculateHeuristic( boardHandler);// Calculate the heuristic value for the initial state
        int threshold =initialState.getFValue();

        while (true)
         {
            Stack<State> stack =new Stack<>();
            HashSet<State> currentPath= new HashSet<>();
            minF =Integer.MAX_VALUE;

            // Print the Open List if the recordOpenList flag is true
            initialState.setMarkedOut(false);
            stack.push( initialState);
            currentPath.add( initialState);

            while (!stack.isEmpty()) {
  // Print the Open List if the recordOpenList flag is true
                if (recordOpenList)
                {
                    printOpenList(stack);
                }
                  // Pop the current state from the stack
                State current =stack.pop();

                // אם המצב מסומן כ-"out", הסר אותו מהנתיב
                if (current.isMarkedOut()) {

                    currentPath.remove(current);
                    continue;
                }

                // סמן את המצב כ-"out" והחזר אותו למחסנית
                current.setMarkedOut(true);
                stack.push(current);
                if (boardHandler.isGoalState(current)) {
                    return current;
                }

                // יצירת היורשים של המצב הנוכחי
                for (State next : boardHandler.getNextStates(current)) {

                    visitedNodes++; 
                    next.calculateHeuristic(boardHandler);

                   
                    if (next.getFValue()> threshold) {

                        minF =Math.min(minF, next.getFValue());
                        continue;
                    }

                 
                    if (currentPath.contains(next)) {continue;
                    }

                    // Push the successor onto the stack 
                    next.setMarkedOut(false);
                    stack.push(next);
                    currentPath.add(next);
                }
            }

            // If no solution is found, update the threshold to the minimum F value above the current threshold
            if (minF ==Integer.MAX_VALUE) {
                return null;
            }
            threshold = minF;
        }
    }


    private static void printOpenList(Stack<State> stack) {
        System.out.println("------- Open List -------");
        for (State state : stack) {

            printBoard(state.getBoard());
            System.out.println("F Value: " + state.getFValue());
        }
        System.out.println("-------------------------\n");
    }

    // פונקציה להדפסת לוח המשחק בצורה של מטריצה
    private static void printBoard(char[][] board) {
        for (char[] row : board) 
        {
            for (char c : row) 
            {
                System.out.print(c  + " ");
            }
            System.out.println();
        }
    }

//Returns the number of visited nodes during the search.
    public static int getVisitedNodes() {
        return visitedNodes;
    }
}
