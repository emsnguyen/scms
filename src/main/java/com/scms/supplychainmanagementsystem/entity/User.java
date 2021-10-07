package com.scms.supplychainmanagementsystem.entity;

import io.micrometer.core.lang.Nullable;
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
    @ManyToOne
    @JoinColumn(name = "roleid", referencedColumnName = "roleid", insertable = false)
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @Nullable
    @JoinColumn(name = "warehouseId", referencedColumnName = "warehouseId", insertable = false)
    private Warehouse warehouse;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private String phone;

    private LocalDate dateOfBirth;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DistrictID", referencedColumnName = "DistrictID", insertable = false)
    private District district;

    private String streetAddress;

    private Instant createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy", referencedColumnName = "userID", insertable = false)
    private User createdBy;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID", insertable = false)
    private User lastModifiedBy;

    private Instant lastModifiedDate;
}
