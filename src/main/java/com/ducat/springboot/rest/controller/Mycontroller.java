package com.ducat.springboot.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ducat.springboot.rest.model.Employee;
import com.ducat.springboot.rest.service.Myservice;

@RestController
public class Mycontroller {

	@Autowired
	Myservice service;

	@RequestMapping(value = "/employee/all", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Employee> getEmployees() {
		System.out.println(this.getClass().getSimpleName() + " - Get all employees service is invoked.");
		return service.getEmployees();
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public Employee getEmployeeById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Get employee details by id is invoked.");

		Optional<Employee> emp = service.getEmployeeById(id);
		if (!emp.isPresent())
			throw new Exception("Could not find employee with id- " + id);

		return emp.get();
	}

	@RequestMapping(value = "/employee/add", method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:4200")
	public Employee createEmployee(@RequestBody Employee newemp) {
		System.out.println(this.getClass().getSimpleName() + " - Create new employee method is invoked.");
		System.out.println("Mycontroller.createEmployee() :: newemp --> "+newemp);
		return service.addNewEmployee(newemp);
	}

	@RequestMapping(value = "/employee/update/{id}", method = RequestMethod.PUT)
	@CrossOrigin(origins = "http://localhost:4200")
	public Employee updateEmployee(@RequestBody Employee updemp, @PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Update employee details by id is invoked.");

		Optional<Employee> emp = service.getEmployeeById(id);
		if (!emp.isPresent())
			throw new Exception("Could not find employee with id- " + id);

		/*
		 * IMPORTANT - To prevent the overiding of the existing value of the variables
		 * in the database, if that variable is not coming in the @RequestBody
		 * annotation object.
		 */
		if (updemp.getName() == null || updemp.getName().isEmpty())
			updemp.setName(emp.get().getName());
		if (updemp.getDepartment() == null || updemp.getDepartment().isEmpty())
			updemp.setDepartment(emp.get().getDepartment());
		if (updemp.getSalary() == 0)
			updemp.setSalary(emp.get().getSalary());

		// Required for the "where" clause in the sql query template.
		updemp.setId(id);
		return service.updateEmployee(updemp);
	}

	@RequestMapping(value = "/employee/delete", method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteEmployeeById(@RequestBody Employee newemp) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete employee by id is invoked.");
		Optional<Employee> emp = service.getEmployeeById(newemp.getId());
		if (!emp.isPresent())
			throw new Exception("Could not find employee with id- " + newemp.getId());

		service.deleteEmployeeById(newemp.getId());
		
		
	}

	@RequestMapping(value = "/employee/deleteall", method = RequestMethod.DELETE)
	@CrossOrigin(origins = "http://localhost:4200")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all employees is invoked.");
		service.deleteAllEmployees();
	}
}