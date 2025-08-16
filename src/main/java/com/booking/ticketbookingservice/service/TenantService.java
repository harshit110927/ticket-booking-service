package com.booking.ticketbookingservice.service;

import com.booking.ticketbookingservice.entity.Tenant;
import com.booking.ticketbookingservice.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;

    @Autowired
    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }
    /**
     * Creates a new tenant
     * @param tenant The tenant object to be saved.
     * @return the saved tenant object, including its new ID
     */
    public Tenant createTenant(Tenant tenant){
        return tenantRepository.save(tenant);
    }
    /**
     * Retrieves all tenants
     * @return A list of all Tenant objects
     */
    public List<Tenant>getAllTenants(){
        return tenantRepository.findAll();
    }

    /**
     * Retrieve a single tenant by its ID
     * @param id -> The ID of the tenant to retrieve
     * @return found tenant object
     * @throws RuntimeException if no tenant found with given ID
     */
    public Tenant getTenantById(Long id){
        return tenantRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Tenant not found with id: "+id));
    }

}
