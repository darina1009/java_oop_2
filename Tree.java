import java.util.ArrayList;
import java.util.List;

public class Tree {
    private Human root;

    public Tree(Human root) {
        this.root = root;
    }

    public void addHuman(Human human, Human father, Human mother) {
        if (father != null) {
            father.addChild(human);
            human.setFather(father);
        }
        if (mother != null) {
            mother.addChild(human);
            human.setMother(mother);
        }
        if (father == null && mother == null) {
            root = human;
        }
    }

    public List<Human> getAllHumans() {
        List<Human> humans = new ArrayList<>();
        getAllHumans(root, humans);
        return humans;
    }

    private void getAllHumans(Human human, List<Human> humans) {
        if (human != null) {
            humans.add(human);
            for (Human child : human.getChildren()) {
                getAllHumans(child, humans);
            }
        }
    }

    public Human findHumanByName(String name, String surname) {
        return findHumanByName(root, name, surname);
    }

    private Human findHumanByName(Human human, String name, String surname) {
        if (human == null) {
            return null;
        }
        if (human.getName().equals(name) && human.getSurname().equals(surname)) {
            return human;
        }
        for (Human child : human.getChildren()) {
            Human found = findHumanByName(child, name, surname);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public List<Human> getChildren(Human human) {
        if (human == null) {
            return new ArrayList<>();
        }
        return human.getChildren();
    }

    public List<Human> getDescendants(Human human) {
        List<Human> descendants = new ArrayList<>();
        getDescendants(human, descendants);
        return descendants;
    }

    private void getDescendants(Human human, List<Human> descendants) {
        if (human != null) {
            descendants.add(human);
            for (Human child : human.getChildren()) {
                getDescendants(child, descendants);
            }
        }
    }

    public List<Human> getAncestors(Human human) {
        List<Human> ancestors = new ArrayList<>();
        Human parent = human.getFather();
        while (parent != null) {
            ancestors.add(parent);
            parent = parent.getFather();
        }
        parent = human.getMother();
        while (parent != null) {
            ancestors.add(parent);
            parent = parent.getMother();
        }
        return ancestors;
    }

    public List<Human> getPeopleBornInYear(int year) {
        List<Human> people = new ArrayList<>();
        getPeopleBornInYear(root, year, people);
        return people;
    }

    private void getPeopleBornInYear(Human human, int year, List<Human> people) {
        if (human.getBirthDate().getYear() == year) {
            people.add(human);
        }
        for (Human child : human.getChildren()) {
            getPeopleBornInYear(child, year, people);
        }
    }

    public Human getRoot() {
        return root;
    }
}