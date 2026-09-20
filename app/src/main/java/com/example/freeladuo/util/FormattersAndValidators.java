package com.example.freeladuo.util;

import android.text.Editable;
import android.text.TextWatcher;

public class FormattersAndValidators {

    public static boolean isValidCpf(String cpf) {
        if (cpf == null) return false;

        String cleanCpf = cpf.replaceAll("[^0-9]", "");
        if (cleanCpf.length() != 11) return false;

        // Bloqueia sequências repetidas (ex: 111.111.111-11)
        boolean allDigitsEquals = true;
        for (int i = 1; i < cleanCpf.length(); i++) {
            if (cleanCpf.charAt(i) != cleanCpf.charAt(0)) {
                allDigitsEquals = false;
                break;
            }
        }
        if (allDigitsEquals) return false;

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Character.getNumericValue(cleanCpf.charAt(i));
            }

            // Valida 1º dígito
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * (10 - i);
            }
            int firstVerifier = 11 - (sum % 11);
            if (firstVerifier >= 10) firstVerifier = 0;
            if (digits[9] != firstVerifier) return false;

            // Valida 2º dígito
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * (11 - i);
            }
            int secondVerifier = 11 - (sum % 11);
            if (secondVerifier >= 10) secondVerifier = 0;

            return digits[10] == secondVerifier;
        } catch (Exception e) {
            return false;
        }
    }

    // Máscara para o WhatsApp
    public static TextWatcher phoneTextWatcher() {
        return new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[^0-9]", "");
                StringBuilder formatted = new StringBuilder();
                int length = str.length();

                if (length > 0) {
                    formatted.append("(").append(str.substring(0, Math.min(2, length)));
                }
                if (length >= 3) {
                    formatted.append(") ");
                    if (length <= 10) {
                        formatted.append(str.substring(2, Math.min(6, length)));
                        if (length >= 7) {
                            formatted.append("-").append(str.substring(6, length));
                        }
                    } else {
                        formatted.append(str.substring(2, Math.min(7, length)));
                        if (length >= 8) {
                            formatted.append("-").append(str.substring(7, Math.min(11, length)));
                        }
                    }
                }

                isUpdating = true;
                Editable editable = (Editable) s;
                editable.replace(0, editable.length(), formatted.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    // Máscara para CPF
    public static TextWatcher cpfTextWatcher() {
        return new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[^0-9]", "");
                StringBuilder formatted = new StringBuilder();
                int length = str.length();

                if (length > 0) {
                    formatted.append(str.substring(0, Math.min(3, length)));
                }
                if (length >= 4) {
                    formatted.append(".").append(str.substring(3, Math.min(6, length)));
                }
                if (length >= 7) {
                    formatted.append(".").append(str.substring(6, Math.min(9, length)));
                }
                if (length >= 10) {
                    formatted.append("-").append(str.substring(9, Math.min(11, length)));
                }

                isUpdating = true;
                Editable editable = (Editable) s;
                editable.replace(0, editable.length(), formatted.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }
}
