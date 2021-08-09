package com.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.domain.Employees;
import com.employee.dto.EmployeeDTO;
import com.employee.repository.EmployeesRepository;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public Employees getEmployee(String id){
        List<Employees> employees = employeesRepository.findById(id);
        if(employees != null && employees.size() > 0 ) {
        	return employees.get(0);
        }
        return null;
    }

    public List<Employees> getEmployees(EmployeeDTO employeeDTO){
        return employeesRepository.findAllBySalary(employeeDTO.getMinSalary(), employeeDTO.getMaxSalary());
    }

    public Employees createEmployee(EmployeeDTO employeeDTO){
    	try{
		Employees employee = getEmployee(employeeDTO.getId());
		if (employee != null) {
			List<Employees> employees = employeesRepository.findByLogin(employeeDTO.getLogin());
			if (employees != null && employees.size() > 0) {
				throw new RuntimeException("Employee ID already exists");
			}
		} else {
			List<Employees> employees = employeesRepository.findByLogin(employeeDTO.getLogin());
			if (employees != null && employees.size() > 0) {
				employee = employees.get(0);
				throw new RuntimeException("Employee login not unique");
			}
		}
		return employeesRepository.save(new Employees(employeeDTO.getId(), employeeDTO.getLogin(),
				employeeDTO.getName(), employeeDTO.getSalary(), employeeDTO.getStartDate()));
    	} catch(Exception e) {
    		throw new RuntimeException(e.getMessage());
    	}
    }

	public Employees updateEmployee(EmployeeDTO employeeDTO) {
		Employees employee = getEmployee(employeeDTO.getId());
		if (employee != null) {
			List<Employees> employees = employeesRepository.findByLogin(employeeDTO.getLogin());
			if (employees != null && employees.size() > 0) {
				throw new RuntimeException("Employee login already exists");
			}
			employee.setLogin(employeeDTO.getLogin());
			employee.setName(employeeDTO.getName());
			employee.setSalary(employeeDTO.getSalary());
			employee.setStartdate(employeeDTO.getStartDate());
			return employeesRepository.save(employee);
		} else {
			throw new RuntimeException("No such employee found");
		}

	}

	public void deleteEmployee(String id) {
		Employees employee = getEmployee(id);
		if (employee == null) {
			throw new RuntimeException("No such employee found");
		}
		employeesRepository.deleteById(id);
	}

}
