package com.trello.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "card_d")
public class Card {

    @Id
    @GeneratedValue
    protected int card_id;

    protected String card_name;

    protected String card_desc;
}
