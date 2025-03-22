package ma.tuto.productmanagerapi.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur d'exemple pour démontrer l'authentification et l'autorisation.
 */
@RestController
@RequestMapping(path = "api/v1")
public class DemoController {

    // Endpoint public
    @GetMapping(path = "/public")
    public String publicCtrl() {
        return "Hello Public World";
    }

    // Endpoint private user
    @GetMapping(path = "/private-user")
    public String privateUserCtrl() {
        return "Hello Private Users World";
    }

    // Endpoint private admin
    @GetMapping(path = "/private-admin")
    public String privateAdminCtrl() {
        return "Hello Private Admins World";
    }

    // Endpoint ressources
    @GetMapping(path = "/ressources")
    public String privateResourcesCtrl() {
        return "Ressources World";
    }

    // Endpoint private
    @GetMapping(path = "/ressources/res1")
    public String privateResourcesResCtrl() {
        return "Ressources Res1 World";
    }
}
