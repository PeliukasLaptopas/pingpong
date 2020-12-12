package patterns.interpreter;

import com.google.gson.Gson;

public enum InterpretedAction {
    BALL_LARGE,
    BALL_MEDIUM,
    BALL_SMALL,
    PADDLE_ANGLED,
    PADDLE_SIMPLE,
    UNDEFINED;

    public String toJson() {
        return new Gson().toJson(this);
    }
}
