import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;

    /**
     * Creates a new Dodo facing east.
     * Starts with 0 eggs hatched.
     */
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    /**
     * Runs once every game tick. Currently empty.
     */
    public void act() {
    }

    /**
     * Moves Dodo one cell forward in the current direction.
     * Shows an error message if she can't move (fence or border ahead).
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Checks whether Dodo can move forward.
     * Use this before calling move() if you want to avoid errors.
     *
     * @return true if there's no fence or border in front of Dodo
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead()){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Removes the egg in the current cell (hatches it).
     * Adds 1 to the hatched-egg counter.
     * Shows an error if there is no egg in the current cell.
     */
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }

    /**
     * Returns how many eggs Dodo has hatched so far.
     *
     * @return total number of hatched eggs
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }

    /**
     * Moves Dodo forward a given number of cells.
     * Use when you want to walk multiple steps in one call.
     *
     * @param distance the number of cells to move forward
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
            System.out.println("Moved " + nrStepsTaken);
        }
    }

    /**
     * Walks forward until Dodo reaches the edge of the world.
     * Use when Dodo is in a clear row with no fences ahead.
     */
    public void walkToWorldEdge(){
        while( ! borderAhead() ){
            move();
        }
    }

    /**
     * Turns Dodo 180 degrees so she faces the opposite direction.
     */
    public void turn180(){
        turnRight();
        turnRight();
    }

    /**
     * Checks whether Dodo can lay an egg in the current cell.
     * Use this before calling layEgg() to avoid laying two eggs in one cell.
     *
     * @return true if there's no egg already in the cell
     */
    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Climbs over a fence directly in front of Dodo.
     * Does nothing if there's no fence ahead.
     * Dodo ends up one cell past the fence, facing the same direction.
     */
    public void climbOverFence(){
        if (fenceAhead()){
            turn(-90);
            move(1);
            turn(90);
            move(2);
            turn(90);
            move(1);
            turn(-90);
        }
    }

    /**
     * Checks whether there is grain in the cell directly in front of Dodo.
     * Dodo briefly steps forward and back, but ends up in the same place.
     *
     * @return true if there's grain in the next cell
     */
    public boolean grainAhead() {
        boolean status = false;
        if (canMove()) {
            move();
            status = onGrain();
            stepOneCellBackwards();
        }
        return status;
    }

    /**
     * Walks forward until Dodo lands on an egg.
     * Use only when you know an egg is ahead.
     */
    public void gotoEgg() {
        while (! onEgg()){
            move(1);
        }
    }

    /**
     * Turns around, walks back to the start of the row, then turns around again.
     * Dodo ends up at the start of her row, facing the original direction.
     */
    public void goBackToStartOfRowAndFaceBack() {
        turn180();
        walkToWorldEdge();
        turn180();
    }

    /**
     * Walks to the edge of the world, climbing over any fences in the way.
     */
    public void walkToWorldEdgeClimbingOverFences() {
        while (!borderAhead()) {
            if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }
    }

    /**
     * Walks to the edge of the world.
     * Picks up every grain along the way and prints its coordinates.
     */
    public void pickUpGrainsAndPrintCoordinates() {
        if (onGrain()) {
            System.out.println("(" + getX() + ", " + getY() + ")");
            pickUpGrain();
        }
        while (!borderAhead()) {
            move();
            if (onGrain()) {
                System.out.println("(" + getX() + ", " + getY() + ")");
                pickUpGrain();
            }
        }
    }

    /**
     * Moves Dodo one cell backward, keeping her facing the same direction.
     */
    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }

    /**
     * Walks to the edge of the world.
     * Lays an egg in every empty nest along the way.
     * Skips nests that already have an egg.
     */
    public void layEggsInEmptyNests() {
        if (onNest() && canLayEgg()) {
            layEgg();
        }
        while (!borderAhead()) {
            move();
            if (onNest() && canLayEgg()) {
                layEgg();
            }
        }
    }

    /**
     * Walks forward until Dodo finds a nest, climbing over any fences in the way.
     * Lays an egg if the nest is empty.
     */
    public void walkToNestAvoidingFences() {
        while (!onNest() && !borderAhead()) {
            if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }
        if (onNest() && canLayEgg()) {
            layEgg();
        }
    }

    /**
     * Walks around a fenced area using the right-hand rule (wall follower).
     * Stops when Dodo lands back on the starting egg.
     * Use this when there's an egg next to the fenced area as a start/end marker.
     */
    public void walkAroundFencedArea() {
    while (!onEgg()) {
        turnRight();
        for (int i = 0; i < 4; i++) {
            if (canMove()) {
                move();
                break;
            }
            turnLeft();
        }
    }
}
}