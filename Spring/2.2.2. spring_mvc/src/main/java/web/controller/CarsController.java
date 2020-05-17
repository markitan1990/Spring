package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Car;
import web.service.CarService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class CarsController {
    @Autowired
    private CarService carService;

    @RequestMapping(value = "cars", method = RequestMethod.GET)
    public String printWelcome(@RequestParam(value = "locale", required = false) String locale, ModelMap model) {
        String head = "CARS";
        String brand = "Brand";
        String modelCar = "Model";
        String series = "Series";
        try {
            if (locale.equals("en")) {
                System.out.println("Используется язык по умолчанию ");
            } else if (locale.equals("ru")) {
                head = "МАШИНЫ";
                brand = "Марка";
                modelCar = "Модель";
                series = "Серия";
            }
        } catch (NullPointerException e) {
            System.out.println("Параметр не был назначен");
        }
        model.addAttribute("head", head);
        model.addAttribute("brand", brand);
        model.addAttribute("model", modelCar);
        model.addAttribute("series", series);

        model.addAttribute("cars", carService.getCars());

        return "cars";
    }
}
