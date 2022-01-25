package com.reactivestax.spring5mvc.web;

import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import com.reactivestax.spring5mvc.repository.WidgetRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WidgetController {

    @Autowired
    WidgetRepositoryImp widgetRepositoryImp;

    private Widget widget1 =null;
    /**
     * Load the new widget page.
     * @param model
     * @return
     */
    @GetMapping("/widget/new")
    public String newWidget(Model model) {
        model.addAttribute("widget", new Widget());
        return "widgetform";
    }

    /**
     * Create a new widget.
     * @param widget
     * @param model
     * @return
     */
    @PostMapping("/widget")
    public String createWidget(Widget widget, Model model) {
        if(widget1==null) {
            widgetRepositoryImp.save(widget);
        }else{
            widgetRepositoryImp.update(widget);
            widget1=null;
        }
        return "redirect:/widget/" + widget.getId();
    }

    /**
     * Get a widget by ID.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/widget/{id}")
    public ModelAndView getWidgetById(@PathVariable Long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("widget");
        modelAndView.addObject("widget", widgetRepositoryImp.findById(Math.toIntExact(id)));
        return  modelAndView;
    }

    /**
     * Get all widgets.
     * @param model
     * @return
     */
    @GetMapping("/widgets")
    public String getWidgets(Model model) {
        model.addAttribute("widgets", widgetRepositoryImp.findAll());
        return "widgets";
    }

    /**
     * Load the edit widget page for the widget with the specified ID.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/widget/edit/{id}")
    public String editWidget(@PathVariable Long id, Model model) {
        widget1 = widgetRepositoryImp.findById(Math.toIntExact(id));
       model.addAttribute("widget", widget1);
        return "widgetform";
    }

    /**
     * Update a widget.
     * @param widget
     * @return
     */
    @PostMapping("/widget/{id}")
    public String updateWidget(Widget widget) {
        widgetRepositoryImp.save(widget);
        return "redirect:/widget/" + widget.getId();
    }

    /**
     * Delete a widget by ID.
     * @param id
     * @return
     */
    @GetMapping("/widget/delete/{id}")
    public String deleteWidget(@PathVariable  Long id) {
        widgetRepositoryImp.deleteById(Math.toIntExact(id));
        return "redirect:/widgets";
    }
}
