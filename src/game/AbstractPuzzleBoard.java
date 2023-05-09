package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPuzzleBoard {
    protected IPuzzleBoardImplementation boardImpl;
    private List<IBoardObserver> observers = new ArrayList<>();
    public AbstractPuzzleBoard(IPuzzleBoardImplementation boardImpl) {
        this.boardImpl = boardImpl;
    }
    
    public abstract boolean move(Direction direction);
    public abstract boolean isSolved();
    public abstract void shuffle(IShuffleStrategy strategy);

    public void addObserver(IBoardObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(IBoardObserver observer) {
        observers.remove(observer);
    }
    protected void notifyObservers() {
        for (IBoardObserver observer : observers){
            observer.update(this);
        }
    }
}
