import java.io.*;
import java.util.List;

public class FileManager {
    public void saveToFile(Tree tree, String fileName) throws IOException {
        List<Human> humans = tree.getAllHumans();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(humans);
        }
    }

    public Tree loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        Tree tree = new Tree(null);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            List<Human> humans = (List<Human>) ois.readObject();
            for (Human human : humans) {
                tree.addHuman(human, human.getFather(), human.getMother());
            }
        }
        return tree;
    }
}