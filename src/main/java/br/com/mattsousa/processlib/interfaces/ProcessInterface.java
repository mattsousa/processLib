/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mattsousa.processlib.interfaces;

import java.util.HashMap;

/**
 *
 * @author mattsousa
 */
public interface ProcessInterface {
    public void run(Object subList[], HashMap<String, Object> args);
}
