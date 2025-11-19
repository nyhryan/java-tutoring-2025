package week6.game;

public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    /**
     * Notify all registered observers about a change in the subject's state.
     */
    void notifyObservers();
}
