package anmi.login.repository;

import anmi.login.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);

    Optional<Employee> findByCpf(String cpf);

    List<Employee> findByFullNameIgnoreCaseContaining(String fullName);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByCpfOrEmail(String cpf, String email);
}
