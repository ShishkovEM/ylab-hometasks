package hometask3.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        String[] pangramms = new String[6];
        pangramms[0] = "Съешь ещё этих мягких французских булок, да выпей же чаю.";
        pangramms[1] = "Эх, взъярюсь, толкну флегматика: \"Дал бы щец жарчайших, Петр!\"";
        pangramms[2] = "Шалящий фавн прикинул объём горячих звезд этих вьюжных царств.";
        pangramms[3] = "СЪЕШЬ ЕЩЁ ЭТИХ МЯГКИХ ФРАНЦУЗСКИХ БУЛОК, ДА ВЫПЕЙ ЖЕ ЧАЮ.";
        pangramms[4] = "ЭХ, ВЗЪЯРЮСЬ, ТОЛКНУ ФЛЕГМАТИКА: \"ДАЛ БЫ ЩЕЦ ЖАРЧАЙШИХ, ПЕТР!\"";
        pangramms[5] = "ШАЛЯЩИЙ ФАВН ПРИКИНУЛ ОБЪЁМ ГОРЯЧИХ ЗВЕЗД ЭТИХ ВЬЮЖНЫХ ЦАРСТВ.";

        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator
                .transliterate("HELLO! ПРИВЕТ! Go, boy!");
        System.out.println(res);

        for (String pangramm : pangramms) {
            System.out.println(
                    transliterator.transliterate(pangramm)
            );
        }
    }
}
