package com.booking.ticketbookingservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

// research in depth about this Lombok and Data annotation
@Data
//@Entity tells the java program that this class is an entity and can be saved in the DB
@Entity
//Specify the mapped DB table of this entity
@Table(name = "Tenants")
public class Tenant {
    @Id
    //@Id has marked this below column as the Primary Key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;
    // now mark all the columns of the table and set the rules and constraints
    @Column(name = "tenant_name", nullable = false)
    private String tenantName;

    @Column(name = "contact_email", nullable = false, unique = true)
    private String contactEmail;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    //Basic english would've told you that this runs before the entity is first saved
    //We are using it to create a timestamp
    @PrePersist
    protected void onCreate(){
        createdAt = Instant.now();
    }
}

//WITHOUT LOMBOK - VERY VERBOSE

//@Entity
//@Table(name = "tenants")
//public class Tenant {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long tenantId;
//
//    @Column(name = "tenant_name", nullable = false)
//    private String tenantName;
//
//    // ... other fields ...
//
//    // --- You would have to write all of this manually! ---
//
//    public Long getTenantId() {
//        return tenantId;
//    }
//
//    public void setTenantId(Long tenantId) {
//        this.tenantId = tenantId;
//    }
//
//    public String getTenantName() {
//        return tenantName;
//    }
//
//    public void setTenantName(String tenantName) {
//        this.tenantName = tenantName;
//    }
//
//    // ... and getters/setters for every other field ...
//    // ... plus toString(), equals(), and hashCode() methods ...
//}