package by.mops.bet.controllers;

import by.mops.bet.DTO.BetDto;
import by.mops.bet.DTO.EventDto;
import by.mops.bet.DTO.UserBetDto;
import by.mops.bet.DTO.UserDto;
import by.mops.bet.download.CreateExcel;
import by.mops.bet.download.MediaTypeUtils;
import by.mops.bet.model.*;
import by.mops.bet.security.MyUserDetails;
import by.mops.bet.services.*;
import by.mops.bet.validator.BalanceValidator;
import by.mops.bet.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {


    @Autowired
    private UserValidator userValidator;

    @Autowired
    private BalanceValidator balanceValidator;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private BetService betService;

    @Autowired
    private TypeOfBetService typeOfBetService;

    @Autowired
    private UserBetService userBetService;

    private static final String DIRECTORY = "C:\\Users\\User\\IdeaProjects\\MopsBet\\src\\main\\resources\\static\\TempFiles";
    private static final String DEFAULT_FILE_NAME = "file.xls";

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        Map<Long, User> users = userService.mapAll();
        model.addAttribute("listUsers", users);
        Map<Long, Event> events = eventService.mapAll();
        model.addAttribute("listEvents", events);
        Map<Long, Bet> bets = betService.mapAll();
        model.addAttribute("listBets", bets);
        Map<Long, String> mapTypes = typeOfBetService.listAll();
        model.addAttribute("mapTypes", mapTypes);

        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(user.getUser());
        String role = user.getRole();
        if (role.equals("ROLE_USER")) {
            List<UserBet> userBets = userBetService.listUserBets(user.getUser().getId());
            model.addAttribute("listUserBets", userBets);
            return "user_home";
        } else {
            Map<Long, UserBet> userBetMap = userBetService.mapAll();
            model.addAttribute("listUserBets", userBetMap);
            return "index";
        }
    }



    @RequestMapping("/download1")
    public ResponseEntity<InputStreamResource> downloadFile1(
            @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {
        List<UserBet> userBets = null;
        Map<Long, User> users = userService.mapAll();
        Map<Long, Event> events = eventService.mapAll();
        Map<Long, Bet> bets = betService.mapAll();
        Map<Long, String> mapTypes = typeOfBetService.listAll();
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRole().equals("ROLE_USER")) {
            userBets = userBetService.listUserBets(user.getUser().getId());
        } else {
            userBets = userBetService.listAll();
        }
        CreateExcel.ctreateExcelFile(user, users, events, bets, mapTypes, userBets);

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        File file = new File(DIRECTORY + "/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }



    //Bet--------------------------------------------------------------------------------
    //Bet--------------------------------------------------------------------------------
    //Bet--------------------------------------------------------------------------------

    @RequestMapping("/new_bet/{id}")
    public String showNewBetPage(@PathVariable(name = "id") Long id, Model model) {
        Map<Long, String> mapTypes = typeOfBetService.listAll();
        model.addAttribute("mapTypes", mapTypes);
        Event event = eventService.get(id);
        model.addAttribute("event", event);
        Bet bet = new Bet();
        bet.setEvent_id(id);
        model.addAttribute("bet", bet);

        return "new_bet";
    }


    @RequestMapping("/edit_bet/{id}")
    public ModelAndView showEditBetPage(@PathVariable(name = "id") Long id) {

        ModelAndView mav = new ModelAndView("edit_bet");
        Bet bet = betService.get(id);
        Event event = eventService.get(bet.getEvent_id());
        mav.addObject("event", event);
        mav.addObject("bet", bet);
        Map<Long, String> mapTypes = typeOfBetService.listAll();
        mav.addObject("mapTypes", mapTypes);

        return mav;
    }

    @RequestMapping(value = "/save_bet", method = RequestMethod.POST)
    public String saveBet(@ModelAttribute("event") Bet bet, @ModelAttribute("type_list") String type_of_bet) {
        Map<Long, String> mapTypes = typeOfBetService.listAll();
        Long type_of_bet_id = null;
        for (Map.Entry<Long, String> entry : mapTypes.entrySet()) {
            if (entry.getValue().equals(type_of_bet))
                type_of_bet_id = entry.getKey();
        }
        bet.setType_of_bet_id(type_of_bet_id);
        BetDto betDto = new BetDto(bet.getId(), bet.getEvent_id(), bet.getType_of_bet_id(), bet.getValue(), bet.getCoefficient());
        if (bet.getId() == null)
            betService.save(bet);
        else
            betService.updateBet(betDto);


        return "redirect:/";
    }

    @RequestMapping("/delete_bet/{id}")
    public String deleteBet(@PathVariable(name = "id") Long id) {
        betService.delete(id);
        return "redirect:/";
    }


// USER BET--------------------------------------------------------------------------
// USER BET--------------------------------------------------------------------------
// USER BET--------------------------------------------------------------------------


    @RequestMapping("/make_bet/{id}")
    public String showNewUserBetPage(@PathVariable(name = "id") Long id, Model model) {
        Bet bet = betService.get(id);
        Event event = eventService.get(bet.getEvent_id());
        TypeOfBet typeOfBet = typeOfBetService.get(bet.getType_of_bet_id());
        //MyUserDetails myUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(event);
        model.addAttribute(bet);
        model.addAttribute("type_of_bet", typeOfBet.getName());
        UserBet userBet = new UserBet();
        //userBet.setUser_id(myUser.getUser().getId());
        userBet.setBet_id(bet.getId());
        model.addAttribute("userBet", userBet);

        return "make_bet";
    }

    @RequestMapping(value = "/save_user_bet", method = RequestMethod.POST)
    public String saveUserBet(@ModelAttribute("userBet") UserBet userBet, BindingResult bindingResult, Model model, @ModelAttribute("bet") Bet bet,
                              @ModelAttribute("event") Event event, @ModelAttribute("type_of_bet") String type_of_bet) {
        balanceValidator.validate(userBet, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(event);
            model.addAttribute(bet);
            model.addAttribute(userBet);
            model.addAttribute("type_of_bet", type_of_bet);

            return "make_bet";
        }
        MyUserDetails myUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        myUser.setBalance(myUser.getUser().getBalance() - userBet.getValue());
        User admin = userService.get(2L);
        UserDto userDto1 = new UserDto(admin.getId(), admin.getUsername(), admin.getPassword(),
                admin.getRole(), admin.getEnabled(), admin.getFirstName(), admin.getLastName(),
                admin.getBalance() + userBet.getValue());
        userService.updateUser(userDto1);

        userBet.setUser_id(myUser.getUser().getId());
        userBet.setStatus("событие не окончено");

        UserDto userDto = new UserDto(myUser.getUser().getId(), myUser.getUser().getUsername(), myUser.getUser().getPassword(),
                myUser.getUser().getRole(), myUser.getUser().getEnabled(), myUser.getUser().getFirstName(), myUser.getUser().getLastName(),
                myUser.getUser().getBalance());

        userService.updateUser(userDto);

        //UserBetDto userBetDto = new UserBetDto(userBet.getId(), userBet.getValue(), userBet.getStatus(), userBet.getBet_id(), userBet.getUser_id());

        if (userBet.getId() == null)
            userBetService.save(userBet);
        /*else
            userBetService.updateUserBet(userBetDto);*/


        return "redirect:/";
    }

// Event-----------------------------------------------------------------------------
// Event-----------------------------------------------------------------------------
// Event-----------------------------------------------------------------------------

    @RequestMapping("/new_event")
    public String showNewEventPage(Model model) {
        Event event = new Event();
        event.setStatus("не завершен");
        model.addAttribute("event", event);

        return "new_event";
    }



    @RequestMapping("/edit_event/{id}")
    public ModelAndView showEditEventPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_event");
        Event event = eventService.get(id);
        mav.addObject("event", event);

        return mav;
    }

    @RequestMapping("/finish_event/{id}")
    public ModelAndView showFinishEventPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("finish_event");
        Event event = eventService.get(id);
        event.setStatus("завершен");
        mav.addObject("event", event);

        return mav;
    }

    @RequestMapping(value = "/save_finish_event", method = RequestMethod.POST)
    public String saveFinishedEvent(@ModelAttribute("event") Event event) {
        EventDto eventDto = new EventDto(event.getId(), event.getTeam1(), event.getTeam2(), event.getStatus(), event.getResult(), event.getTotal());
        eventService.updateEvent(eventDto);

        List<Bet> bets = betService.listAllByEvent_id(event.getId());

        for(Bet bet:bets){
            switch(Integer.parseInt(bet.getType_of_bet_id().toString())){
                case 1:
                case 4:
                    if(bet.getValue().equals(event.getResult())){

                        paymentBet(bet);
                    }
                    else{
                        for(UserBet userBet:bet.getUserBets()){
                            userBetService.saveUserBetStatus(userBet.getId(), "Проигрыш");
                        }
                    }
                    break;
                case 3:
                    if(Double.parseDouble(bet.getValue()) <= event.getTotal()){
                        paymentBet(bet);
                    }
                    else{
                        for(UserBet userBet:bet.getUserBets()){
                            userBetService.saveUserBetStatus(userBet.getId(), "Проигрыш");
                        }
                    }
                    break;
                case 5:
                    if(Double.parseDouble(bet.getValue()) >= event.getTotal()){
                        paymentBet(bet);
                    }
                    else{
                        for(UserBet userBet:bet.getUserBets()){
                            userBetService.saveUserBetStatus(userBet.getId(), "Проигрыш");
                        }
                    }
                    break;
            }
        }


        return "redirect:/";
    }

    private void paymentBet(Bet bet) {
        for(UserBet userBet: bet.getUserBets()){
            double sumWin = userBet.getValue() * bet.getCoefficient();
            userBetService.saveUserBetStatus( userBet.getId(), "Выигрыш:" + sumWin);
            User myUser = userService.get(userBet.getUser_id());
            UserDto userDto = new UserDto(userBet.getUser_id(), myUser.getUsername(), myUser.getPassword(),
                    myUser.getRole(), myUser.getEnabled(), myUser.getFirstName(), myUser.getLastName(),
                    myUser.getBalance() + sumWin);
            userService.updateUser(userDto);
            User admin = userService.get(2L);
            UserDto userDto1 = new UserDto(admin.getId(), admin.getUsername(), admin.getPassword(),
                    admin.getRole(), admin.getEnabled(), admin.getFirstName(), admin.getLastName(),
                    admin.getBalance() - sumWin);
            userService.updateUser(userDto1);
        }
    }

    @RequestMapping(value = "/save_event", method = RequestMethod.POST)
    public String saveEvent(@ModelAttribute("event") Event event) {
        EventDto eventDto = new EventDto(event.getId(), event.getTeam1(), event.getTeam2(), event.getStatus(), event.getResult(), event.getTotal());
        System.out.println(event);
        System.out.println(eventDto);
        if (event.getId() == null)
            eventService.save(event);
        else
            eventService.updateEvent(eventDto);


        return "redirect:/";
    }

    @RequestMapping("/delete_event/{id}")
    public String deleteEvent(@PathVariable(name = "id") int id) {
        eventService.delete(id);
        return "redirect:/";
    }


    //USER------------------------------------------------------------------------------------
    //USER------------------------------------------------------------------------------------
    //USER------------------------------------------------------------------------------------

    @RequestMapping("/edit_user/{id}")
    public ModelAndView showEditUserPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_user");
        User user = userService.get(id);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(value = "/save_user", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        UserDto userDto = new UserDto(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getEnabled(),
                user.getFirstName(),
                user.getLastName(),
                user.getBalance());
        if (user.getId() == null)
            userService.save(user);
        else
            userService.updateUser(userDto);

        return "redirect:/";
    }


    @RequestMapping("/balance")
    public String showBalance(Model model) {
        MyUserDetails myUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(myUser.getUser());
        return "balance";
    }

    @RequestMapping(value = "/update_balance", method = RequestMethod.POST)
    public String updateBalance(@ModelAttribute("user") User user, @ModelAttribute("action") String action) {
        MyUserDetails myUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (action.equals("Get money from account"))
            user.setBalance(user.getBalance() * -1);
        UserDto userDto = new UserDto(myUser.getUser().getId(), myUser.getUser().getUsername(), myUser.getUser().getPassword(),
                myUser.getUser().getRole(), myUser.getUser().getEnabled(), myUser.getUser().getFirstName(), myUser.getUser().getLastName(),
                myUser.getUser().getBalance() + user.getBalance());
        System.out.println(user);
        myUser.setBalance(userDto.getBalance());

        userService.updateUser(userDto);

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginView(Model model, String error) {
        if (error != null) {
            model.addAttribute("message", "Username or password is incorrect.");
        }
        return "login";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.saveNewUser(userForm);

        return "redirect:/";
    }


    // handler methods...
}
