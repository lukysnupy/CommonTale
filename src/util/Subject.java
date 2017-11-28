package util;

public interface Subject {

    /**
     * Registruje observer
     * @param observer observer
     */
    void registerObserver(Observer observer);

    /**
     * Vymaže observer
     * @param observer observer
     */
    void removeObserver(Observer observer);

    /**
     * Aktualizuje všechny observery
     */
    void notifyObservers();
}
