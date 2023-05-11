package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        return employeeService.readAll().stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());

//        return getAllEmployees().stream()
//                .map(Employee::getFirstName)
//                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {
        return countryService.readAll().stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
        return departmentService.readAll().stream()
                .map(Department::getManager)
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return departmentService.readAll().stream()
                .filter(d->d.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());
    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        return departmentService.readAll().stream()
                .filter(d->d.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());
    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
//        return departmentService.readAll().stream()
//                .filter(d->d.getLocation().getCountry().getRegion().getRegionName().equals("IT"))
//                .map(e->e.getLocation().getCountry().getRegion())
//                .collect(Collectors.toList())
//                .get(0);
//        return departmentService.readAll().stream()
//                .filter(d->d.getDepartmentName().equals("IT"))
//                .map(d->d.getLocation().getCountry().getRegion());

        // findFirst() or findAny()
        return getAllDepartments().stream()
                .filter(department -> department.getDepartmentName().equals("IT"))
                .findFirst().get().getLocation().getCountry().getRegion();

        // orElseThrow
//        return getAllDepartments().stream()
//                .filter(department -> department.getDepartmentName().equals("IT"))
//                .findFirst().orElseThrow(()->new Exception("Department not found!")).getLocation().getCountry().getRegion();

    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        return departmentService.readAll().stream()
                .filter(d->d.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
                .collect(Collectors.toList());
    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
//        return employeeService.readAll().stream()
//                .map(Employee::getSalary)
//                .noneMatch(s->s<1000);
        return getAllEmployees().stream()
                .noneMatch(employee -> employee.getSalary() < 1000);
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        return employeeService.readAll().stream()
                .filter(d->d.getDepartment().getDepartmentName().equals("IT"))
                .allMatch(e->e.getSalary() > 2000);
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary() < 5000)
                .collect(Collectors.toList());
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        return employeeService.readAll().stream()
                .filter(e->e.getSalary() > 6000 && e.getSalary() < 7000)
                .collect(Collectors.toList());
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
//        return employeeService.readAll().stream()
//                .filter(employee -> employee.getFirstName().equals("Douglas") && employee.getLastName().equals("Grant"))
//                .map(Employee::getSalary)
//                .reduce(0L, Long::sum);
        //TODO *** is this the BEST way? *** find below: //
        return getAllEmployees().stream()
                .filter(employee -> employee.getFirstName().equals("Douglas") && employee.getLastName().equals("Grant"))
                .findFirst().get().getSalary();     //findFirst() returns Optional, so get() the object

//        return getAllEmployees().stream()
//                .filter(employee -> employee.getFirstName().equals("Douglas") && employee.getLastName().equals("Grant"))
//                .findFirst().orElseThrow(()->new Exception("No Employee found!")).getSalary();
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
//        return employeeService.readAll().stream()
//                .map(Employee::getSalary)
//                .reduce(Long::max)
//                .stream().reduce(0L, Long::sum);
        //TODO *** is this the BEST way? *** find below: //
//        return getAllEmployees().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .findFirst().get().getSalary();

        return getAllEmployees().stream()
                .max(Comparator.comparing(Employee::getSalary)) //returns Optional
                .get().getSalary();         // so, I need to get() the object

//        return getAllEmployees().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .limit(1).collect(Collectors.toList()).get(0).getSalary();

//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .reduce((salary1,salary2)->salary1>salary2 ? salary1 : salary2)
//                .get();

//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .reduce(Long::max)
//                .get();

//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .collect(Collectors.maxBy(Comparator.comparing(Long::longValue)))
//                .get();

//        return getAllEmployees().stream()
//                .collect(Collectors.maxBy(Comparator.comparing(Employee::getSalary)))
//                .get().getSalary();

//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .max(Comparator.comparing(Long::longValue))
//                .get();

//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .mapToLong(i->i)
//                .max().getAsLong();

    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        return employeeService.readAll().stream()
                .max(Comparator.comparing(Employee::getSalary))
                .stream().collect(Collectors.toList());
    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
//        return employeeService.readAll().stream()
//                .max(Comparator.comparing(Employee::getSalary))
//                .map(Employee::getJob)
//                .stream().collect(Collectors.toList())
//                .get(0);
        //TODO *** is this the BEST way? *** see below: //

        return getAllEmployees().stream()
                .max(Comparator.comparing(Employee::getSalary))
                .get().getJob();

//        return getMaxSalaryEmployee().get(0).getJob();
    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
