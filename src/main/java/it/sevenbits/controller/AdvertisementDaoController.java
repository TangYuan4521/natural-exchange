package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "advertisement")
public class AdvertisementDaoController {

    @Resource(name = "advertisementDao")
    private AdvertisementDao advertisementDao;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list() {

        //Создаем вьюшку по list.jsp, которая выведется этим контроллером на экран
        ModelAndView modelAndView = new ModelAndView("advertisement/list");
        List<Advertisement> advertisements = this.advertisementDao.findAll();
        modelAndView.addObject("advertisements", advertisements);
        List<String> categories = new ArrayList<String>();
        categories.add("Игрушки");
        categories.add("Одежда");
        categories.add("Мебель");
        modelAndView.addObject("categories", categories);

        return modelAndView;
    }

    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public ModelAndView view(@RequestParam(required = true) String id) {

        //Создаем вьюшку по list.jsp, которая выведется этим контроллером на экран
        ModelAndView modelAndView = new ModelAndView("advertisement/list");
        List<Advertisement> advertisements = this.advertisementDao.findAll();
        modelAndView.addObject("advertisements", advertisements);
        List<String> categories = new ArrayList<String>();
        categories.add("Игрушки");
        categories.add("Одежда");
        categories.add("Мебель");
        modelAndView.addObject("categories", categories);

        return modelAndView;
    }

}