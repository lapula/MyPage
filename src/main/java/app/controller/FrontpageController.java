package app.controller;

import app.domain.StoryEntry;
import app.repository.StoryEntryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value="/frontpage")
public class FrontpageController {
    
    @Autowired
    private StoryEntryRepository ser;
    
    @RequestMapping(method = RequestMethod.GET)
    public String getFrontpage(Model model) {
        
        model.addAttribute("stories", ser.findAll());
        
        return "frontpage";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute StoryEntry se) {
        if (se.getName() != null && se.getName() != "") {
            ser.save(se);
        }
        return "redirect:/frontpage";
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@RequestParam long storyId) {
        ser.delete(storyId);
        return "redirect:/frontpage";
    }
}