//        return employeeService.readAll().stream()
//                .filter(e->e.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
//                .max(Comparator.comparing(Employee::getSalary))
//                .map(Employee::getSalary)
//                .stream().collect(Collectors.toList())
//                .get(0);
        //TODO *** is this the BEST way? *** //
        return getAllEmployees().stream()
                .filter(e->e.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(()->new Exception("Americas region not found!")).getSalary();
//                .get().getSalary();   //also possible
    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() {
        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .map(Employee::getSalary)
                .distinct()
                .collect(Collectors.toList())
                .get(1);
//                .skip(1)
//                .findFirst().get();
    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {
        return employeeService.readAll().stream()
                .filter(e->e.getSalary().equals(getSecondMaxSalary()))
                .collect(Collectors.toList());
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        return getAllEmployees().stream()
                .map(Employee::getSalary)
                .min(Comparator.comparing(Long::longValue))
                .get();
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        return getAllEmployees().stream()
                .min(Comparator.comparing(Employee::getSalary))
                .stream().collect(Collectors.toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() {
        return getAllEmployees().stream()
                .map(Employee::getSalary)
                .sorted(Comparator.comparing(Long::longValue))
                .distinct()
                .collect(Collectors.toList())
                .get(1);
//                .skip(1)
//                .findFirst().get();

    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary().equals(getSecondMinSalary()))
                .collect(Collectors.toList());
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        return getAllEmployees().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
        return getAllEmployees().stream()
                .filter(e->e.getSalary() > getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        return getAllEmployees().stream()
                .filter(e->e.getSalary() < getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        return getAllEmployees().stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment().getId()));
        // returns Map<Long, List<Employee>> ; Key: Id, Value: List of Employee
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
//        return getAllDepartments().stream().count();
        return (long) getAllDepartments().size();
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        return getAllEmployees().stream()
                .filter(e->e.getFirstName().equals("Alyssa")
                        && e.getManager().getFirstName().equals("Eleni")
                        && e.getDepartment().getDepartmentName().equals("Sales"))
                .findFirst().get();

//        return getAllEmployees().stream()
//                .filter(e->e.getFirstName().equals("Alyssa"))
//                .filter(e->e.getManager().getFirstName().equals("Eleni"))
//                .filter(e->e.getDepartment().getDepartmentName().equals("Sales"))
//                .findFirst().get();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        return getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        return getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        return getAllJobHistories().stream()
                .filter(j->j.getStartDate().isAfter(LocalDate.of(2005,1,1)))
                .collect(Collectors.toList());
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        return getAllJobHistories().stream()
                .filter(jh->jh.getEndDate().equals(LocalDate.of(2007,12,31)))
                .filter(jh->jh.getJob().getJobTitle().equals("Programmer"))
                .collect(Collectors.toList());
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        return getAllJobHistories().stream()
                .filter(jh->jh.getStartDate().equals(LocalDate.of(2007,1,1))
                        && jh.getEndDate().equals(LocalDate.of(2007,12,31))
                        && jh.getDepartment().getDepartmentName().equals("Shipping"))
                .findFirst().get().getEmployee();
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        return getAllEmployees().stream()
                .filter(e->e.getFirstName().startsWith("A"))
                .collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        return getAllEmployees().stream()
                .filter(e->e.getJob().getId().contains("IT"))
                .collect(Collectors.toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        return getAllEmployees().stream()
                .filter(e->e.getJob().getJobTitle().equalsIgnoreCase("programmer"))
                .filter(e->e.getDepartment().getDepartmentName().equals("IT"))
                .count();
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return getAllEmployees().stream()
                .filter(e->e.getDepartment().getId() ==50
                        || e.getDepartment().getId() == 80
                        || e.getDepartment().getId() == 100)
                .collect(Collectors.toList());
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        return getAllEmployees().stream()
                .map(e->e.getFirstName().charAt(0) + "" + e.getLastName().charAt(0))
                .collect(Collectors.toList());

//        return getAllEmployees().stream()
//                .map(e-> {
//                    char firstNameInitial = e.getFirstName().charAt(0);
//                    char lastNameInitial = e.getLastName().charAt(0);
//                    return firstNameInitial + "" + lastNameInitial;
//                })
//                .collect(Collectors.toList());
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        return getAllEmployees().stream()
                .map(e->e.getFirstName() + " " + e.getLastName())
                .collect(Collectors.toList());
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() {
        return getAllEmployees().stream()
                .map(e->e.getFirstName() + " " + e.getLastName())
                .map(String::length)
                .max(Comparator.comparing(Integer::longValue)).get();

//        return getAllEmployeesFullNames().stream()
//                .max(Comparator.comparing(String::length))
//                .get().length();
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        return getAllEmployees().stream()
                .filter(e-> (e.getFirstName().length() + e.getLastName().length() +1) == getLongestNameLength())
                .collect(Collectors.toList());
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        return getAllEmployees().stream()
                .filter(e->e.getDepartment().getId() == 90
                        || e.getDepartment().getId() == 60
                        || e.getDepartment().getId() == 100
                        || e.getDepartment().getId() == 120
                        || e.getDepartment().getId() == 130)
                .collect(Collectors.toList());
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        return getAllEmployees().stream()
                .filter(e->e.getDepartment().getId() != 90
                        && e.getDepartment().getId() != 60
                        && e.getDepartment().getId() != 100
                        && e.getDepartment().getId() != 120
                        && e.getDepartment().getId() != 130)
                .collect(Collectors.toList());
    }

}
