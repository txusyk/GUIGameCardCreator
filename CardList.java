
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Josu on 15/08/16.
 */
public class CardList {

    private static CardList myCardList;

    private ArrayList<Card> cardsL;

    private CardList(){
        cardsL = new ArrayList<>();
    }

    public static CardList getMyCardList(){
        if (myCardList == null){
            myCardList = new CardList();
        }
        return myCardList;
    }
    
    public ArrayList<Card> getMyCardsL(){
        return cardsL;
    }

    public Iterator<Card> getIterator(){
        return cardsL.iterator();
    }
    
    public int getSize(){
        return cardsL.size();
    }

    public void addCard(Card pCard){
        cardsL.add(pCard);
    }

    public void removeCard(Card pCard){
        Iterator<Card> itr = getIterator();
        Card cardAux;
        boolean finded = false;
        while(itr.hasNext() && !finded){
            cardAux = itr.next();
            if (cardAux.getText().equalsIgnoreCase(pCard.getText())){
                cardsL.remove(cardAux);
                finded = true;
            }
        }
    }
}
