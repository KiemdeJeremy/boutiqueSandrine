package com.example.demo.validateurs;

import java.util.List;

public abstract class Validateur {

    public abstract List<String> valider(String... attributs);
    
}