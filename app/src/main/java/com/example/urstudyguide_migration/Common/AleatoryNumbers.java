package com.example.urstudyguide_migration.Common;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AleatoryNumbers {

    private Set<Integer> aleatoryNumbers = new HashSet<>();

    Random random = new Random();

    public void addElements(int value){
        aleatoryNumbers.add(value);
    }

    public int checkForRepeatedValues(int value, int randomInterval){
        System.out.println("Se ha ingresado el valor " + value);
        while(aleatoryNumbers.contains(value)){
            value = random.nextInt(randomInterval)+1;
        }
        System.out.println("sale el valor de la función 'Checador' "  + value);
        aleatoryNumbers.add(value);
        return value;
    }
    
    public void printAllAleatoryNumbers(){
        for (Integer aleatoryNumber: aleatoryNumbers) {
            System.out.println(aleatoryNumber);
        }
    }

    public void restartAleatoryNumbers(){
        aleatoryNumbers.clear();
        System.out.println("Se ha limpiado el Arreglo de número aleatoreos");
    }

}
