package br.com.gateway.aksnes.security.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String surname;

    @Column(columnDefinition = "CHAR(60) NOT NULL")
    private String password;

    @Column(columnDefinition = "CHAR(11) NOT NULL UNIQUE")
    private String cpf;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> userRoles;

    @PrePersist
    private void prePersist() {
        if (this.userId == null) this.userId = UUID.randomUUID();
    }
}
