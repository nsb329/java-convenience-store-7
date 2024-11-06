package store;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public enum PromotionEnum {
    CARBONATED_2PLUS1("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")),
    MD_RECOMMENDED("MD추천상품", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")),
    FLASH_SALE("반짝할인", 1, 1, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-30"));

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    PromotionEnum(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() { return name; }
    public int getBuy() { return buy; }
    public int getGet() { return get; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    public boolean isDateValid(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public static List<PromotionData> loadFromFile() throws IOException {
        List<PromotionData> promotions = new ArrayList<>();
        String filePath = "src/main/resources/promotions.md";
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (int i = 1; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            String name = fields[0];
            int buy = Integer.parseInt(fields[1]);
            int get = Integer.parseInt(fields[2]);
            LocalDate startDate = LocalDate.parse(fields[3]);
            LocalDate endDate = LocalDate.parse(fields[4]);

            promotions.add(new PromotionData(name, buy, get, startDate, endDate));
        }
        return promotions;
    }
}

class PromotionData {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionData(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() { return name; }
    public int getBuy() { return buy; }
    public int getGet() { return get; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    @Override
    public String toString() {
        return "PromotionData{" +
                "name='" + name + '\'' +
                ", buy=" + buy +
                ", get=" + get +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
