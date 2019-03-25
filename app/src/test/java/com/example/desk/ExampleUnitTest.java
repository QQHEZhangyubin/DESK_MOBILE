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
        //assertEquals(4, 2 + 2);
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
        Matcher m = p.matcher( "东区401" );
        if (m.find()){
            System.out.println(m.group());
        }
        if (m.find()){
            System.out.println(m.group());
        }
        //String location = m.group(1);
        //System.out.println(location);
        //String classroom = m.group(2);
       // System.out.println(classroom);
        //while (m.find()){
          ///  System.out.println(m.group());
        //}

    }
}