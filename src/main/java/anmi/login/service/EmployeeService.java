package anmi.login.service;

import anmi.login.dto.EmployeeDTO;
import anmi.login.dto.EmployeeFormDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO save(EmployeeFormDTO body);

    List<EmployeeDTO> getEmployees(String name);

    EmployeeDTO searchEmployee(String cpf);

    EmployeeDTO updateEmployee(String cpf, EmployeeFormDTO body);

    void deleteEmployee(String cpf);


}
