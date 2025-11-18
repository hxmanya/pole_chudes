import java.util.*;

public class Main {

    private static final Random random = new Random();
    private static final Map<Integer, String> words = new HashMap<>();
    private static final Map<String, String> descriptionsMap = new HashMap<>();

    static {
        words.put(0, "солнце");
        words.put(1, "пушка");
        words.put(2, "лампочка");
        words.put(3, "год");
        words.put(4, "время");
        words.put(5, "яйцо");
        words.put(6, "ветер");
        words.put(7, "чайник");
        words.put(8, "книга");
        words.put(9, "кактус");

        descriptionsMap.put("солнце", "Самая близкая звезда к Земле?");
        descriptionsMap.put("пушка", "Артиллерия стреляет из?");
        descriptionsMap.put("лампочка", "Висит груша, нельзя скушать?");
        descriptionsMap.put("год",
                "Стоит дуб,\n" +
                        "В нём двенадцать гнезд,\n" +
                        "В каждом гнезде\n" +
                        "По четыре яйца,\n" +
                        "В каждом яйце\n" +
                        "По семи цыплят.");
        descriptionsMap.put("время",
                "Пожирает всё кругом:\n" +
                        "Зверя, птицу, лес и дом.\n" +
                        "Сталь сгрызёт, железо сгложет,\n" +
                        "Крепкий камень уничтожит,\n" +
                        "Власть его всего сильней,\n" +
                        "Даже власти королей.");
        descriptionsMap.put("яйцо",
                "Без замка, без крышки\n" +
                        "Сделан сундучок,\n" +
                        "А внутри хранится\n" +
                        "Золота кусок.");
        descriptionsMap.put("ветер",
                "Без голоса кричит,\n" +
                        "Без крыльев — а летает,\n" +
                        "И безо рта свистит,\n" +
                        "И без зубов кусает.");
        descriptionsMap.put("чайник",
                "В брюшке — баня,\n" +
                        "В носу — решето,\n" +
                        "Нос — хоботок,\n" +
                        "На голове — пупок,\n" +
                        "Всего одна рука\n" +
                        "Без пальчиков,\n" +
                        "И та — на спине\n" +
                        "Калачиком.");
        descriptionsMap.put("книга",
                "Страну чудес откроем мы\n" +
                        "И встретимся с героями\n" +
                        "В строчках,\n" +
                        "На листочках,\n" +
                        "Где станции на точках.");
        descriptionsMap.put("кактус",
                "Ёжик странный у Егорки\n" +
                        "На окне сидит в ведёрке.\n" +
                        "День и ночь он дремлет,\n" +
                        "Спрятав ножки в землю.");
    }

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Первый игрок, введите своё имя: ");
        String playerA = sc.nextLine().trim();

        System.out.print("Второй игрок, введите своё имя: ");
        String playerB = sc.nextLine().trim();

        System.out.println("Игрок 1: " + playerA + "    Игрок 2: " + playerB);

        String wordA = getRandomWord();
        String wordB = getRandomWord();

        StringBuilder guessedA = new StringBuilder("_".repeat(wordA.length()));
        StringBuilder guessedB = new StringBuilder("_".repeat(wordB.length()));

        Set<Character> guessedLettersA = new HashSet<>();
        Set<Character> guessedLettersB = new HashSet<>();

        boolean gameFinished = false;

        while (!gameFinished) {
            if (playerTurn(playerA, wordA, playerB, guessedA, guessedLettersA, sc)) {
                System.out.println(playerA + " победил! Слово: " + wordA);
                //gameFinished = true;
                break;
            }

            if (playerTurn(playerB, wordB, playerA, guessedB, guessedLettersB, sc)) {
                System.out.println(playerB + " победил! Слово: " + wordB);
                gameFinished = true;
            }
        }
    }


    private static boolean playerTurn(String currentPlayer,
                                      String currentWord,
                                      String nextPlayer,
                                      StringBuilder currentGuessedWord,
                                      Set<Character> guessedLetters,
                                      Scanner sc) {

        while (true) {
            System.out.println("----------------------------------------");
            System.out.println("Ход игрока: " + currentPlayer);
            System.out.println("Загаданное слово: " + currentGuessedWord);
            System.out.println("Загадка:");
            System.out.println(descriptionsMap.getOrDefault(currentWord, "Описание отсутствует."));
            System.out.println("Уже названные буквы: " + guessedLetters);
            System.out.print("Введите букву: ");

            String input = sc.next().toLowerCase();
            if (input.isEmpty()) {
                System.out.println("Пустой ввод. Попробуйте ещё раз.");
                continue;
            }

            char guess = input.charAt(0);

            if (!Character.isLetter(guess)) {
                System.out.println("Нужно вводить букву. Попробуйте ещё раз.");
                continue;
            }

            if (guessedLetters.contains(guess)) {
                System.out.println("Эта буква уже была названа. Попробуйте другую.");
                continue;
            }

            guessedLetters.add(guess);

            boolean found = false;
            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == guess) {
                    currentGuessedWord.setCharAt(i, guess);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Нет такой буквы. Ход переходит к игроку: " + nextPlayer);
                return false;
            }

            System.out.println("Текущее состояние слова: " + currentGuessedWord);

            if (currentGuessedWord.toString().equals(currentWord)) {
                return true;
            }

        }
    }

    private static String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}
