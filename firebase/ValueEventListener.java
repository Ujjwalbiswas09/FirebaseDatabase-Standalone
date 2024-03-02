package firebase;

public interface ValueEventListener {
    void onChange(String path, Object obj) throws Exception;
}
