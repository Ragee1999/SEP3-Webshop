package sep3.webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sep3.webshop.repository.AdminRepository;


@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public boolean authenticate(String username, String password) {
        return adminRepository.findByUsername(username)
                .map(admin -> admin.getPassword().equals(password))
                .orElse(false);
    }
}
