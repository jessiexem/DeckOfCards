package vttp2022.Day20DeckOfCards.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vttp2022.Day20DeckOfCards.model.Card;
import vttp2022.Day20DeckOfCards.model.Deck;
import vttp2022.Day20DeckOfCards.service.DoCService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/deck")
public class DoCController {

    @Autowired
    DoCService doCService;

    @GetMapping
    public String showHome() {
        return "index";
    }

    @PostMapping
    public String createNewDeck(Model model) {
        Optional<Deck> opt = doCService.createDeck();

        if(opt.isEmpty()) {
            return "404";
        }

        Deck deck = opt.get();

        System.out.println("Deck="+deck.getDeck_id()+deck.getRemaining()+deck.isShuffled());

        model.addAttribute("deckId",deck.getDeck_id());
        model.addAttribute("deck", deck);
        model.addAttribute("cards", List.of());
        model.addAttribute("action", "/deck/%s".formatted(deck.getDeck_id()));

        return "displayCards";
    }

    @PostMapping("/{deckId}")
    public String displayDrawnCards(@PathVariable String deckId, @RequestBody MultiValueMap<String,String> form,
                                    Model model, HttpSession httpSession) {

        String count = form.getFirst("count");
        String hiddenDrawn = form.getFirst("hidden_drawn");

        Integer visited = (Integer) httpSession.getAttribute("visited");
        List<Card> drawn = (List<Card>) httpSession.getAttribute("drawn");
        if (null == visited || null == drawn) {
            visited = 0;
            drawn = new LinkedList<>();
        }

        System.out.println(deckId);
        Optional<Deck> opt = doCService.drawCards(deckId,Integer.parseInt(count));

        if(opt.isEmpty()) {
            return "404";
        }

        Deck deck = opt.get();

        visited++;
        for (Card c: deck.getCards()) {
            drawn.add(c);
        }
        httpSession.setAttribute("visited",visited);
        httpSession.setAttribute("drawn",drawn);

        //convert String[] to linkedList
        List<String> _hiddenCards = Arrays.asList(hiddenDrawn.split(","));
        List<String> hiddenCards = new LinkedList<>(_hiddenCards);
        for (Card c: deck.getCards()) {
            hiddenCards.add(c.getImage());
        }



        System.out.println("-----displayDrawnCards: Deck="+deck.getDeck_id()+deck.getRemaining());



        model.addAttribute("deck",deck);
        model.addAttribute("deckId",deck.getDeck_id());
        //model.addAttribute("cards",deck.getCards());

        model.addAttribute("visited",visited);
        model.addAttribute("cards",hiddenCards);
        //convert LinkedList to String
        model.addAttribute("drawn",String.join(",",hiddenCards));

        if (visited > 3) {
            httpSession.invalidate();
        }

        return "displayCards";
    }
}
