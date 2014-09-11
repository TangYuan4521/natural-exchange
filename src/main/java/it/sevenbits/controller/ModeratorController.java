package it.sevenbits.controller;


import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.security.Role;
import it.sevenbits.util.SortOrder;
import it.sevenbits.util.form.UserSearchingForm;
import it.sevenbits.util.form.validator.UserSearchingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "moderator")
public class ModeratorController {
    private static final int DEFAULT_PAGE_SIZE = 7;
    private static final long MILLISECONDS_IN_A_DAY = 86400000;

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    public UserSearchingValidator userSearchingValidator;

    private final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    @RequestMapping(value = "/userlist.html", method = RequestMethod.GET)
    public ModelAndView showUsers(@RequestParam(value = "sortOrder", required = false)final String previousSortOrder ,
                                  @RequestParam(value = "pageSize", required = false)final Integer previousPageSize,
                                  @RequestParam(value = "currentPage", required = false) final Integer previousCurrentPage,
                                  @RequestParam(value = "currentCategory", required = false) final String previousCurrentCategory,
                                  @RequestParam(value = "currentKeyWords", required = false) final String previousKeyWords,
                                  @RequestParam(value = "currentDateFrom", required = false)final String previousDateFrom,
                                  @RequestParam(value = "currentDateTo", required = false)final String previousDateTo,
                                  final UserSearchingForm previousUserSearchingForm,
                                  final BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("user/allUsers");
        UserSearchingForm userSearchingForm = previousUserSearchingForm;

        //даты готовы
        if (previousDateFrom != null) {
            userSearchingForm.setDateFrom(previousDateFrom);
        } else if (userSearchingForm.getDateFrom() == null) {
            userSearchingForm.setDateFrom("");
        }
        if (previousDateTo != null) {
            userSearchingForm.setDateTo(previousDateTo);
        } else if (userSearchingForm.getDateTo() == null) {
            userSearchingForm.setDateTo("");
        }

        this.userSearchingValidator.validate(userSearchingForm, bindingResult);
        String stringDateFrom = previousUserSearchingForm.getDateFrom();
        String stringDateTo = previousUserSearchingForm.getDateTo();
        Long longDateFrom = null;
        Long longDateTo = null;
        if (bindingResult.hasErrors()) {
        } else {
            longDateFrom = strDateToUnixTimestamp(stringDateFrom);
            longDateTo = strDateToUnixTimestamp(stringDateTo);
            if (longDateTo != null) {
                longDateTo += this.MILLISECONDS_IN_A_DAY;
            }
        }

        //ключевые слова готовы
        String keyWordsFromForm = previousUserSearchingForm.getKeyWords();

        //сортировка готова
        SortOrder currentSortOrder;
        if (previousSortOrder == null) {
            currentSortOrder = SortOrder.DESCENDING;
        } else {
            currentSortOrder = SortOrder.valueOf(previousSortOrder);
        }
        String s = currentSortOrder.toString();

        //категории готовы
        boolean isBanned = true;
        String category = "";
        if (previousCurrentCategory != null) {
            if (userSearchingForm.getCategories() != null) {
                if (userSearchingForm.getCategories().length == 0 || userSearchingForm.getCategories()[0].equals("")) {
                    isBanned = false;
                    userSearchingForm.setCategories(new String[]{""});
                } else {
                    category = "banned";
                    userSearchingForm.setCategories(new String[]{"banned"});
                }
            } else {
                if (previousCurrentCategory.equals("")) {
                    isBanned = false;
                }
                userSearchingForm.setCategories(new String[]{previousCurrentCategory});
            }

        } else {
            category = "banned";
            userSearchingForm.setCategories(new String[]{"banned"});
        }


        PagedListHolder<User> users = new PagedListHolder<User>();
        List<User> listUsers = this.getAllUsersExceptCurrent(keyWordsFromForm, longDateFrom, longDateTo,
                                                             isBanned, currentSortOrder);
        users.setSource(listUsers);
        users.setSource(listUsers);

        int pageSize;
        if (previousPageSize != null) {
            pageSize = previousPageSize.intValue();
        } else {
            pageSize = this.DEFAULT_PAGE_SIZE;
        }
        users.setPageSize(pageSize);
        //if too many pages
        int noOfPage = users.getPageCount();
        int currentPage;
        if (previousCurrentPage == null || previousCurrentPage >= noOfPage) {
            currentPage = 0;
        } else {
            currentPage = previousCurrentPage.intValue();
        }
        users.setPage(currentPage);

        modelAndView.addObject("sortOrderDate", currentSortOrder.toString());
        modelAndView.addObject("currentCategory", category);
        modelAndView.addObject("pageSize", previousPageSize);
        modelAndView.addObject("noOfPage", noOfPage);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("currentDateFrom", stringDateFrom);
        modelAndView.addObject("currentDateTo", stringDateTo);

        modelAndView.addObject("users", users.getPageList());
        return modelAndView;
    }

    private static Long strDateToUnixTimestamp(String dt) {
        if (dt.equals("")) {
            return null;
        }
        DateFormat formatter;
        Date date = null;
        long unixtime;
        formatter = new SimpleDateFormat("dd.MM.yy");
        try {
            date = formatter.parse(dt);
        } catch (ParseException ex) {
            //Bad
            ex.printStackTrace();
        }
        unixtime = date.getTime();
        return unixtime;
    }

    private String emailFromCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = null;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) auth.getPrincipal();
            String name = userDetails.getUsername();
            return name;
        }
        return null;
    }

    private List<User> getAllUsersExceptCurrent(String keyWordsFromForm, Long longDateFrom, Long longDateTo,
                                                 boolean isBanned, SortOrder currentSortOrder) {
        List<User> listUsers = this.userDao.findUsersByKeywordsDateAndBan(keyWordsFromForm, longDateFrom, longDateTo,
                isBanned, currentSortOrder);
        String email = this.emailFromCurrentUser();
        if (email != null) {
            for (User currentUser : listUsers) {
                if (currentUser.getEmail().equals(email)) {
                    listUsers.remove(currentUser);
                    break;
                }
            }
        }
        return listUsers;
    }

    @RequestMapping(value = "/banuser.html", method = RequestMethod.POST)
    public String ban(@RequestParam(value = "email", required = true) final String userEmail,
                      @RequestParam(value = "ban", required = false) final String status) {
        String rederectAddress = "redirect:/moderator/userlist.html";
        if (userEmail == null) {
            return rederectAddress;
        }

        User userToBan = this.userDao.findUserByEmail(userEmail);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        } else {
            return rederectAddress;
        }

        String roleOfCurrentUser = userToBan.getRole();
        String insideRole = Role.createModeratorRole().toString();

        if (userDetails.getAuthorities().contains(Role.createModeratorRole())) {
            if (!roleOfCurrentUser.equals(Role.createAdminRole().toString())) {
                this.userDao.setBanned(userEmail);
            }
        }
//        if (status != null) {
//            rederectAddress += "?currentCategory=" + status;
//        }
        return rederectAddress;
    }
}
