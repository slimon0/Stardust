package umiker9.stardust2d.systems.io.HID;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by miker9 on 12/12/2015.
 */
public class InputRelay extends InputListener {
    private final Queue<InputListener> inputListeners = new PriorityQueue<>((Comparator<InputListener>) (o1, o2) -> o2.getPriority() - o1.getPriority());
    private boolean active = true;

    public InputRelay() {

    }

    public InputRelay(int priority) {
        super(priority);
    }


    public void addInputListener(InputListener listener) {
        if (!inputListeners.contains(listener)) {
            inputListeners.add(listener);
        }
        listener.setInputSource(this);
    }

    public void removeInputListener(InputListener listener) {
        inputListeners.remove(listener);
        listener.setInputSource(null);
    }

    @Override
    public void onInputEvent(InputEvent event) {
        super.onInputEvent(event);
        if (isActive()) {
            for (InputListener listener : inputListeners) {
                if (event.isCancelled()) {
                    break;
                }
                listener.onInputEvent(event);
            }
        }
    }

    public InputState getInputState() {
        if (inputSource != null) {
            return inputSource.getInputState();
        } else {
            return new InputState();
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
