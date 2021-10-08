package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "roleid", referencedColumnName = "roleid")
    private Role role;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "warehouseId", referencedColumnName = "warehouseId")
    private Warehouse warehouse;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private String phone;

    private LocalDate dateOfBirth;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "DistrictID", referencedColumnName = "DistrictID")
    private District district;

    private String streetAddress;

    private Instant createdDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;

    private Instant lastModifiedDate;
}
