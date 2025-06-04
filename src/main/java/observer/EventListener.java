package observer;

public interface EventListener<T> {
    void invoke(T target);
}
