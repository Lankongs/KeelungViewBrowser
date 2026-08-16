package org.example;

public class Main {

    public static void main(String[] args)
            throws Exception {

        KeelungSightsCrawler crawler =
                new KeelungSightsCrawler();

        Sight[] sights =
                crawler.getItems("qidu");

        for (Sight s : sights) {
            System.out.println(s);
            System.out.println(
                    "------------------------------"
            );
        }
    }
}