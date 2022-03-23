package vttp2022.Day20DeckOfCards.service;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vttp2022.Day20DeckOfCards.model.Deck;

import java.util.Optional;

@Service
public class DoCService {

    private static final String SHUFFLE_URL = "https://deckofcardsapi.com/api/deck/new/shuffle";
    private static final String DRAW_URL = "https://deckofcardsapi.com/api/deck";

    public Optional<Deck> drawCards(String deckId, Integer count) {

        StringBuilder sb = new StringBuilder();
        sb.append(DRAW_URL)
            .append("/"+deckId)
                .append("/draw/");

        String url = UriComponentsBuilder
                .fromUriString(sb.toString())
                .queryParam("count",count)
                .toUriString();

        RequestEntity req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req,String.class);

        System.out.println(">>>>DoCService drawCards: "+resp.getBody());

        try {
            Deck deck = Deck.create(resp.getBody());
            return Optional.of(deck);
        } catch (Exception e) {
            System.out.println(">>>> DoCService - drawCards: Error creating deck");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Deck> createDeck() {

        //make deck count a variable instead of 1.
        String url = UriComponentsBuilder
                .fromUriString(SHUFFLE_URL)
                .queryParam("deck_count",1)
                .toUriString();

        RequestEntity req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req,String.class);

        System.out.println(">>>>>DocService createDeck: "+resp.getBody());

        try {
            Deck deck = Deck.create(resp.getBody());
            return Optional.of(deck);
        } catch (Exception e) {
            System.out.println(">>>> DoCService: Error creating deck");
            e.printStackTrace();
        }

        return Optional.empty();
    }


}
