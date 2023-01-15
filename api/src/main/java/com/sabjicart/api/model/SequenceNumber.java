
package com.sabjicart.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Sequence Number Generator.  This is modeled after the suggestion
 * from stackoverflow.  Each row consists of partition, classname and the
 * sequence number.
 */
@Entity
@Table(name = "SequenceNumber",
        indexes = {@Index(name = "SequenceNumberIdx",
                columnList = "userICode,nextValue",
                unique = true)})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceNumber {
    /* This id column is not used  - it is auto generated*/
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private long id;

    @Column(updatable = false)
    private String userIcode;

    private long nextValue = 1001;

    private long incrementValue = 1;

}

