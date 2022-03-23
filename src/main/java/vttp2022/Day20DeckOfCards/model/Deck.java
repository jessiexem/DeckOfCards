package vttp2022.Day20DeckOfCards.model;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class Deck {

    private String deck_id;
    private boolean shuffled = false;
    private int remaining;
    private List<Card> cards;

    public String getDeck_id() { return deck_id; }
    public void setDeck_id(String deck_id) { this.deck_id = deck_id;}

    public boolean isShuffled() { return shuffled;}
    public void setShuffled(boolean shuffled) { this.shuffled = shuffled; }

    public int getRemaining() { return remaining;}
    public void setRemaining(int remaining) { this.remaining = remaining; }

    public List<Card> getCards() {return cards;}

    public void setCards(List<Card> cards) {this.cards = cards;}

    public static Deck create (String json) {
        Deck deck = new Deck();

        try{
            InputStream is = new ByteArrayInputStream(json.getBytes());
            JsonReader reader = Json.createReader(is);
            JsonObject obj = reader.readObject();

            deck.setDeck_id(obj.getString("deck_id"));

            if (obj.containsKey("shuffled")) {
                deck.setShuffled(obj.getBoolean("shuffled"));
            }

            List<Card> cardList = null;
            if(obj.containsKey("cards")) {
                cardList = Card.create(json,obj);
            }
            deck.setCards(cardList);
            deck.setRemaining(obj.getInt("remaining"));

        } catch (Exception e) {
            System.out.println(">>>>>> Deck class: unable to convert to JsonObject");
            e.printStackTrace();
        }
        return deck;
    }

}
