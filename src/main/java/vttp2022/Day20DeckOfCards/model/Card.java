package vttp2022.Day20DeckOfCards.model;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private String image;
    private String value;
    private String suit;
    private String code;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static List<Card> create(String json, JsonObject obj) {
        JsonArray jsonArray = obj.getJsonArray("cards");

        List<Card> cards = new ArrayList<>();
        for (int i=0; i< jsonArray.size(); i++) {
            Card card = new Card();
            JsonObject cardObj = jsonArray.getJsonObject(i);
            card.setValue(cardObj.getString("value"));
            card.setSuit(cardObj.getString("suit"));
            card.setCode(cardObj.getString("code"));
            card.setImage(cardObj.getString("image"));
            cards.add(card);
        }
        System.out.println("++++++ end of Card.create()");

//        try {
//            DocumentContext jsonContext = JsonPath.parse(json);
//            String jsonPathCardsImagePath = "$.cards[0].image";
//            String jsonPathCardsImage = jsonContext.read(jsonPathCardsImagePath);
//
//
//            card.setImage(jsonPathCardsImage);
//            card.setCode(obj.getString("code"));
//            card.setValue("value");
//            card.setSuit("suit");
//        } catch (Exception e) {
//            System.out.println(">>>>>> Card class: unable to convert to JsonObject");
//            e.printStackTrace();
//        }
        return cards;
    }
}
