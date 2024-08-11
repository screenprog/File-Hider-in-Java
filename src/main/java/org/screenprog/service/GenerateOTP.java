package org.screenprog.service;

import java.util.Random;

public class GenerateOTP {
    public static String getOTP()
    {
        return String.format("%4d", new Random().nextInt(10000));
    }
}
