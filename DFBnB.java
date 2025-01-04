import java.util.*;

public class DFBnB {
    private static int visitedNodes= 0; // Variable to track the number of visited nodes

    private static int generationTime= 0; // Variable to track the generation time of nodes

    public static State search( State initialState, Board boardHandler, boolean recordOpenList) {
        visitedNodes =0; 
        generationTime= 0; 
        Stack<State> stack =new Stack<>();
        Set<State> currentPath= new HashSet<>();
        int upperBound =35;// Initial upper bound for the cost

        State result =null;
        // Set the generation time for the initial state and push it to the stack

        initialState.setGenerationTime(generationTime ++); // זמן יצירת קודקוד ההתחלה
        stack.push( initialState);

        currentPath.add( initialState);

        while (!stack.isEmpty()) {

            if ( recordOpenList) {
                System.out.println("------- Open List -------");
                printOpenList( stack);
                System.out.println("-------------------------\n");
            }
            // pop the current state 

            State current = stack.pop();

            // if the state is marked as "out", remove it from the current path and continue
            if (current.isMarkedOut()) {
                currentPath.remove( current);
                continue;
            }

            // Mark the state as "out" and push it back to the stack
            current.setMarkedOut(true);
            stack.push(current);

            // Check if the current state is the goal state

            if (boardHandler.isGoalState( current)) {
                upperBound =current.getFValue();
                result= current;
                continue;
            }

    
            List<State> successors =boardHandler.getNextStates(current);
            visitedNodes +=successors.size();

            for (State successor : successors) {
                successor.calculateHeuristic(boardHandler);

                successor.setGenerationTime(generationTime++); // עדכון זמן יצירה
            }

           
            successors.sort((s1, s2) -> {

                int f1 =s1.getFValue();
                int f2 =s2.getFValue();
                if (f1 != f2) return Integer.compare(f1, f2);
                return Integer.compare(s1.getGenerationTime(), s2.getGenerationTime());
            });

              // Process the successors
            for (int i = 0; i <successors.size(); i++) {
                State successor =successors.get(i);

                if (successor.getFValue() >=upperBound) {
                    while (i <successors.size()) {

                        successors.remove( i);
                    }
                    break;
                }

                if (currentPath.contains( successor)) {
                    successors.remove( i);
                    i --;
                    continue;
                }

                if (boardHandler.isGoalState(successor)) {
                    upperBound =successor.getFValue();
                    result =successor;
                    while (i <successors.size()) {
                        successors.remove(i );
                    }
                    break;
                }
            }

            for (int i =successors.size() - 1; i>= 0; i--) {
                stack.push(successors.get( i));
                currentPath.add(successors.get( i));
            }
        }

        return result;
    }

   //Prints the Open List to the terminal.
   private static void printOpenList(Stack<State> stack) {
    for (State state : stack) {
        printBoard(state.getBoard());

        System.out.println("F Value: " + state.getFValue() + ", Generation Time: " + state.getGenerationTime());
    }
}

private static void printBoard(char[][] board) {
    for (char[] row : board) {
        for (char c : row) {

            System.out.print(c  + " ");
        }
        System.out.println();
    }
}

    public static int getVisitedNodes() {
        return visitedNodes;
    }
}
