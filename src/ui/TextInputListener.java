package ui;

public interface TextInputListener {
    void onTextInputCompleted(String text, int x, int y);
    void onTextInputCancelled();
}
