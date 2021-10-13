package by.htp.pazl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import by.htp.pazl.persist.*;

import java.security.Principal;

@Controller
public class ShoppingListController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingListController.class);

    private final ShoppingItemRepository shoppingItemRepository;

    private final UserRepository userRepository;

    @Autowired
    public ShoppingListController(ShoppingItemRepository shoppingItemRepository, UserRepository userRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String indexPage(Model model, Principal principal) {
        logger.info("User name: {}", principal.getName());

        model.addAttribute("items", shoppingItemRepository.findByUserUsername(principal.getName()));
        model.addAttribute("item", new ShoppingItem());
        return "index";
    }

    @PostMapping
    public String newShoppingItem(ShoppingItem item, Principal principal) {
        logger.info("User name: {}", principal.getName());

        User user = userRepository.findByUsername(principal.getName()).get();
        item.setUser(user);
        shoppingItemRepository.save(item);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteShoppingItem(@PathVariable("id") long id) {
        shoppingItemRepository.deleteById(id);
        return "redirect:/";
    }
}

