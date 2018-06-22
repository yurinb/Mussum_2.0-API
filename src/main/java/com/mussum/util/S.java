/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mussum.util;

import java.time.LocalDateTime;

/**
 *
 * @author Yuri
 */
public class S {

    private static final String SEPARATOR = "_-> ";

    public static void out(String txt, Object emitter) {
        String output = "";

        String emitterClassLog = emitter.getClass().getSimpleName();
        String threadLog = "Thread::"+String.valueOf(Thread.currentThread().getId());
        

        output += SEPARATOR + stringWithLength(info_timeLog(), 17);
        output += SEPARATOR + stringWithLength(emitterClassLog, 17);
        output += SEPARATOR + stringWithLength(threadLog, 17);
        
        System.out.println(output + " :: " + txt);
    }

    private static String info_timeLog() {
        LocalDateTime time = LocalDateTime.now();
        int day = time.getDayOfMonth();
        int hour = time.getHour();
        int min = time.getMinute();
        int seg = time.getSecond();
        return "D:" + day + "." + hour + "h." + min + "m." + seg + "s.";
    }

    private static String buildTxtInsideTxt(String inside, String txt) {
        return inside + txt.substring(inside.length());
    }

    private static String stringWithLength(String info, int length) {
        String backgroundTxt = buildStringRepetingTimes(" ", length);
        boolean fit = info.length() <= length;

        if (fit) {
            return buildTxtInsideTxt(info, backgroundTxt);
        }

        return null;
    }

    private static String buildStringRepetingTimes(String txt, int times) {
        String generatedTxt = "";
        for (int i = 0; i < times; i++) {
            generatedTxt += txt;
        }
        return generatedTxt;
    }

}
