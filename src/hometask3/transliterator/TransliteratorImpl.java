package hometask3.transliterator;

public class TransliteratorImpl implements Transliterator {
    TransliteratorDictionary dictionary = new TransliteratorDictionary();

    @Override
    public String transliterate(String source) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            char currentKey = source.charAt(i);
            if (this.dictionary.containsKey(currentKey)) {
                stringBuilder.append(
                        this.dictionary.getValue(
                                currentKey
                        )
                );
            } else {
                stringBuilder.append(currentKey);
            }
        }

        return stringBuilder.toString();
    }
}
