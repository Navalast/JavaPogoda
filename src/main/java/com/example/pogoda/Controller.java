package com.example.pogoda;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.*;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button GetData;

    @FXML
    private TextField cityId;

    @FXML
    private Text info;

    @FXML
    private Text temp_feel;

    @FXML
    private Text temp_humidity;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text temp_press;

    @FXML
    void initialize() {
        // При нажатии на кнопку
        GetData.setOnAction(event -> {
            // Получаем данные из текстового поля
            String getUserCity = cityId.getText().trim();

            
            if(!getUserCity.equals("")) {
                // Если данные не пустые
                // Получаем данные о погоде с сайта openweathermap
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=22d95768d344e3df6ae8697246d71df2&units=metric");
                if (!output.isEmpty()) { // Нет ошибки и такой город есть
                    JSONObject obj = new JSONObject(output);
                    // Обрабатываем JSON и устанавливаем данные в текстовые надписи
                    temp_info.setText("Температура: " + obj.getJSONObject("main").getInt("temp") + "℃");
                    temp_feel.setText("Ощущается: " + obj.getJSONObject("main").getInt("feels_like") + "℃");
                    temp_max.setText("Максимум: " + obj.getJSONObject("main").getInt("temp_max") + "℃");
                    temp_min.setText("Минимум: " + obj.getJSONObject("main").getInt("temp_min") + "℃");
                    temp_press.setText("Давление: " + obj.getJSONObject("main").getInt("pressure") + "Па");
                    temp_humidity.setText("Влажность: " + obj.getJSONObject("main").getInt("humidity") + "%");
                }
            }
        });
    }

    // Обработка URL адреса и получение данных с него
    private static String getUrlContent(String urlAdress) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Такой город не был найден!");
        }
        return content.toString();
    }
}

