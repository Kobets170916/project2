package by.htp.pazl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import by.htp.pazl.persist.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ItemController {

   // private static final Logger logger = LoggerFactory.getLogger(ShoppingListController.class);
  // @Autowired
   private final ItemRepository itemRepository;

   @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @GetMapping("/item")
    public String itemMain(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "itemMain";

    }@GetMapping("/item/itemShow")
    public String itemShow(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "itemShow";
    }

    @GetMapping("/item/itemAdd")
    public String itemAdd(Model model) {

        return "itemAdd";
    }

    @PostMapping("/item/itemAdd")
    public String itemPostAdd(@RequestParam String name, @RequestParam int count, @RequestParam String description, Model model){
        Item item = new Item(name, count, description);
        itemRepository.save(item);
        return "redirect:/item";
    }
    @GetMapping("/item/{id}")
    public String itemDetails(@PathVariable(value = "id") long id, Model model) {
        if(!itemRepository.existsById(id)){
            return "redirect:/item";
        }

        Optional<Item> item = itemRepository.findById(id);
        ArrayList<Item> res = new ArrayList<>();
        item.ifPresent(res::add);
        model.addAttribute("item", res);
        return "itemDetails";
    }

    @GetMapping("/item/{id}/edit")
    public String itemEdit(@PathVariable(value = "id") long id, Model model) {
        if(!itemRepository.existsById(id)){
            return "redirect:/item";
        }
        Optional<Item> item = itemRepository.findById(id);
        ArrayList<Item> res = new ArrayList<>();
        item.ifPresent(res::add);
        model.addAttribute("item", res);
        return "itemEdit";
    }
    @PostMapping("/item/{id}/edit")
    public String itemPostUpdate(@PathVariable(value = "id") long id,@RequestParam String name, @RequestParam int count, @RequestParam String description, Model model){
        Item item = itemRepository.findById(id).orElseThrow();
        item.setName(name);
        item.setCount(count);
        item.setDescription(description);
        itemRepository.save(item);
        return "redirect:/item";
    }
    @PostMapping("/item/{id}/remove")
    public String itemPostUpdate(@PathVariable(value = "id") long id, Model model){
        Item item = itemRepository.findById(id).orElseThrow();
        itemRepository.delete(item);
        return "redirect:/item";
    }
}