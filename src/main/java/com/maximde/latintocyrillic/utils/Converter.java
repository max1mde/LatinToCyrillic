package com.maximde.latintocyrillic.utils;




import java.util.HashMap;
import java.util.Map;


public class Converter {
    private static final Map<String, String> criticalWords = new HashMap<>();
    private static final Map<Character, String> latinToCyrillic = new HashMap<>();



    public static String convertToKeyboardLayout(String text) {
        String[][] mappings = {
                {"a", "а"}, {"b", "б"}, {"c", "ц"}, {"d", "д"}, {"e", "е"}, {"f", "ф"}, {"g", "г"}, {"h", "х"},
                {"i", "и"}, {"j", "й"}, {"k", "к"}, {"l", "л"}, {"m", "м"}, {"n", "н"}, {"o", "о"}, {"p", "п"},
                {"q", "я"}, {"r", "р"}, {"s", "с"}, {"t", "т"}, {"u", "у"}, {"v", "в"}, {"w", "в"}, {"x", "ь"},
                {"y", "з"}, {"z", "ъ"},
                {"A", "А"}, {"B", "Б"}, {"C", "Ц"}, {"D", "Д"}, {"E", "Е"}, {"F", "Ф"}, {"G", "Г"}, {"H", "Х"},
                {"I", "И"}, {"J", "Й"}, {"K", "К"}, {"L", "Л"}, {"M", "М"}, {"N", "Н"}, {"O", "О"}, {"P", "П"},
                {"Q", "Я"}, {"R", "Р"}, {"S", "С"}, {"T", "Т"}, {"U", "У"}, {"V", "В"}, {"W", "В"}, {"X", "ѝ"},
                {"Y", "З"}, {"Z", "Ъ"}
        };

        for (String[] mapping : mappings) {
            text = text.replace(mapping[0], mapping[1]);
        }
        return text;
    }

