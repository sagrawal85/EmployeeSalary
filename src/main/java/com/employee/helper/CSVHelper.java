package com.employee.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.employee.domain.Employees;

public class CSVHelper {

	public static String TYPE = "text/csv";
	static String[] HEADERS = { "id", "login", "name", "salary", "startDate" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Employees> csvToTutorials(InputStream is) {
		DateTimeFormatter yyyydateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
				.withResolverStyle(ResolverStyle.STRICT);

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withHeader().withAllowMissingColumnNames(false).withSkipHeaderRecord())) {

			if (!csvParser.getHeaderNames().equals(Arrays.asList(HEADERS))) {
				throw new RuntimeException("Headers are not in same order :: " + csvParser.getHeaderNames());
			}

			List<Employees> employees = new ArrayList<Employees>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Employees employee = new Employees(csvRecord.get("id"), csvRecord.get("login"), csvRecord.get("name"),
						Double.valueOf(csvRecord.get("salary")),
						Date.valueOf(validateAndParseDateJava8(csvRecord.get("startDate"), yyyydateFormatter)));

				employees.add(employee);
			}

			return employees;
		} catch (Exception ex) {
			if(ex instanceof DateTimeParseException) {
				throw new RuntimeException("Invalid Date format!");
			} else {
				throw new RuntimeException("fail to parse CSV file: " + ex.getMessage());
			}
		}
	}

	public static LocalDate validateAndParseDateJava8(String dateStr, DateTimeFormatter dateFormatter) {
		LocalDate today = LocalDate.now();
		int thisYear = today.getYear();
		LocalDate date = null;
		try {
			date = LocalDate.parse(dateStr);
		} catch (DateTimeParseException e) {
			DateTimeFormatter dddateFormatter = new DateTimeFormatterBuilder().appendPattern("d-MMM-")
					.appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2, thisYear - 99).toFormatter(Locale.ENGLISH);
			date = LocalDate.parse(dateStr, dddateFormatter);
		}
		return date;
	}

}
