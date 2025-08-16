package com.booking.ticketbookingservice.service;

import com.booking.ticketbookingservice.dto.TenantOnboardingRequest;
import com.booking.ticketbookingservice.entity.Role;
import com.booking.ticketbookingservice.entity.Tenant;
import com.booking.ticketbookingservice.entity.User;
import com.booking.ticketbookingservice.repository.TenantRepository;
import com.booking.ticketbookingservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantOnboardingService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TenantOnboardingService(TenantRepository tenantRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Tenant onboardTenant(TenantOnboardingRequest request) {
        if (userRepository.findByEmail(request.getAdminEmail()).isPresent()) {
            throw new RuntimeException("Error: Admin email is already in use!");
        }

        Tenant tenant = new Tenant();
        tenant.setTenantName(request.getTenantName());
        tenant.setContactEmail(request.getAdminEmail());
        Tenant savedTenant = tenantRepository.save(tenant);

        User adminUser = new User();
        adminUser.setUserName(request.getAdminUserName());
        adminUser.setEmail(request.getAdminEmail());
        adminUser.setPasswordHash(passwordEncoder.encode(request.getAdminPassword()));
        adminUser.setRole(Role.ADMIN);
        adminUser.setTenant(savedTenant);
        userRepository.save(adminUser);

        return savedTenant;
    }
}