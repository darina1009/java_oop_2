import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UI {
    private static final Scanner scanner = new Scanner(System.in);
    private static Tree tree;
    private static FileManager fileManager;

    public void run() {
        tree = new Tree(null);
        fileManager = new FileManager();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Добавить человека");
            System.out.println("2. Найти человека по имени");
            System.out.println("3. Вывести всех детей выбранного человека");
            System.out.println("4. Вывести всех потомков выбранного человека");
            System.out.println("5. Вывести всех предков выбранного человека");
            System.out.println("6. Вывести всех людей, родившихся в выбранном году");
            System.out.println("7. Сохранить дерево в файл");
            System.out.println("8. Загрузить дерево из файла");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addHuman();
                    break;
                case 2:
                    findHumanByName();
                    break;
                case 3:
                    showChildren();
                    break;
                case 4:
                    showDescendants();
                    break;
                case 5:
                    showAncestors();
                    break;
                case 6:
                    showPeopleBornInYear();
                    break;
                case 7:
                    saveToFile();
                    break;
                case 8:
                    loadFromFile();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void addHuman() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String surname = scanner.nextLine();
        System.out.print("Введите дату рождения (дд.мм.гггг): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.print("Введите имя отца или 'none': ");
        String fatherName = scanner.nextLine();
        Human father = fatherName.equals("none") ? null : findHumanByName(fatherName);
        System.out.print("Введите имя матери или 'none': ");
        String motherName = scanner.nextLine();
        Human mother = motherName.equals("none") ? null : findHumanByName(motherName);

        Human human = new Human(name, surname, birthDate);
        tree.addHuman(human, father, mother);
        System.out.println("Человек добавлен");
    }

    private void findHumanByName() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String surname = scanner.nextLine();

        Human human = tree.findHumanByName(name, surname);
        if (human == null) {
            System.out.println("Человек не найден");
        } else {
            System.out.println(human);
        }
    }

    private void showChildren() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String surname = scanner.nextLine();

        Human human = tree.findHumanByName(name, surname);
        if (human == null) {
            System.out.println("Человек не найден");
        } else {
            List<Human> children = tree.getChildren(human);
            if (children.isEmpty()) {
                System.out.println("Дети отсутствуют");
            } else {
                System.out.println("Дети:");
                for (Human child : children) {
                    System.out.println(child);
                }
            }
        }
    }

    private void showDescendants() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String surname = scanner.nextLine();

        Human human = tree.findHumanByName(name, surname);
        if (human == null) {
            System.out.println("Человек не найден");
        } else {
            List<Human> descendants = tree.getDescendants(human);
            if (descendants.isEmpty()) {
                System.out.println("Потомки отсутствуют");
            } else {
                System.out.println("Потомки:");
                for (Human descendant : descendants) {
                    System.out.println(descendant);
                }
            }
        }
    }

    private void showAncestors() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String surname = scanner.nextLine();

        Human human = tree.findHumanByName(name, surname);
        if (human == null) {
            System.out.println("Человек не найден");
        } else {
            List<Human> ancestors = tree.getAncestors(human);
            if (ancestors.isEmpty()) {
                System.out.println("Предки отсутствуют");
            } else {
                System.out.println("Предки:");
                for (Human ancestor : ancestors) {
                    System.out.println(ancestor);
                }
            }
        }
    }

    private void showPeopleBornInYear() {
        System.out.print("Введите год: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        List<Human> people = tree.getPeopleBornInYear(year);
        if (people.isEmpty()) {
            System.out.println("Люди, родившиеся в этом году, отсутствуют");
        } else {
            System.out.println("Люди, родившиеся в этом году:");
            for (Human person : people) {
                System.out.println(person);
            }
        }
    }

    private void saveToFile() {
        System.out.print("Введите имя файла: ");
        String fileName = scanner.nextLine();
        try {
            fileManager.saveToFile(tree, fileName);
            System.out.println("Дерево сохранено в файл");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении дерева в файл");
        }
    }

    private void loadFromFile() {
        System.out.print("Введите имя файла: ");
        String fileName = scanner.nextLine();
        try {
            tree = fileManager.loadFromFile(fileName);
            System.out.println("Дерево загружено из файла");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке дерева из файла");
        }
    }

    private Human findHumanByName(String name) {
        return tree.findHumanByName(name, null);
    }
}