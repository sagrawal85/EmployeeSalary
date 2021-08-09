package com.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.domain.Employees;
import com.employee.dto.FileUploadDTO;
import com.employee.helper.CSVHelper;
import com.employee.repository.EmployeesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUploadService {

	@Autowired
	private EmployeesRepository employeesRepository;

	public void parseAndSave(FileUploadDTO file) throws Exception {
		log.info("parse csv file and save data into DB");
		List<Employees> employees = CSVHelper.csvToTutorials(file.getFile().getInputStream());
		employeesRepository.saveAll(employees);
	}
}
