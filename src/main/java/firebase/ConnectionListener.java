package firebase;

public interface ConnectionListener {
    void onConnected();

    void onDisconnect();

    void onUpdate(boolean state);
}
