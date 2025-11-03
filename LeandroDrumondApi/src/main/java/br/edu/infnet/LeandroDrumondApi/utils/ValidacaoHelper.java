package br.edu.infnet.LeandroDrumondApi.utils;

import java.util.function.Consumer;

public final class ValidacaoHelper {

    private ValidacaoHelper() {
    }

    /**
     * Aplica o valor informado ao setter apenas se ele não for nulo.
     * @param <T> tipo genérico do valor
     * @param valor valor que será testado
     * @param setter função setter que será invocada se o valor não for nulo
     */
    public static <T> void aplicarSeNaoNulo(T valor, Consumer<T> setter) {
        if (valor != null) setter.accept(valor);
    }
}
