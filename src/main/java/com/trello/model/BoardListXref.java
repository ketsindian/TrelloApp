package com.trello.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "board_list_xref")
public class BoardListXref {

    @PositiveOrZero
    private int board_id;

    @PositiveOrZero
    private int list_id;
}
