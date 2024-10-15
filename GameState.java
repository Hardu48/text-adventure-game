// import java.io.Serializable;
// import java.util.ArrayList;

// public class GameState implements Serializable {
//     private String currentLocation;
//     private ArrayList<String> inventory;
//     private int progress;
//     private String story;

//     public GameState(String currentLocation, String story) {
//         this.currentLocation = currentLocation;
//         this.inventory = new ArrayList<>();
//         this.progress = 0;
//         this.story = story;
//     }

//     // Getter and Setter methods for currentLocation, inventory, progress, and story
//     public String getCurrentLocation() {
//         return currentLocation;
//     }

//     public void setCurrentLocation(String currentLocation) {
//         this.currentLocation = currentLocation;
//     }

//     public ArrayList<String> getInventory() {
//         return inventory;
//     }

//     public void addItemToInventory(String item) {
//         inventory.add(item);
//     }

//     public int getProgress() {
//         return progress;
//     }

//     public void increaseProgress() {
//         this.progress++;
//     }

//     public String getStory() {
//         return story;
//     }

//     public void appendToStory(String additionalText) {
//         this.story += "\n" + additionalText; // Append new content to the existing story
//     }

//     public void clear() {
//         this.currentLocation = "Starting Location";
//         this.inventory.clear();
//         this.progress = 0;
//         this.story = "Welcome to your adventure!"; // Reset the story
//     }
// }

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GameState implements Serializable {
    private String currentLocation;
    private ArrayList<String> inventory;
    private int progress;
    private String story;
    
    // New: Store custom choices and corresponding outcomes
    private HashMap<String, String> customChoices;

    public GameState(String currentLocation, String story) {
        this.currentLocation = currentLocation;
        this.inventory = new ArrayList<>();
        this.progress = 0;
        this.story = story;
        this.customChoices = new HashMap<>();
    }

    // Getter and Setter methods for currentLocation, inventory, progress, and story
    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void addItemToInventory(String item) {
        inventory.add(item);
    }

    public int getProgress() {
        return progress;
    }

    public void increaseProgress() {
        this.progress++;
    }

    public String getStory() {
        return story;
    }

    public void appendToStory(String additionalText) {
        this.story += "\n" + additionalText;
    }

    // New: Methods to manage custom choices
    public void addCustomChoice(String choice, String outcome) {
        customChoices.put(choice, outcome);
    }

    public HashMap<String, String> getCustomChoices() {
        return customChoices;
    }

    public void clear() {
        this.currentLocation = "Starting Location";
        this.inventory.clear();
        this.progress = 0;
        this.story = "Welcome to your adventure!";
        this.customChoices.clear(); // Clear custom choices when resetting the game
    }
}
