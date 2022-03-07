package com.sandbox.main.routes;

import com.sandbox.main.config.ControllersConfig;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class Routes {
    private ControllersConfig controllers;

    public Routes(ControllersConfig controllers) {
        this.controllers = controllers;
    }
    
    public void setupRoutes() {
        get("/healthcheck", (req, res) -> controllers.systemController.healthCheck(req, res));
        path("/jdwe", () -> {
            get("/setup", (req, res) -> controllers.systemController.setupData(req, res));
            path("/person", ()->{
                get("/person/:id", (req,res) -> controllers.personController.getPersonById(req,res),controllers.jsonMapper);
                get("/person", (req,res)-> controllers.personController.getPersonByCriteria(req,res), controllers.jsonMapper);
                put("/person", (req,res)-> controllers.personController.updatePerson(req,res), controllers.jsonMapper);
                post("/person", (req,res)-> controllers.personController.insertPerson(req,res), controllers.jsonMapper);
                delete("/person/:id", (req,res)-> controllers.personController.deletePerson(req,res));
            });
        });
    }

}
