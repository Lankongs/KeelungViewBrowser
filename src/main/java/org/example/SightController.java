package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/sights")
public class SightController {

    @GetMapping("/{zone}")
    public Sight[] getSights(@PathVariable String zone)
            throws IOException {

        KeelungSightsCrawler crawler =
                new KeelungSightsCrawler();

        return crawler.getItems(zone);
    }
}