package com.theironyard.controllers;

import com.theironyard.entities.Concert;
import com.theironyard.entities.User;
import com.theironyard.services.ConcertRepo;
import com.theironyard.services.UserRepo;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathandavidblack on 6/23/16.
 */
@Controller
public class ConcertTrackerController {

    @Autowired
    UserRepo users;
    @Autowired
    ConcertRepo concerts;

    static boolean openEdit = false;
    static boolean isMe = false;
    static boolean isNotMe = false;

    @RequestMapping(path="/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {

        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        User user = users.findByName(username);

        Iterable<Concert> ccts;
        ccts = concerts.findAll();
        ArrayList<Concert> canEditConcerts = new ArrayList<>();
        ArrayList<Concert> cannotEditConcerts = new ArrayList<>();
        for (Concert concert : ccts) {
            if (concert.getCreator() == user) {
                canEditConcerts.add(concert);
                isMe = true;
            }
        }
        for (Concert concert: ccts) {
            if (concert.getCreator() != user) {
                cannotEditConcerts.add(concert);
                isNotMe = true;
            }
        }
            model.addAttribute("isNotMe", cannotEditConcerts);
            model.addAttribute("editableConcerts", canEditConcerts);
            model.addAttribute("isMe", isMe);
            model.addAttribute("concerts", ccts);
            model.addAttribute("openEdit", openEdit);


        return "home";
    }
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        User user = users.findByName(username);
        if (user == null) {
            user = new User(username, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Wrong password");
        }
        session.setAttribute("username", username);
        return"redirect:/";
    }
    @RequestMapping(path = "/create-concert", method = RequestMethod.POST)
    public String createConcert(HttpSession session, String bandName, String venue, String date, int rating, String highlights) {
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        Concert concert = new Concert(bandName, venue, LocalDateTime.parse(date), rating, highlights, user);
        concerts.save(concert);

        return "redirect:/";
    }

    @RequestMapping(path = "/delete-concert", method = RequestMethod.POST)
    public String delete(int id, HttpSession session, User creator) throws Exception {
        Concert concert = concerts.findOne(id);
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        if(concert.getCreator() != user) {
            throw new Exception ("You May Only Delete Posts That You Create!");
        }
        else {
            concerts.delete(id);
        }

        return "redirect:/";
    }
    @RequestMapping(path ="/getEdit", method = RequestMethod.POST)
    public String updateConcert() {
        openEdit = true;
        return "redirect:/";
    }
    @RequestMapping(path="//edit-concert", method = RequestMethod.POST)
    public String actuallyUpdateConcert(int id, String bandName, String venue, String date, int rating, String highlights, String username) {
        Concert concert = new Concert(id, bandName, venue, LocalDateTime.parse(date), rating, highlights);
        concerts.save(concert);
        openEdit = false;
        return "redirect:/";
    }
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @RequestMapping(path = "/attended", method = RequestMethod.POST)
    public String attended(HttpSession session, int id) {
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        if (user == null) {
            return "redirect:/";
        }
        Concert concert = concerts.findOne(id);
        List<User> userList = concert.getUserList();
        if(concert.containsUser(user.getId())) {
            userList.add(user);
            concerts.save(concert);
            }

        else{
            return "redirect:/";
        }
        return "redirect:/";
    }
}


