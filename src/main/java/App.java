import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import models.Walker;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;


public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        // delete all walkers
        get("/walkers/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Walker.clearAllWalker();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

       // home page
        get ("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "layout.hbs");
        }, new HandlebarsTemplateEngine());

        //show new walker form
        get("/walkers/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "walker-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process new walker
        post("/walkers/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String walkerName = request.queryParams("walkerName");
            Walker newWalker = new Walker(walkerName);
            model.put("walker", newWalker);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //show all walkers
        get("/walkers", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Walker> walkers = Walker.getAll();
            model.put("walkers", walkers);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        //show individual
        get("/walkers/:id", (request, response) ->  {
            Map<String, Object> model = new HashMap<>();
            int idOfWalkerToFind = Integer.parseInt(request.params("id"));
            Walker foundWalker = Walker.findById(idOfWalkerToFind);
            model.put("walker", foundWalker);
            return new ModelAndView(model, "walker-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //show form walker update
        get("/walkers/:id/update", (request,response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfWalkerToEdit = Integer.parseInt(request.params("id"));
            Walker editWalker = Walker.findById(idOfWalkerToEdit);
            model.put("editWalker", editWalker);
            return new ModelAndView(model, "walker-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process update
        post("/walkers/:id/update", (request,response) -> {
            Map<String, Object> model = new HashMap<>();
            String newWalkerName = request.queryParams("walkerName");
            int idOfWalkerToEdit = Integer.parseInt(request.params("id"));
            Walker editWalker = Walker.findById(idOfWalkerToEdit);
            editWalker.update(newWalkerName);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        // delete individual
        get("/walkers/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfWalkerToDelete = Integer.parseInt(req.params("id"));
            Walker deleteWalker = Walker.findById(idOfWalkerToDelete);
            deleteWalker.deleteWalker();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

    }
}
