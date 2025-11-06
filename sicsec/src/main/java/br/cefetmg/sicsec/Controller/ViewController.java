/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author davig
 */

@Controller
public class ViewController {

    @GetMapping("/homeAdmin")
    public String homeAdminView() {
        return "admin-turmas";
    }
    
}
