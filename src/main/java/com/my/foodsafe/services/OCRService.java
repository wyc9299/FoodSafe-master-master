package com.my.foodsafe.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OCRService implements IOCRService {

    @Value("${google.vision.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OCRService() {
        this.restTemplate = new RestTemplate();
    }

    public String processOCR(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // 将文件转换为 Base64 编码字符串
        String base64Image = java.util.Base64.getEncoder().encodeToString(file.getBytes());

        // 创建 JSON 请求体
        JsonObject requestBody = new JsonObject();
        JsonArray requestsArray = new JsonArray();
        JsonObject requestObject = new JsonObject();
        JsonObject imageObject = new JsonObject();
        imageObject.addProperty("content", base64Image);
        JsonObject featureObject = new JsonObject();
        featureObject.addProperty("type", "TEXT_DETECTION");
        requestObject.add("image", imageObject);
        requestObject.add("features", new JsonArray());
        requestObject.getAsJsonArray("features").add(featureObject);
        requestsArray.add(requestObject);
        requestBody.add("requests", requestsArray);

        // 设置 HTTP 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        // 调用 Google Cloud Vision API
        String visionApiUrl = "https://vision.googleapis.com/v1/images:annotate?key=" + apiKey;
        ResponseEntity<String> response = restTemplate.postForEntity(visionApiUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // 解析响应体
            JsonObject jsonResponse = JsonParser.parseString(response.getBody()).getAsJsonObject();
            String ocrText = jsonResponse.getAsJsonArray("responses").get(0)
                    .getAsJsonObject().getAsJsonArray("textAnnotations").get(0)
                    .getAsJsonObject().get("description").getAsString();

            // 使用正则表达式提取品名
            Pattern pattern = Pattern.compile("品名/用途:\\s*([^/]+)");
            Matcher matcher = pattern.matcher(ocrText);
            String productName = "";
            if (matcher.find()) {
                productName = matcher.group(1);
            }
            return productName;
        } else {
            throw new RuntimeException("Failed to call Google Cloud Vision API: " + response.getStatusCode());
        }
    }
}
