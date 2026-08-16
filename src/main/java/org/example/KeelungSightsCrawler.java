package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class KeelungSightsCrawler {

    private static final String BASE_URL =
            "https://okgo.tw/buty/keelung.html";


    public Sight[] getItems(String zone) throws IOException {

        // "qidu" -> "七堵區"
        String zoneName = getZoneName(zone);

        if (zoneName.isEmpty()) {
            return new Sight[0];
        }

        // 基隆景點列表
        Document listDoc = fetch(BASE_URL);

        // 尋找指定行政區
        Element zoneSection = null;

        String targetTitle =
                "基隆市" + zoneName + "景點";

        for (Element section :
                listDoc.select("div.sec2")) {

            if (section.text().startsWith(targetTitle)) {
                zoneSection = section;
                break;
            }
        }

        if (zoneSection == null) {
            return new Sight[0];
        }

        // 該行政區底下的所有景點
        Elements items =
                zoneSection.select("ul.dot > li");

        ArrayList<Sight> sights =
                new ArrayList<>();

        for (Element item : items) {

            // 尋找詳細頁連結
            Element link = item.selectFirst(
                    "a[href*='butyview.html?id=']"
            );

            if (link == null) {
                continue;
            }

            String detailUrl =
                    link.absUrl("href");

            Sight sight =
                    parseSight(detailUrl);

            sights.add(sight);
        }

        return sights.toArray(new Sight[0]);
    }


    private String getZoneName(String zone) {

        return switch (zone) {

            case "renai" ->
                    "仁愛區";

            case "xinyi" ->
                    "信義區";

            case "zhongzheng" ->
                    "中正區";

            case "zhongshan" ->
                    "中山區";

            case "anle" ->
                    "安樂區";

            case "nuannuan" ->
                    "暖暖區";

            case "qidu" ->
                    "七堵區";

            default ->
                    "";
        };
    }


    // 解析第二層景點
    private Sight parseSight(String detailUrl)
            throws IOException {

        Document detailDoc =
                fetch(detailUrl);

        Sight sight =
                new Sight();


        // 景點名稱

        String sightName =
                detailDoc
                        .select("div.sec3 h2")
                        .text();

        sight.setSightName(sightName);


        // 行政區

        Element zoneElement =
                detailDoc.selectFirst(
                        "a[href*='buty/town.html']"
                );

        String zone = "";

        if (zoneElement != null) {
            zone = zoneElement.text();
        }

        sight.setZone(zone);


        // Category(OKGO沒有)

        sight.setCategory("");


        // 景點圖片

        String photoURL =
                detailDoc
                        .select("#Buty_Title_Pic img")
                        .attr("abs:src");

        sight.setPhotoURL(photoURL);


        // 景點介紹

        String description = "";

        for (Element div :
                detailDoc.select("div.sec3 > div")) {

            if (div.className().isEmpty()
                    && div.id().isEmpty()
                    && !div.text().isBlank()) {

                description = div.text();
                break;
            }
        }

        sight.setDescription(description);


        // 地址

        Element sec3 =
                detailDoc.selectFirst("div.sec3");

        String address = "";

        if (sec3 != null) {

            String info =
                    sec3.ownText();

            int addressIndex =
                    info.indexOf("地址：");

            if (addressIndex != -1) {

                address =
                        info.substring(
                                addressIndex
                                        + "地址：".length()
                        ).trim();
            }
        }

        sight.setAddress(address);


        return sight;
    }


    private Document fetch(String url)
            throws IOException {

        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();
    }
}