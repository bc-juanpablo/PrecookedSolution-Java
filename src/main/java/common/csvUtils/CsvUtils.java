package common.csvUtils;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CsvUtils {
    private List<Map<String,String>> csvData;

    public CsvUtils(){
        csvData = new ArrayList<>();
    }

    public List<Map<String,String>> getCsvData(String path) throws IOException {
        Path filePath = Paths.get(path);
        CSVParser csvParser = CSVParser.parse(filePath, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader());

        Stream<CSVRecord> csvRecordStream = StreamSupport.stream(csvParser.spliterator(),false);

        List<Map<String,String>> rowList = csvRecordStream
                .map(CSVRecord::toMap)
                .collect(Collectors.toList());
        csvData = rowList;
        return rowList;
    }

    public int getCsvTotalRows(){
        return csvData.size();
    }

    public ArrayList<Object[]> fillDataProviderIterations(){
        ArrayList<Object[]> dataProviderIteration = new ArrayList<>();
        for (int i = 0; i<getCsvTotalRows(); i++){
            int iteration = i;
            Object[] ob = {iteration};
            dataProviderIteration.add(ob);
        }
        return dataProviderIteration;
    }

}
