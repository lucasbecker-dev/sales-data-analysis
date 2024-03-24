package com.coderscampus.assignment6;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SalesDataService {
    public static YearMonth getDateAsYearMonth(SalesData salesData) {
        String month = salesData.getDate().split("-")[0];
        String year = "20" + salesData.getDate().split("-")[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM", Locale.getDefault());
        return YearMonth.parse(year + "-" + month, formatter);
    }

    public static Integer getSalesAsInteger(SalesData salesData) {
        Integer convertedSales = null;
        try {
            convertedSales = Integer.parseInt(salesData.getSales());
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
        return convertedSales;
    }

    public static SalesData findSalesDataBestMonth(List<SalesData> salesDataList) {
        Optional<SalesData> bestMonthOpt = salesDataList
                .stream()
                .max(Comparator.comparingInt(SalesDataService::getSalesAsInteger));
        return bestMonthOpt.orElse(null);
    }

    public static SalesData findSalesDataWorstMonth(List<SalesData> salesDataList) {
        Optional<SalesData> worstMonthOpt = salesDataList
                .stream()
                .min(Comparator.comparingInt(SalesDataService::getSalesAsInteger));
        return worstMonthOpt.orElse(null);

    }

    public static Map<String, String> groupSalesByYear(List<SalesData> salesDataList) {
        Map<String, String> salesGroupedByYear = new HashMap<>();
        Set<Integer> years = salesDataList.stream()
                                          .map(data -> SalesDataService.getDateAsYearMonth(data).getYear())
                                          .collect(Collectors.toCollection(LinkedHashSet<Integer>::new));

        for (Integer year : years) {
            Integer sum = 0;
            for (SalesData salesData : salesDataList) {
                Integer currentYear = SalesDataService.getDateAsYearMonth(salesData).getYear();
                if (currentYear.equals(year)) {
                    sum += SalesDataService.getSalesAsInteger(salesData);
                }
            }
            salesGroupedByYear.put(year.toString(), sum.toString());
        }

        return salesGroupedByYear;
    }
}
