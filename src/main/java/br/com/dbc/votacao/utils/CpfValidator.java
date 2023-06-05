package br.com.dbc.votacao.utils;

import org.springframework.stereotype.Component;

@Component
public class CpfValidator {

    public boolean isValid(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || isRepeatedSequence(cpf)) {
            return false;
        }

        int digit1 = calculateDigit(cpf.substring(0, 9));
        int digit2 = calculateDigit(cpf.substring(0, 9) + digit1);

        return cpf.equals(cpf.substring(0, 9) + digit1 + digit2);
    }

    private boolean isRepeatedSequence(String cpf) {
        return cpf.matches("(\\d)\\1{10}");
    }

    private int calculateDigit(String num) {
        int sum = 0;
        int weight = num.length() + 1;

        for (char digit : num.toCharArray()) {
            sum += Character.getNumericValue(digit) * weight--;
        }

        int result = 11 - (sum % 11);
        return result > 9 ? 0 : result;
    }
}