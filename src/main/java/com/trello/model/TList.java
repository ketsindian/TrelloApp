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
@Table(name = "list_d")
public class TList {
    @Id
    @GeneratedValue
    private int list_id;

    private String list_name;

    private String list_desc;
}
