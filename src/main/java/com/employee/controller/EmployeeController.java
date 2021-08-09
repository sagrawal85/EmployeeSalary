package com.employee.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.domain.Employees;
import com.employee.domain.ResponseMessage;
import com.employee.dto.EmployeeDTO;
import com.employee.dto.FileUploadDTO;
import com.employee.helper.CSVHelper;
import com.employee.service.EmployeeService;
import com.employee.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private FileUploadService fileUploadService;

	@PostMapping("/users/upload")
	public ResponseEntity<?> uploadCsv(@ModelAttribute FileUploadDTO file) {
		String message = "";
		if (CSVHelper.hasCSVFormat(file.getFile())) {
			try {
				fileUploadService.parseAndSave(file);
				message = "Data created or uploaded.";
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new ResponseMessage(HttpStatus.CREATED.value(), message));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
			}
		}
		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), message));

	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable String id) {
		try {
			Employees employees = employeeService.getEmployee(id);
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
		}
	}

	@GetMapping("/users")
	public ResponseEntity<?> getEmployee(@RequestBody EmployeeDTO employeeDTO) {
		try {
			List<Employees> employees = employeeService.getEmployees(employeeDTO);
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
		}
	}

	@PostMapping("/users")
	public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		try {
			Employees employees = employeeService.createEmployee(employeeDTO);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseMessage(HttpStatus.CREATED.value(), "Successfully created"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
		}
	}

	@PutMapping("/users")
	public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {

		try {
			Employees employees = employeeService.updateEmployee(employeeDTO);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(HttpStatus.OK.value(), "Successfully updated"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
		try {
			employeeService.deleteEmployee(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(HttpStatus.OK.value(), "Successfully deleted"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
		}

	}
}
