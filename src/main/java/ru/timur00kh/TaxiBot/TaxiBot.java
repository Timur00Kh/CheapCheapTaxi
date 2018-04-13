package ru.timur00kh.TaxiBot;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TaxiBot extends TelegramLongPollingBot {

    String[] commands = {
            "TaxiNow",
            "PlanTaxi"
    };
    String[] taxiNowCommands = {
            "10",
            "20",
            "30",
            "1 hour"
    };
    String[] planTaxiCommands = {
            "Today",
            "Tomorrow",
    };
    String[] finalCommands = {
            "Заказать anyway",
            "cansel если без попутчика",
    };

    TaxiQue que = new TaxiQue();

    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());
        System.out.println(update.hasCallbackQuery());
//        System.out.println(update.getCallbackQuery().getData());
//        System.out.println(update.getCallbackQuery().toString());

        que.printQue();
//        if (!update.hasMessage()) String messageText = update.getMessage().getText();
//        long chat_id = update.getMessage().getChatId();
        int userId;
        long chat_id;
        if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getFrom().getId();
            chat_id = update.getCallbackQuery().getMessage().getChatId();
        } else {
            userId = update.getMessage().getFrom().getId();
            chat_id = update.getMessage().getChatId();
        }

        TaxiUser currentUser = que.getUserFromQue(userId, chat_id);

        switch (currentUser.getState()) {
            case "initial": {
                if (update.hasMessage() && update.getMessage().hasText()){
                    String userName = update.getMessage().getFrom().getUserName();
                    if (update.getMessage().getText().equals("/start") || update.getMessage().getText().equals("/start")){
                        SendMessage newMessage = buildMarkUpInitialMessage(update);
                        if (this.executeMessage(newMessage)) {
                            currentUser.nullify();
                            currentUser.setNextState();
                        }
                    }
                }
                break;
            }
            case "TaxiType": {
                if (update.hasCallbackQuery()) {
                    String callBack = update.getCallbackQuery().getData();
                    if(callBack.equals(commands[0])) {
                        currentUser.setTaxiType(commands[0]);

                        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                        List<InlineKeyboardButton> rowInline = new ArrayList<>();
                        for (int i = 0; i < taxiNowCommands.length; i++) {
                            rowInline.add(new InlineKeyboardButton().setText(taxiNowCommands[i]).setCallbackData(taxiNowCommands[i]));
                        }
                        rowsInline.add(rowInline);
                        markupInline.setKeyboard(rowsInline);

                        EditMessageText editMessageText = new EditMessageText()
                                .setChatId(chat_id)
                                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                                .setText("choose time")
                                .setReplyMarkup(markupInline);
                        executeMessage(editMessageText);

                    }else if (callBack.equals(commands[1])) {
                        currentUser.setTaxiType(commands[1]);

                        System.out.println(update.getCallbackQuery().getMessage().getDate());
                        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                        List<InlineKeyboardButton> rowInline = new ArrayList<>();
                        for (int i = 0; i < planTaxiCommands.length; i++) {
                            rowInline.add(new InlineKeyboardButton().setText(planTaxiCommands[i]).setCallbackData(planTaxiCommands[i]));
                        }
                        rowsInline.add(rowInline);
                        markupInline.setKeyboard(rowsInline);

                        EditMessageText editMessageText = new EditMessageText()
                                .setChatId(chat_id)
                                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                                .setText("choose date")
                                .setReplyMarkup(markupInline);
                        executeMessage(editMessageText);
                    } else {
                        EditMessageText editMessageText = new EditMessageText()
                                .setChatId(chat_id)
                                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                                .setText("Pls, send A location");
                        executeMessage(editMessageText);
                        currentUser.setTime(update.getCallbackQuery().getData());
                        currentUser.setNextState();
                        currentUser.setNextState();
                    }

                } else {
                    SendMessage newMessage = new SendMessage().setText("just tap the button").setChatId(chat_id);
                    this.executeMessage(newMessage);
                }
                break;
            }
            case "time": {
                SendMessage newMessage = new SendMessage().setText("Pls, send A location").setChatId(chat_id);

            }
            case "locationA": {
                if (update.hasMessage() && update.getMessage().hasLocation()) {
                    Location locationA = update.getMessage().getLocation();
                    currentUser.setLocationA(locationA);
                    SendMessage newMessage = new SendMessage().setText("Ok, send location B").setChatId(chat_id);
                    if (executeMessage(newMessage)) {
                        currentUser.setNextState();
                    }
                } else {
                    SendMessage newMessage = new SendMessage().setText("Send location pls").setChatId(chat_id);
                    this.executeMessage(newMessage);
                }
                break;
            }
            case "locationB": {
                if (update.hasMessage() && update.getMessage().hasLocation()) {
                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    for (int i = 0; i < finalCommands.length; i++) {
                        List<InlineKeyboardButton> rowInline = new ArrayList<>();
                        rowInline.add(new InlineKeyboardButton().setText(finalCommands[i]).setCallbackData(finalCommands[i]));
                        rowsInline.add(rowInline);
                    }
                    markupInline.setKeyboard(rowsInline);

                    Location locationA = update.getMessage().getLocation();
                    currentUser.setLocationB(locationA);
                    SendMessage newMessage = new SendMessage().setChatId(chat_id)
                            .setParseMode("html")
                            .setText("<b>Ваша поездка:</b> &0A Откуда: <i>" +
                                    currentUser.getLocationA() + "</i> &0A Куда: <i>" +
                                    currentUser.getLocationB() + "</i> &0A Время: <i>" +
                                    currentUser.getTime() + "</i>")
                            .setReplyMarkup(markupInline);
                    if (executeMessage(newMessage)) {
                        currentUser.setNextState();
                    }
                } else {
                    SendMessage newMessage = new SendMessage().setText("Pls, send location B").setChatId(chat_id);
                    this.executeMessage(newMessage);
                }
                break;
            }

            case "final": {
                if (update.hasCallbackQuery()) {
                    currentUser.setSettings(update.getCallbackQuery().getData());
                    EditMessageText newMessage = new EditMessageText().setChatId(chat_id)
                            .setParseMode("html")
                            .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                            .setText("<b>Ваша поездка:</b>          Откуда: <i>" +
                                    currentUser.getLocationA() + "</i> Куда: <i>          " +
                                    currentUser.getLocationB() + "</i>Время: <i>          " +
                                    currentUser.getTime() + "</i> Settings: <i>           " +
                                    currentUser.getSettings() + "</i>")
                            .enableHtml(true);
                    executeMessage(newMessage);
                    currentUser.setNextState();
                    que.checkQue();
                }
            }
        }







    }

    private SendMessage buildMarkUpInitialMessage(Update update) {
        SendMessage newMessage = new SendMessage()
                .setText("choose mode")
                .setChatId(update.getMessage().getChatId());
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText(commands[i]).setCallbackData(commands[i]));
            rowsInline.add(rowInline);
        }
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        newMessage.setReplyMarkup(markupInline);
        return newMessage;
    }

    private SendMessage buildMarkUpInitialMessage() {
        SendMessage newMessage = new SendMessage().setText("choose mode");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText(commands[i]).setCallbackData(commands[i]));
            rowsInline.add(rowInline);
        }
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        newMessage.setReplyMarkup(markupInline);
        return newMessage;
    }

    private boolean executeMessage(BotApiMethod message) {
        try {
            execute(message);
            return true;
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public void onUpdatesReceived(List<Update> updates) {
//
//    }

    public String getBotUsername() {
        return "CheapCheapTaxi_bot";
    }

    public String getBotToken() {
        return "";
    }
}
