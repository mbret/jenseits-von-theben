
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.game;

import com.miage.areas.Area;
import com.miage.areas.ExcavationArea;
import com.miage.areas.TouristicArea;
import com.miage.cards.CarCard;
import com.miage.cards.Card;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.ShovelCard;
import com.miage.cards.SpecificKnowledgeCard;
import com.miage.tokens.GeneralKnowledgeToken;
import com.miage.tokens.PointToken;
import com.miage.tokens.SpecificKnowledgeToken;
import com.miage.tokens.Token;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class LogDisplay {
    
    /**
     * Display logs, depending of the action executed by the player
     * @param b 
     * @param action action which 'played' by current player
     * @param o object used byy player (area,card,integer,token)
     * @return string which will display
     */
    public static String displayAction(Board b,String action,Object o){
        Date date = new Date(); 
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("[HH:mm:ss]");
        String message = dateFormat.format(date)+" ";
        PlayerToken pt = b.getCurrentPlayerToken();
        Player p = b.getPlayerByToken(pt);
        message += p.getName()+" ";
        switch(action){
            case "pickCard":
                Card c = (Card)o;
                message += "pioche la carte "+c.getDisplayName()+".";
                break;
            case "move":
                TouristicArea ta = (TouristicArea)o;
                message += "se déplace sur "+ta.getName()+".";
                break;    
            case "excavate":
                ExcavationArea ea = (ExcavationArea)o;
                message += "fouille en "+ea.getName()+".";
                break;    
            case "makeExposition":
                TouristicArea exa = (TouristicArea)o;
                message += "fait l'exposition à "+exa.getName()+".";
                break;
            case "changeFourCurrentCard":
                message += "change les 4 cartes du plateau.";
                break;
            case "chooseNumberWeeks":
                Integer i = (Integer)o;
                if(i>1)
                    message += "choisit "+i.toString()+" semaines pour fouiller.";
                else
                    message += "choisit "+i.toString()+" semaine pour fouiller.";
                    break;
            case "use":
                Card c2 = (Card)o;
                message += "utilise la carte "+c2.getDisplayName()+".";
                break;
            case "pickToken":
                Token t = (Token)o;
                message += "pioche un jeton "+t.getAreaName()+".";
                break;
            case "discard":
                Card c3 = (Card)o;
                message += "se défausse de sa carte "+c3.getDisplayName()+".";
                break;
            default:
                message = "Erreur : l'action "+action+" n'est pas reconnue ...";
                break;
        }
        return message;
    }
    
}


