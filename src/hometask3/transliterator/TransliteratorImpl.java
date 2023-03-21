package hometask3.transliterator;

public class TransliteratorImpl implements Transliterator {
    TransliteratorDictionary dictionary = new TransliteratorDictionary();

    @Override
    public String transliterate(String source) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            char currentChar = source.charAt(i);
            if (this.dictionary.containsKey(currentChar)) {
                stringBuilder.append(
                        this.dictionary.getValue(
                                currentChar
                        )
                );
            } else {
                stringBuilder.append(currentChar);
            }
        }

        return stringBuilder.toString();
    }
}
