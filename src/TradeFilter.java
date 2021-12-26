import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class TradeFilter {

    private final Path file;
    private final String platform;

    public TradeFilter(Path file, String platform) {
        this.file = file;
        this.platform = platform;
    }

    public void filter() throws IOException {

        System.out.println("Filter for " + platform);

        List<String> pl = Files.lines(file).filter(line -> {
            String[] arr = line.split("\t");
            return platform.equals(arr[2]);
        }).collect(Collectors.toList());

        String firstTrade = first(pl);
        System.out.println(String.join("\n", List.of("First trade:", firstTrade)));

        String lastTrade = last(pl);
        System.out.println(String.join("\n", List.of("Last trade:", lastTrade)));

        List<TradeInfo> tradeInfoStream = pl.stream()
                .map(line -> line.split("\t"))
                .collect(Collectors.groupingBy(row -> row[3]))
                .entrySet()
                .stream()
                .map(trade -> {
                    List<String[]> tradePrices = trade.getValue();
                    Double firstPrice = Double.parseDouble(tradePrices.stream().findFirst().get()[4]);
                    Double lastPrice = Double.parseDouble(tradePrices.stream().reduce((first, second) -> second).get()[4]);
                    Double sum = tradePrices.stream().mapToDouble(line -> Double.parseDouble(line[8])).sum();
                    return new TradeInfo(trade.getKey(), firstPrice, lastPrice, lastPrice - firstPrice, sum);
                }).sorted(TradeInfo::compareTo).collect(Collectors.toList());

        System.out.println("------------Worst ones------------");
        tradeInfoStream.stream().limit(10).forEach(System.out::println);

        System.out.println("------------Best ones-------------");
        tradeInfoStream.stream().skip(tradeInfoStream.size() - 10).forEach(System.out::println);

        System.out.println("\n");
    }

    private String first(List<String> stream) {
        return stream.stream().findFirst().get();
    }

    private String last(List<String> stream) {
        return stream.stream().reduce((first, second) -> second).get();
    }

}
