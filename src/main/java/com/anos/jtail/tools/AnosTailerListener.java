package com.anos.jtail.tools;

import org.apache.commons.io.input.TailerListenerAdapter;

/**
 *
 * @author Gani GÜRGAH <gani.gurgah@qnbsigorta.com>
 */
public class AnosTailerListener extends TailerListenerAdapter {

    public void handle(String line) {
        if (!Utils.isEmtpyStr(line)) {
            System.out.println(line);
        }
    }
}
