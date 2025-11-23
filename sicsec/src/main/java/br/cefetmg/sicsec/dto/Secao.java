package br.cefetmg.sicsec.dto;

import java.util.List;

public class Secao {
    private String icone;
    private String html;
    private String texto;
    private List<String> css;
    private List<String> js;
    private String secao;
    private Boolean isDefault;

    public Secao(String icone, String html, String texto, List<String> css, List<String> js, String secao, Boolean isDefault) {
        this.icone = icone;
        this.html = html;
        this.texto = texto;
        this.css = css;
        this.js = js;
        this.secao = secao;
        this.isDefault = isDefault;
    }

    public String getIcone() {
        return icone;
    }

    public String getHtml() {
        return html;
    }

    public String getTexto() {
        return texto;
    }

    public List<String> getCss() {
        return css;
    }

    public List<String> getJs() {
        return js;
    }

    public String getSecao() {
        return secao;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
}