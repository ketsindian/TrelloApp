package com.trello.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "list_card_xref")
@IdClass(ListCardXrefCompositeKey.class)
public class ListCardXref {

    @Id
    @PositiveOrZero
    private int list_id;

    @Id
    @PositiveOrZero
    private int card_id;

    private int card_priority_id;
}
