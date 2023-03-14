package anmi.login.configuration;

import anmi.login.entity.Employee;
import anmi.login.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> userRole = employeeRepository.findByEmail(username);
        if (userRole.isPresent()) {
            return (UserDetails) userRole.get();
        }
        System.out.println(userRole);
        throw new UsernameNotFoundException("Token JWT inválido ou expirado!");
    }
}
