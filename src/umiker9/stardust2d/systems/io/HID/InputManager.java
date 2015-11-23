package umiker9.stardust2d.systems.io.HID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.*;

public class InputManager {
    protected InputState inputState;
    protected final Queue<InputListener> listeners = new PriorityQueue<>((Comparator<InputListener>) (o1, o2) -> o2.getPriority() - o1.getPriority());

    public InputManager() {
        inputState = new InputState();
    }

    public void handleInput() {
        inputState.setMouseDWheel(0);
        inputState.setMouseDX(0);
        inputState.setMouseDY(0);
        inputState.setMouseX(Mouse.getX());
        inputState.setMouseY(Mouse.getY());

        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == -1) {
                continue;
            }

            inputState.setKeyDown(Keyboard.getEventKey(), Keyboard.getEventKeyState());

            KeyboardEvent event = new KeyboardEvent(inputState);
            event.setEventKey(Keyboard.getEventKey());
            event.setEventKeyState(Keyboard.getEventKeyState());
            event.setEventCharacter(Keyboard.getEventCharacter());
            event.setEventNanoseconds(Keyboard.getEventNanoseconds());

            postEvent(event);
        }

        while (Mouse.next()) {
            if (Mouse.getEventButton() == -1) {
                if (Mouse.getEventDWheel() != 0) {
                    inputState.setMouseDWheel(inputState.getMouseDWheel() + Mouse.getEventDWheel());
                } else {
                    inputState.setMouseDX(inputState.getMouseDX() + Mouse.getEventDX());
                    inputState.setMouseDY(inputState.getMouseDY() + Mouse.getEventDY());
                }
            } else {
                inputState.setMouseButtonDown(Mouse.getEventButton(), Mouse.getEventButtonState());
            }

            MouseEvent event = new MouseEvent(inputState);
            event.setEventX(Mouse.getEventX());
            event.setEventY(Mouse.getEventY());
            event.setEventDX(Mouse.getEventDX());
            event.setEventDY(Mouse.getEventDY());
            event.setEventDWheel(Mouse.getEventDWheel());
            event.setEventButton(Mouse.getEventButton());
            event.setEventButtonState(Mouse.getEventButtonState());
            event.setEventNanoseconds(Mouse.getEventNanoseconds());

            postEvent(event);
        }
    }

    protected void postEvent(InputEvent e) {
        for (InputListener listener : listeners) {
            if(e.isCancelled()) {
                break;
            }
            listener.onInputEvent(e);
        }
    }

    public InputState getInputState() {
        return inputState;
    }

    public void addListener(InputListener inputListener) {
        listeners.add(inputListener);
    }

    public void removeListener(InputListener inputListener) {
        listeners.remove(inputListener);
    }
}