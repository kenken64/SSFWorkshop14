package sg.edu.nus.iss.Workshop14.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.iss.Workshop14.model.Contact;
import sg.edu.nus.iss.Workshop14.service.ContactsRedis;

@Controller
public class AddressbookController {

    private static final Logger logger = LoggerFactory.getLogger(AddressbookController.class);

    @Autowired
    ContactsRedis service;

    @GetMapping("/")
    public String contactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @GetMapping("/contact/{contactId}")
    public String getContact(Model model, @PathVariable(value = "contactId") String contactId) {
        Contact cc = service.findById(contactId);
        model.addAttribute("contact", cc);
        return "showContact";
    }

    @PostMapping("/contact")
    public String submitContact(@ModelAttribute Contact contact, Model model) {
        logger.info("contact id > " + contact.getId());
        service.save(contact);
        model.addAttribute("contact", contact);
        return "showContact";
    }
}
