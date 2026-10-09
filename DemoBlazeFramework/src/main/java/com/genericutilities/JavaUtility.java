package com.genericutilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class JavaUtility {

    /**
     * This method is used to generate random number.
     * @return random number
     */
    public int getRandomNumber() {

        Random random = new Random();

        int randomNumber = random.nextInt(1000);

        return randomNumber;
    }


    /**
     * This method is used to get current date.
     * @return current date
     */
    public String getCurrentDate() {

        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        String currentDate = sdf.format(date);

        return currentDate;
    }


    /**
     * This method is used to get current date and time.
     * @return current date and time
     */
    public String getCurrentDateAndTime() {

        Date date = new Date();

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

        String currentDateAndTime = sdf.format(date);

        return currentDateAndTime;
    }


    /**
     * This method is used to generate random email.
     * @return unique email
     */
    public String getRandomEmail() {

        Random random = new Random();

        int randomNumber = random.nextInt(10000);

        String email = "demoblaze" + randomNumber + "@gmail.com";

        return email;
    }


    /**
     * This method is used to generate random username.
     * @return unique username
     */
    public String getRandomUsername() {

        Random random = new Random();

        int randomNumber = random.nextInt(10000);

        String username = "user" + randomNumber;

        return username;
    }


    /**
     * This method is used to generate random string.
     * @return random string
     */
    public String getRandomString() {

        Random random = new Random();

        int randomNumber = random.nextInt(10000);

        String randomString = "Test" + randomNumber;

        return randomString;
    }


    /**
     * This method is used to pause execution.
     * @param milliseconds
     */
    public void sleep(long milliseconds) {

        try {

            Thread.sleep(milliseconds);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}