
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot()); // регистрация бота

        } catch (TelegramApiException e) {// обработка исключений
            e.printStackTrace();
        }
    }


    public void SendMsg(Message message,String text){

        SendMessage sendMessage = new SendMessage(); // создаем объект класса сендмесседж
        sendMessage.enableMarkdown(true);//включаем возможность разметки
        sendMessage.setChatId(message.getChatId().toString()); // из сообщения получаем айди???(в какой конкретно чат мы должны отправить ответ)
        sendMessage.setReplyToMessageId(message.getMessageId());//определили айди сообщения на котоырй мы будем отвечать
        sendMessage.setText(text);// установим сообщению текст который мы отправляли в этот метод
        sendMessage.enableHtml(true);
        try {     // устанавливаем отправку сообщения
            setBottoms(sendMessage); // поместили кнопку
            execute(sendMessage);//сообщение с ответом

        } catch (TelegramApiException e) {

            e.printStackTrace();
        }


}
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage(); // создаем объект класса месседж и помещаем туда текст
        if (message != null && message.hasText()) {
            switch (message.getText()){
                case "/help":
                    SendMsg(message , "Чем я могу помочь?");
                    break;
                case "/settings":
                    SendMsg(message , "Чем будем настраивать?");
                default:

                    try{
                        SendMsg(message,Weather.getWeather(message.getText(),model));
                    } catch (IOException e) {
                        SendMsg(message,"Нет такого города!");
                    }


            }
        }

    }

    public void setBottoms(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();// инициализация клавиатуры
        sendMessage.setReplyMarkup(replyKeyboardMarkup); // разметка для клавиатуры
        replyKeyboardMarkup.setSelective(true);//кому будет видна клавиатура
        replyKeyboardMarkup.setResizeKeyboard(true);//автоматически подгоняет клавиатуры
        replyKeyboardMarkup.setOneTimeKeyboard(false);//скрывать клавиатуру после нажатия или нет(вроде так)

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardfirstRow = new KeyboardRow(); // первая строка клавиатуры
        keyboardfirstRow.add(new KeyboardButton("/help"));
        keyboardfirstRow.add(new KeyboardButton("/settings"));
        keyboardRowList.add(keyboardfirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return "2chlinks";
    }

    public String getBotToken() {
        return "1232090313:AAHSwRWKEMErRx4riIVwopRM7kdvfSDwVJo";
    }
}
