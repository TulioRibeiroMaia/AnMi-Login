package anmi.login.service;

import anmi.login.dto.EmployeeDTO;
import anmi.login.dto.EmployeeFormDTO;
import anmi.login.exception.ResourceNotFoundException;
import anmi.login.repository.EmployeeRepository;
import anmi.login.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

@Autowired
private EmployeeRepository employeeRepository;

@Autowired
private ModelMapper modelMapper;

@Override
public EmployeeDTO save(EmployeeFormDTO body){
        Employee employee=modelMapper.map(body,Employee.class);
        Employee savedEmployee=this.employeeRepository.save(employee);
        return modelMapper.map(savedEmployee,EmployeeDTO.class);
        }

@Override
public List<EmployeeDTO> getEmployees(String fullName){
        List<Employee> employees;

        if(fullName!=null){
        employees=this.employeeRepository.findByFullNameIgnoreCaseContaining(fullName);
        }else{
        employees=this.employeeRepository.findAll();
        }

        return employees
        .stream()
        .map(employee->modelMapper
        .map(employee,EmployeeDTO.class))
        .collect(Collectors.toList());
        }

@Override
public EmployeeDTO searchEmployee(String cpf){
        Optional<Employee> employee=this.employeeRepository.findByCpf(cpf);

        if(employee.isPresent()){
        return modelMapper.map(employee.get(),EmployeeDTO.class);
        }

        throw new ResourceNotFoundException("CPF "+cpf);
        }

@Override
public EmployeeDTO updateEmployee(String cpf,EmployeeFormDTO body) {
        Optional<Employee> employee = this.employeeRepository.findByCpf(cpf);

        if (employee.isEmpty()) {
                throw new ResourceNotFoundException("Cpf não encontrado " + cpf);
        }

        Employee employeeFound = employee.get();

        Optional<Employee> employeeByEmail = this.employeeRepository.findByEmail(body.getEmail());
        if (employeeByEmail.isPresent() && !employeeFound.getCpf().equals(employeeByEmail.get().getCpf())) {
                throw new RuntimeException("Email '" + body.getEmail() + "' já existe em outro cadastro");
        }
        Employee updatedEmployee = modelMapper.map(body, Employee.class);
        updatedEmployee.setId(employee.get().getId());
        this.employeeRepository.save(updatedEmployee);

        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
}

@Override
public void deleteEmployee(String cpf){
        Optional<Employee> employee=this.employeeRepository.findByCpf(cpf);

        if(!employee.isPresent()){
        throw new ResourceNotFoundException("CPF "+cpf);
        }

        this.employeeRepository.deleteById(employee.get().getId());
        }

}



