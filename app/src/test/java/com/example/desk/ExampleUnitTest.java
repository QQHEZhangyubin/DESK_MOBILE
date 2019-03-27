package com.example.desk;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
       String k = "/storage/emulated/0/wode/outtemp.png";
        String[] dataStr = k.split("/");
        String fileTruePath = "/sdcard";
        for(int i=4;i<dataStr.length;i++){
            fileTruePath = fileTruePath+"/"+dataStr[i];
        }
        System.out.println(fileTruePath);
    }
}