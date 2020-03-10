package com.trello.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CardResponse extends Card{

    private int card_priority_id;

    public CardResponse(Card card, int card_priority_id){
        this.card_id=card.getCard_id();
        this.card_name=card.getCard_name();
        this.card_desc=card.getCard_desc();
        this.card_priority_id=card_priority_id;
    }

}
