package ru.timur00kh.TaxiBot;

import java.util.ArrayList;

public class TaxiQue {
    ArrayList<TaxiUser> taxiUsers = new ArrayList<>();
    int queSize;

    public TaxiUser getUserFromQue(String name, int id) {
        for (int i = 0; i < taxiUsers.size(); i++) {
            if (taxiUsers.get(i).getId() == id) return taxiUsers.get(i);
        }
        TaxiUser newTaxiUser = new TaxiUser(name, id, this);
        this.add(newTaxiUser);
        return newTaxiUser;
    }

    public TaxiUser getUserFromQue(int id, long chat_id) {
        for (int i = 0; i < taxiUsers.size(); i++) {
            if (taxiUsers.get(i).getId() == id) return taxiUsers.get(i);
        }
        TaxiUser newTaxiUser = new TaxiUser(id, this, chat_id);
        this.add(newTaxiUser);
        return newTaxiUser;
    }

    private void add(TaxiUser taxiUser) {
        taxiUsers.add(taxiUser);
    }

    public void printQue() {
        for (int i = 0; i < taxiUsers.size(); i++) {
            System.out.println(taxiUsers.get(i).toString());
        }
    }

    public void checkQue() {
        for (int i = 0; i < taxiUsers.size(); i++) {
            if (taxiUsers.get(i).getState().equals("waiting")) {
                taxiUsers.remove(i);
            }
        }

    }
}
