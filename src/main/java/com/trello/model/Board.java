package com.trello.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "board_d")
public class Board {

    @Id
    @GeneratedValue
    private int board_id;

    private String board_name;

    @PositiveOrZero
    private int board_owner_id;

}
