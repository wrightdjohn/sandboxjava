package com.sandbox.main;

import com.sandbox.main.config.ControllersConfig;
import com.sandbox.main.routes.Routes;

public class Application {
    static Routes routes = new Routes(ControllersConfig.instance);
    public static void main(String[] args) {
        routes.setupRoutes();
    }
}
