package com.jb.coupon2demo.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
/**
 *this class defines the required information for the creation of a new company.
 * Company is one of the 3 clients in this Coupon management system
 */
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonIgnore
    private int id;
    @Column(unique = true, nullable = false, length = 40)
    private String name;
    @Column(unique = true, nullable = false, length = 40)
    private String email;
    @Column(nullable = false, length = 11)
    private String password;
    /**
     * @OneToMany is to define the relationship between the company and the coupons.
     * one company can have many coupons
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "companyId", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<Coupon> coupons;

    /**
     *this method is defined private to prevent from clients to modify this field
     */
    private void setId(int id) {
        this.id = id;
    }
}
