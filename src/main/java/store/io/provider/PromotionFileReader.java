package store.io.provider;

import static store.common.ErrorMessage.CAN_NOT_READ;
import static store.common.ErrorMessage.INVALID_FORMAT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import store.common.exception.FileReadException;
import store.domain.store.promotion.Promotion;
import store.domain.store.promotion.PromotionType;
import store.domain.store.promotion.Promotions;

public class PromotionFileReader {

    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private static final String DELIMITER = ",";
    private static final int HEADER_LINE = 1;

    public Promotions getPromotions() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(PROMOTION_FILE_PATH));
            Set<Promotion> promotions = lines.stream()
                    .skip(HEADER_LINE)
                    .map(this::parsePromotion)
                    .collect(Collectors.toUnmodifiableSet());
            return new Promotions(promotions);
        } catch (IOException e) {
            throw new FileReadException(CAN_NOT_READ.getMessage(), e);
        }
    }

    private Promotion parsePromotion(String line) {
        String[] values = line.split(DELIMITER);
        String name = values[0];
        PromotionType promotionType = findPromotionType(values);
        LocalDate startDate = LocalDate.parse(values[3]);
        LocalDate endDate = LocalDate.parse(values[4]);
        return new Promotion(name, promotionType, startDate, endDate);
    }

    private PromotionType findPromotionType(final String[] values) {
        int countOfBuy = convertStringToInt(values[1]);
        int freeToGet = convertStringToInt(values[2]);
        return PromotionType.findPromotionType(countOfBuy, freeToGet);
    }

    private int convertStringToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new FileReadException(INVALID_FORMAT.getMessage(), e);
        }
    }
}