    public static String convertToCyrillic(String text, boolean useCriticalWords) {
        StringBuilder result = new StringBuilder();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                word.append(c);
            } else {
                if (!word.isEmpty()) {
                    result.append(convertWord(word.toString(), useCriticalWords));
                    word.setLength(0);
                }
                result.append(c);
            }
        }

        if (!word.isEmpty()) {
            result.append(convertWord(word.toString(), useCriticalWords));
        }

        return result.toString();
    }

    private static String convertWord(String word, boolean useCriticalWords) {
        if (useCriticalWords && criticalWords.containsKey(word.toLowerCase())) {
            String converted = criticalWords.get(word.toLowerCase());
            return Character.isUpperCase(word.charAt(0))
                    ? Character.toUpperCase(converted.charAt(0)) + converted.substring(1)
                    : converted;
        }

        StringBuilder convertedWord = new StringBuilder();
        int i = 0;
        while (i < word.length()) {
            if (i < word.length() - 2 && word.substring(i, i + 3).equalsIgnoreCase("sht")) {
                convertedWord.append(word.startsWith("sht", i) ? "щ" : "Щ");
                i += 3;
            } else if (i < word.length() - 1) {
                String twoChars = word.substring(i, i + 2).toLowerCase();
                if (twoChars.equals("ch")) {
                    convertedWord.append(word.startsWith("ch", i) ? "ч" : "Ч");
                    i += 2;
                } else if (twoChars.equals("sh")) {
                    convertedWord.append(word.startsWith("sh", i) ? "ш" : "Ш");
                    i += 2;
                } else if (twoChars.equals("ts")) {
                    convertedWord.append(word.startsWith("ts", i) ? "ц" : "Ц");
                    i += 2;
                } else if (twoChars.equals("ya")) {
                    convertedWord.append(word.startsWith("ya", i) ? "я" : "Я");
                    i += 2;
                } else if (twoChars.equals("yu")) {
                    convertedWord.append(word.startsWith("yu", i) ? "ю" : "Ю");
                    i += 2;
                } else if (twoChars.equals("zh")) {
                    convertedWord.append(word.substring(i, i + 2).equals("zh") ? "ж" : "Ж");
                    i += 2;
                } else {
                    convertedWord.append(convertSingleChar(word.charAt(i), i > 0 ? word.charAt(i - 1) : ' '));
                    i++;
                }
            } else {
                convertedWord.append(convertSingleChar(word.charAt(i), i > 0 ? word.charAt(i - 1) : ' '));
                i++;
            }
        }
        return convertedWord.toString();
    }

    private static String convertSingleChar(char c, char previousChar) {
        if (c == 'y' || c == 'Y') {
            if (isVowel(previousChar)) {
                return c == 'y' ? "й" : "Й";
            } else {
                return c == 'y' ? "ъ" : "Ъ";
            }
        } else if (c == 'q' || c == 'Q') {
            return c == 'q' ? "я" : "Я";
        } else if (c == '`') {
            return "ь";
        }
        return latinToCyrillic.getOrDefault(c, String.valueOf(c));
    }

    private static boolean isVowel(char c) {
        return "aeiouyAEIOUY".indexOf(c) != -1;
    }

    static {
        criticalWords.put("as", "аз");
        criticalWords.put("sum", "съм");
        criticalWords.put("cum", "съм");
        criticalWords.put("dobur", "добър");
        criticalWords.put("dobzr", "добър");
        criticalWords.put("sdrasti", "здрасти");
        criticalWords.put("sdrawej", "здравей");
        criticalWords.put("sdrawejte", "здравейте");
        criticalWords.put("zdrawej", "здравей");
        criticalWords.put("zdrawejte", "здравейте");
        criticalWords.put("zdraweyte", "здравейте");
        criticalWords.put("tup", "тъп");
        criticalWords.put("koy", "кой");
        criticalWords.put("koi", "кой");
        criticalWords.put("toy", "той");
        criticalWords.put("toi", "той");
        criticalWords.put("muj", "мъж");
        criticalWords.put("pytiat", "пътят");
        criticalWords.put("nqkoy", "някой");
        criticalWords.put("nqkoi", "някой");
        criticalWords.put("nqkoj", "някой");
        criticalWords.put("luv", "лъв");
        criticalWords.put("tyi", "тъй");
        criticalWords.put("tui", "тъй");
        criticalWords.put("lubov", "любов");
        criticalWords.put("lubim", "любим");
        criticalWords.put("jyvot", "живот");
        criticalWords.put("jyvee", "живее");
        criticalWords.put("jyv", "жив");
        criticalWords.put("jyvi", "живи");
        criticalWords.put("putq", "пътя");
        criticalWords.put("syjedenie", "съждение");
        criticalWords.put("synyvam", "сънувам");
        criticalWords.put("zdrawey", "здравей");
        criticalWords.put("zdravey", "здравей");
        criticalWords.put("zdraveu", "здравей");
        criticalWords.put("zdrawej", "здравей");
        criticalWords.put("zdraweu", "здравей");
        criticalWords.put("schiweq", "живея");
        criticalWords.put("jyvot", "живот");
        criticalWords.put("jyvee", "живее");
        criticalWords.put("jyv", "жив");
        criticalWords.put("jyvi", "живи");
        criticalWords.put("muj", "мъж");
        criticalWords.put("moy", "мой");
        criticalWords.put("tvoj", "твой");
        criticalWords.put("neina", "нейна");
        criticalWords.put("surver", "сървер");
        criticalWords.put("syrver", "сървер");
        criticalWords.put("syrvera", "сървера");
        criticalWords.put("surveryt", "сърверът");
        criticalWords.put("neino", "нейно");
        criticalWords.put("neini", "нейни");
        criticalWords.put("tqhni", "техни");
        criticalWords.put("koi", "кой");
        criticalWords.put("koj", "кой");
        criticalWords.put("kojto", "който");
        criticalWords.put("koyto", "който");
        criticalWords.put("chiy", "чий");
        criticalWords.put("vsitchki", "всички");
        criticalWords.put("toj", "той");
        criticalWords.put("sas", "със");
        criticalWords.put("naqsht", "нящ");
        criticalWords.put("majka", "майка");
        criticalWords.put("maika", "майка");
        criticalWords.put("mayka", "майка");
        criticalWords.put("niy", "ний");
        criticalWords.put("seyf", "сейф");
        criticalWords.put("seif", "сейф");
        criticalWords.put("chay", "чай");
        criticalWords.put("chaj", "чай");
        criticalWords.put("chai", "чай");
        criticalWords.put("pleyur", "плейър");
        criticalWords.put("pleyr", "плейър");
        criticalWords.put("kray", "край");
        criticalWords.put("krai", "край");
        criticalWords.put("tvoj", "твой");
        criticalWords.put("tvoi", "твой");
        criticalWords.put("lujitsa", "лъжица");
        criticalWords.put("lujica", "лъжица");
        criticalWords.put("vuy", "въй");
        criticalWords.put("vuj", "въй");
        criticalWords.put("gayka", "гайка");
        criticalWords.put("gaika", "гайка");
        criticalWords.put("peyka", "пейка");
        criticalWords.put("peika", "пейка");
        criticalWords.put("luykay", "лъкай");
        criticalWords.put("lyjkay", "лъкай");
        criticalWords.put("lujkay", "лъкай");
        criticalWords.put("okej", "окей");
        criticalWords.put("okey", "окей");
        criticalWords.put("okay", "окей");
        criticalWords.put("boy", "бой");
        criticalWords.put("boj", "бой");
        criticalWords.put("viy", "вий");
        criticalWords.put("vi", "вий");
        criticalWords.put("remontiriy", "ремонтирий");
        criticalWords.put("remontirii", "ремонтирий");
        criticalWords.put("krayche", "крайче");
        criticalWords.put("kraiche", "крайче");
        criticalWords.put("majstor", "майстор");
        criticalWords.put("maystor", "майстор");
        criticalWords.put("maistor", "майстор");
        criticalWords.put("pitay", "питай");
        criticalWords.put("pitai", "питай");
        criticalWords.put("pytai", "питай");
        criticalWords.put("piy", "пий");
        criticalWords.put("pi", "пий");
        criticalWords.put("stoy", "стой");
        criticalWords.put("nikoj", "никой");
        criticalWords.put("nikoi", "никой");
        criticalWords.put("nikoy", "никой");
        criticalWords.put("stai", "стой");
        criticalWords.put("viyte", "вийте");
        criticalWords.put("lehlо", "легло");
        criticalWords.put("praznuvay", "празнувай");
        criticalWords.put("praznuva", "празнувай");
        criticalWords.put("sniy", "сний");
        criticalWords.put("sni", "сний");
        criticalWords.put("zapitay", "запитай");
        criticalWords.put("zapitai", "запитай");
        criticalWords.put("zapitaj", "запитай");
        criticalWords.put("tichay", "тичай");
        criticalWords.put("tichaj", "тичай");
        criticalWords.put("tichai", "тичай");
        criticalWords.put("rabotiy", "работий");
        criticalWords.put("rabotii", "работий");
        criticalWords.put("spiy", "спий");
        criticalWords.put("spi", "спий");
        criticalWords.put("uspokoy", "успокой");
        criticalWords.put("uspokoi", "успокой");
        criticalWords.put("viyka", "вийка");
        criticalWords.put("viika", "вийка");
        criticalWords.put("vuzklinkniy", "възкликни");
        criticalWords.put("vuzklinkni", "възкликни");
        criticalWords.put("podsushiy", "подсуший");
        criticalWords.put("podsushii", "подсуший");
        criticalWords.put("podsusiy", "подсуший");
        criticalWords.put("naviy", "навий");
        criticalWords.put("navii", "навий");
        criticalWords.put("razbiray", "разбирай");
        criticalWords.put("razbirai", "разбирай");
        criticalWords.put("napiy", "напий");
        criticalWords.put("napii", "напий");
        criticalWords.put("subudiy", "събудий");
        criticalWords.put("subudii", "събудий");
        criticalWords.put("gray", "грай");
        criticalWords.put("grai", "грай");
        criticalWords.put("nay", "най");
        criticalWords.put("nai", "най");
        criticalWords.put("naj", "най");
        criticalWords.put("zaba", "жаба");
        criticalWords.put("zabata", "жабата");
        latinToCyrillic.put('a', "а");latinToCyrillic.put('b', "б");latinToCyrillic.put('v', "в");latinToCyrillic.put('g', "г");latinToCyrillic.put('d', "д");latinToCyrillic.put('e', "е");
        latinToCyrillic.put('j', "ж");latinToCyrillic.put('z', "з");latinToCyrillic.put('i', "и");latinToCyrillic.put('k', "к");latinToCyrillic.put('l', "л");
        latinToCyrillic.put('m', "м");latinToCyrillic.put('n', "н");latinToCyrillic.put('o', "о");latinToCyrillic.put('p', "п");latinToCyrillic.put('r', "р");latinToCyrillic.put('s', "с");latinToCyrillic.put('t', "т");
        latinToCyrillic.put('u', "у");latinToCyrillic.put('f', "ф");latinToCyrillic.put('h', "х");latinToCyrillic.put('c', "ц");latinToCyrillic.put('w', "в");
        latinToCyrillic.put('A', "А");latinToCyrillic.put('B', "Б");latinToCyrillic.put('V', "В");latinToCyrillic.put('G', "Г");
        latinToCyrillic.put('D', "Д");latinToCyrillic.put('E', "Е");latinToCyrillic.put('J', "Ж");latinToCyrillic.put('Z', "З");latinToCyrillic.put('I', "И");
        latinToCyrillic.put('K', "К");latinToCyrillic.put('L', "Л");latinToCyrillic.put('M', "М");latinToCyrillic.put('N', "Н");latinToCyrillic.put('O', "О");latinToCyrillic.put('P', "П");latinToCyrillic.put('R', "Р");latinToCyrillic.put('S', "С");
        latinToCyrillic.put('T', "Т");latinToCyrillic.put('U', "У");latinToCyrillic.put('F', "Ф");latinToCyrillic.put('H', "Х");latinToCyrillic.put('C', "Ц");latinToCyrillic.put('W', "В");
    }
}