import java.util.*;

public class Main {

    private static final Random random = new Random();
    private static final Map<Integer, String> words = new HashMap<>();
    private static final Map<String, String> descriptionsMap = new HashMap<>();

    private static final int ROUNDS = 3;
    private static final int MAX_POINTS = 20;

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

        clearConsole();
        System.out.println("\n========================================");
        System.out.println("      ИГРА-ВИКТОРИНА: ПОЛЕ-ЧУДЕС");
        System.out.println("========================================\n");

        System.out.print("Введите количество игроков (минимум 2): ");
        int playerCount = readPlayerCount(sc);

        String[] players = new String[playerCount];
        for (int i = 0; i < playerCount; i++) {
            System.out.print("Игрок " + (i + 1) + ", введите своё имя: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                name = "Игрок " + (i + 1);
            }
            players[i] = name;
        }

        System.out.println("\nУчастники игры:");
        for (int i = 0; i < players.length; i++) {
            System.out.println("  " + (i + 1) + ". " + players[i]);
        }

        int[] totalScores = new int[playerCount];
        int[] wins = new int[playerCount];

        Set<String> usedWords = new HashSet<>();

        for (int round = 1; round <= ROUNDS; round++) {
            clearConsole();
            System.out.println("\n========================================");
            System.out.println("              РАУНД " + round + " / " + ROUNDS);
            System.out.println("========================================\n");


            String currentWord = getRandomWord(usedWords);
            usedWords.add(currentWord);

            int[] roundScores = new int[playerCount];
            int winnerIndex = playRound(players, currentWord, sc, roundScores);

            System.out.println("\n------------ РЕЗУЛЬТАТЫ РАУНДА " + round + " ------------");
            for (int i = 0; i < players.length; i++) {
                System.out.println(players[i] + " — очки в раунде: " + roundScores[i]);
            }

            if (winnerIndex >= 0) {
                wins[winnerIndex]++;
                totalScores[winnerIndex] += roundScores[winnerIndex];

                System.out.println("Раунд " + round + " выиграл игрок " +
                        players[winnerIndex] + ". Очки за раунд: " + roundScores[winnerIndex]);
            } else {
                System.out.println("Раунд " + round + " завершился без победителя.");
            }

        }

        clearConsole();
        System.out.println("\n========================================");
        System.out.println("               ИТОГИ ИГРЫ");
        System.out.println("========================================\n");

        System.out.printf("%-20s %-20s %-20s%n",
                "Игрок", "Выигранных раундов", "Очки (за выигр. раунды)");
        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < players.length; i++) {
            System.out.printf("%-20s %-20d %-20d%n",
                    players[i], wins[i], totalScores[i]);
        }

        int overallWinner = 0;
        for (int i = 1; i < players.length; i++) {
            if (wins[i] > wins[overallWinner] ||
                    (wins[i] == wins[overallWinner] && totalScores[i] > totalScores[overallWinner])) {
                overallWinner = i;
            }
        }

        System.out.println("\n========================================");
        System.out.println("  ПОБЕДИТЕЛЬ ИГРЫ: " + players[overallWinner]);
        System.out.println("  Выигранных раундов: " + wins[overallWinner]);
        System.out.println("  Очков: " + totalScores[overallWinner]);
        System.out.println("========================================");
    }

    private static int playRound(String[] players,
                                 String word,
                                 Scanner sc,
                                 int[] roundScores) {

        StringBuilder guessed = new StringBuilder("_".repeat(word.length()));
        Set<Character> guessedLetters = new HashSet<>();

        int currentPlayerIndex = 0;

        while (true) {
            clearConsole();
            System.out.println("\n========================================");
            System.out.println("               ОТГАДЫВАНИЕ");
            System.out.println("========================================\n");

            String currentPlayer = players[currentPlayerIndex];

            System.out.println("Ход игрока: " + currentPlayer);
            System.out.println("Слово: " + guessed);
            System.out.println("Загадка:");
            System.out.println(descriptionsMap.getOrDefault(word, "Описание отсутствует."));
            System.out.println("Уже названные буквы: " + guessedLetters);
            System.out.println("Текущие очки игрока в этом раунде: " + roundScores[currentPlayerIndex]);

            int pointsThisTurn = random.nextInt(MAX_POINTS) + 1;
            System.out.println("На этот ход вам выпало " + pointsThisTurn + " очков.");


            char guess = readLetter(sc, guessedLetters);


            boolean found = false;
            int matches = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    guessed.setCharAt(i, guess);
                    found = true;
                    matches++;
                }
            }

            if (found) {
                int gained = pointsThisTurn * matches;
                roundScores[currentPlayerIndex] += gained;
                System.out.println("Есть такая буква!");
                System.out.println("Открылось позиций: " + matches);
                System.out.println("За этот ход вы получили " + gained +
                        " очков (" + pointsThisTurn + " × " + matches + ").");
                System.out.println("Слово теперь: " + guessed);
                System.out.println("Очки игрока " + currentPlayer +
                        " в этом раунде: " + roundScores[currentPlayerIndex]);

                if (guessed.indexOf("_") == -1) {
                    System.out.println("\n========================================");
                    System.out.println("  Игрок " + currentPlayer +
                            " полностью отгадал слово \"" + word + "\"!");
                    System.out.println("  Раунд выигран!");
                    System.out.println("========================================");
                    return currentPlayerIndex;
                }



            } else {
                System.out.println("\nНет такой буквы. Очки за этот ход не начисляются.");
                System.out.println("(Ход переходит к следующему игроку)");
                System.out.println("Нажмите Enter, чтобы продолжить...");
                sc.nextLine();

                currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
            }
        }
    }


    private static int readPlayerCount(Scanner sc) {
        while (true) {
            if (sc.hasNextInt()) {
                int count = sc.nextInt();
                sc.nextLine();
                if (count >= 2) {
                    return count;
                } else {
                    System.out.print("Количество игроков должно быть не меньше 2. Попробуйте снова: ");
                }
            } else {
                System.out.print("Введите целое число. Попробуйте снова: ");
                sc.next();
            }
        }
    }

    private static char readLetter(Scanner sc, Set<Character> guessedLetters) {
        while (true) {
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
            return guess;
        }
    }

    private static String getRandomWord(Set<String> usedWords) {
        List<String> allWords = new ArrayList<>(words.values());
        allWords.removeAll(usedWords);

        if (allWords.isEmpty()) {
            return words.get(random.nextInt(words.size()));
        }

        return allWords.get(random.nextInt(allWords.size()));
    }



    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}