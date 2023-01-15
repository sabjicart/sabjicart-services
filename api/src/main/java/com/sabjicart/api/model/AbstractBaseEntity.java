
package com.sabjicart.api.model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EntityListeners({SequenceNumberListener.class})
public abstract class AbstractBaseEntity {
    public static final String SORT_COLUMN = "timeUpdated";
    public static final String SORT_BY_DATE_CREATED = "timeCreated";
    private static final String SQ_CLIENT = "SABJI_CART_SEQ";
    @Version
    private final long version = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = SQ_CLIENT)
    @SequenceGenerator(name = SQ_CLIENT,
            sequenceName = SQ_CLIENT,
            allocationSize = 1,
            initialValue = 1)
    private Long id;
    private boolean active = true;
    private String createdBy;
    private String modifiedBy;
    private long timeUpdated;
    private long timeCreated;

}
