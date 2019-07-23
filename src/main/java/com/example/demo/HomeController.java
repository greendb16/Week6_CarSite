package com.example.demo;


import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CatagoryRepository catagoryRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("catagories", catagoryRepository.findAll());
        model.addAttribute("cars", carRepository.findAll());
                return "index";
    }

    @GetMapping("/addCar")
    public String newCar(Model model){
        model.addAttribute("car", new Car());
        model.addAttribute("catagories", catagoryRepository.findAll());
        return "carForm";
    }

    @GetMapping("/addCatagory")
    public String newCatagory(Model model){
        model.addAttribute("catagory", new Catagory());
        return "catagoryForm";
    }

    @PostMapping("/processCar")
    public String processCar(@Valid Car car, BindingResult result, @RequestParam("file")MultipartFile file){

        if (file.isEmpty()){
            car.setPic(null);
        }
        if(!file.isEmpty()){
            try{
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("Resourcetype", "auto"));
                car.setPic(uploadResult.get("url").toString());
                carRepository.save(car);

            }catch (IOException e){
                e.printStackTrace();
                return "carForm";}
        }
        if(result.hasErrors()){
            return "carForm";
        }
        carRepository.save(car);
        return "redirect:/";
    }

    @PostMapping("/processCatagory")
    public String processCatagory(@Valid Catagory catagory, BindingResult result){
        if(result.hasErrors()){
            return "catagoryForm";
        }
        catagoryRepository.save(catagory);
        return "redirect:/";
    }

    @PostConstruct
    public void fillTables(){
        Catagory cat= new Catagory("Truck");
        catagoryRepository.save(cat);

        Car car= new Car("Toyota", 2006, 205.36, cat);
        car.setPic("https://res.cloudinary.com/dv2nrlkn5/image/upload/v1563914282/CR-Cars-Inline-PickUpTruck-BuyingGuide-Toyota-Tacoma-2019-4-19_vbtkea.jpg");
        carRepository.save(car);
        car= new Car("Ford", 1976, 999.6, cat);
        car.setPic("https://res.cloudinary.com/dv2nrlkn5/image/upload/v1563914303/images_ma7et7.jpg");
        carRepository.save(car);


        cat= new Catagory("Sedan");
        catagoryRepository.save(cat);

        car= new Car("Cruiser", 1995, 1222.25, cat);
        car.setPic("https://res.cloudinary.com/dv2nrlkn5/image/upload/v1563914540/2018-accord-sport-2-0-liter-turbo_s3e7qr.jpg");
        carRepository.save(car);
        car= new Car("Mini", 2003, 555.55, cat);
        car.setPic("https://res.cloudinary.com/dv2nrlkn5/image/upload/v1563914557/cooperhardtop4_classic_851_mleym4.png");
        carRepository.save(car);


        cat= new Catagory("Motorcycle");
        catagoryRepository.save(cat);

        car= new Car("Moto-2", 2001, 1250.25, cat);
        car.setPic("https://res.cloudinary.com/dv2nrlkn5/image/upload/v1563909445/ypftseigl5j7lkh32mxk.jpg");
        carRepository.save(car);
        car= new Car("Mini-Moto", 2017, 600.36, cat);
        car.setPic("https://res.cloudinary.com/dv2nrlkn5/image/upload/v1563914231/maxresdefault_zjx6kd.jpg");
        carRepository.save(car);
    }

    @RequestMapping("/update/{id}")
    public String updateCar(@PathVariable("id") long id, Model model){
        model.addAttribute("car", carRepository.findById(id).get());
        return "carForm";
    }

    @RequestMapping("/detail/{id}")
    public String detailCar(@PathVariable("id") long id, Model model){
        model.addAttribute(carRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") long id){
        carRepository.findById(id).get().setCatagory(null);
        carRepository.deleteById(id);
        return "redirect:/";
    }
}
