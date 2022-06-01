package com.jb.coupon2demo.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@Table(name = "customers")
/**
 * this class defines the required information for the creation of a new customer.
 * Customer is one of the 3 clients in this Coupon management system
 */
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonIgnore
    private int id;
    @Column(nullable = false, length = 40)
    private String firstName;
    @Column(nullable = false, length = 40)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, length = 11)
    private String password;
    /**
     * @annotations:
     * ManyToMany is to define the relationship between the coupons and the customers.
     * Many customers may purchase many coupons
     * fetchType.EAGER is to load the defined relationship  into the object when loaded by Hibernate
     *
     * Fetch.FetchMode.JOIN is to define how Hibernate will fetch the data.
     * it loads the set of coupons eagerly every time we call for a customer object
     */
    @ManyToMany( fetch = FetchType.EAGER, mappedBy = "customers", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.JOIN)
    @JsonIgnore
    private Set<Coupon> coupons;

    /**
     * this method is defined private to prevent from clients to modify this field
     */
    private void setId(int id) {
        this.id = id;
    }

}
