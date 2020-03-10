package com.trello.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Entity
public class ListCardXrefCompositeKey implements Serializable {
    @Id
    @PositiveOrZero
    private int list_id;

    @Id
    @PositiveOrZero
    private int card_id;
}
