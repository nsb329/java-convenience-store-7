package store;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Filein {
    List<FileinVO> fileList = new ArrayList<>();

    public void fileall(Firstclass firstclass) {
        try {
            List<FileinVO> products = readfile("src/main/resources/products.md");
            displayProducts(products);
            firstclass.calc(products, new ArrayList<>(), new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FileinVO> readfile(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        return parseLines(lines);
    }

    List<FileinVO> parseLines(List<String> lines) {
        for (String line : lines) {
            parseLine(line);
        }
        return fileList;
    }

    void parseLine(String line) {
        FileinVO fileinVO = new FileinVO();
        String[] data = line.split(",");
        fileinVO.setName(data[0].trim());
        fileinVO.setPrice(Integer.parseInt(data[1].trim()));
        fileinVO.setQuantity(Integer.parseInt(data[2].trim()));
        if (data.length > 3) {
            fileinVO.setPromotion(data[3].trim());
        }
        fileList.add(fileinVO);
    }

    void displayProducts(List<FileinVO> products) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        for (FileinVO product : products) {
            String formattedPrice = numberFormat.format(product.getPrice());
            StringBuilder output = new StringBuilder("- " + product.getName() + " " + formattedPrice + "원 ");

            if (product.getQuantity() <= 0) {
                output.append("재고 없음");
            } else {
                output.append(product.getQuantity() + "개");
            }

            if (!"null".equals(product.getPromotion())) {
                output.append(" ").append(product.getPromotion());
            }

            System.out.println(output.toString());
        }
    }
}
