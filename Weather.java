import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
      //25c42d22ee58da668fd62113a5c3ba6a
    public static String getWeather(String message , Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" +message+ "&units=metric&appid=25c42d22ee58da668fd62113a5c3ba6a");

        Scanner in = new Scanner((InputStream)/* приведение типов */ url.getContent());// читаем содержимое того что нам пришло
        String result = "";
        while(in.hasNext()){
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result); // тут начинается магия с джейсон форматом
        model.setName(object.getString("name"));// получили наш город

        JSONObject main = object.getJSONObject("main"); // получили объект main из джейсона
        model.setTemp(main.getDouble("temp")); // из мейна получили температуру
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for(int i = 0;i < getArray.length();i++){
            JSONObject obj = getArray.getJSONObject(i); // инициализируем объект weather
            model.setIcon((String)/*приведение типа*/obj.get("icon"));
            model.setMain((String)obj.get("main"));
        }

        return "City:" + model.getName()+ "\n" +
                "Main:" + model.getMain() + "\n" +
                "Temperature:" + model.getTemp() + "c" + "\n" +
                "Humidity:" + model.getHumidity() + "%" + "\n" +
                 "http://openweathermap.org/img/w/" +model.getIcon() + ".png";


    }
}
