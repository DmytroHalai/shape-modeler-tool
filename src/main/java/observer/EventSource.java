package observer;

import java.util.ArrayList;
import java.util.List;

public class EventSource<T, TListener extends EventListener<T>> {
    protected final List<TListener> listeners = new ArrayList<>();

    public void addListener(TListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TListener listener) {
        listeners.remove(listener);
    }

    public void invoke(T target) {
        for (TListener listener : listeners) {
            listener.invoke(target);
        }
    }
}
