/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.util;

import java.util.Random;

/**
 *
 * @author Mohammad Hosny
 */
public class Util {

    // Get random integer between [1000 ... 100000000]
    public static int getRandomNumberInRange() {
        int min = 1000;
        int max = 100000000;

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
