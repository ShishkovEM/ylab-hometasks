package hometask3.filesort;

import java.io.*;
import java.util.*;

public class Sorter {
    private static final int CHUNK_SIZE = 100;

    public File sortFile(File dataFile) throws IOException {
        List<File> chunkFiles = createSortedChunks(dataFile);
        return mergeSortedChunks(chunkFiles);
    }

    /**
     * Создает временные файлы с частичными отсортированными чанками, возвращает список файлов
     *
     * @param dataFile - сортируемый исходный файл
     * @return список файлов-чанков
     * @throws IOException
     */
    private List<File> createSortedChunks(File dataFile) throws IOException {
        List<File> chunkFiles = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(dataFile))) {
            List<Long> chunk = new ArrayList<>(CHUNK_SIZE);

            while (scanner.hasNextLong()) {
                chunk.add(scanner.nextLong());

                if (chunk.size() == CHUNK_SIZE) {
                    chunkFiles.add(sortAndSaveChunk(chunk));
                    chunk.clear();
                }
            }

            if (!chunk.isEmpty()) {
                chunkFiles.add(sortAndSaveChunk(chunk));
                chunk.clear();
            }
        }

        return chunkFiles;
    }

    /**
     * Cортирует чанк и записывает его во временный файл, возвращает этот файл
     *
     * @param chunk - список сортируемых значений
     * @return - файл с отсортированным чанком
     * @throws IOException
     */
    private File sortAndSaveChunk(List<Long> chunk) throws IOException {
        Collections.sort(chunk);

        File file = File.createTempFile("chunk", null);
        file.deleteOnExit();

        try (PrintWriter pw = new PrintWriter(file)) {
            for (long num : chunk) {
                pw.println(num);
            }
            pw.flush();
        }

        return file;
    }

    /**
     * Cливает все временные файлы в выходной отсортированный файл
     *
     * @param chunkFiles - лист файлов с отсортированными чанками
     * @return выходной файл с отсортированными значениями
     * @throws IOException
     */
    private File mergeSortedChunks(List<File> chunkFiles) throws IOException {
        Queue<Scanner> scanners = new LinkedList<>();
        for (File file : chunkFiles) {
            scanners.add(new Scanner(new FileInputStream(file)));
        }

        File outputFile = new File("./src/hometask3/filesort/files/sorted.txt");

        try (PrintWriter pw = new PrintWriter(outputFile)) {
            PriorityQueue<Long> pq = new PriorityQueue<>();
            for (Scanner scanner : scanners) {
                if (scanner.hasNextLong()) {
                    pq.offer(scanner.nextLong());
                }
            }

            while (!pq.isEmpty()) {
                long min = pq.poll();
                pw.println(min);

                for (Scanner scanner : scanners) {
                    if (scanner.hasNextLong()) {
                        long num = scanner.nextLong();
                        pq.offer(num);
                    }
                }
            }
        }

        return outputFile;
    }
}
