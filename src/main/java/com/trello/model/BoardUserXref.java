package com.trello.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "board_user_xref")
@IdClass(BoardUserXref.class)
public class BoardUserXref  implements Serializable {

    @Id
    private int board_id;

    @Id
    private int primary_user_id;

    @Id
    private int secondary_user_id;
}
