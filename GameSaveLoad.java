import java.io.*;

public class GameSaveLoad {
    private static final String SAVE_DIRECTORY = "saves/";

    public static void saveGame(GameState gameState, String fileName) {
        File dir = new File(SAVE_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();  // Create the directory if it doesn't exist
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_DIRECTORY + fileName))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully in " + SAVE_DIRECTORY + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState loadGame(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_DIRECTORY + fileName))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}




