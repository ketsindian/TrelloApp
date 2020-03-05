package com.trello.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "board_list_xref")
@IdClass(BoardListXref.class)
public class BoardListXref implements Serializable {

    @Id
    @PositiveOrZero
    private int board_id;

    @Id
    @PositiveOrZero
    private int list_id;
}
