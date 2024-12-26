package com.p1.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@DiscriminatorValue("ROLE_ADMIN")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends Utilisateur {

}
