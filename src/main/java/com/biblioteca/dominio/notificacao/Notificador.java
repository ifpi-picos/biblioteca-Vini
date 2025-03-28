package main.java.com.biblioteca.dominio.notificacao;
public class Notificador {
    private Notificacao estrategia;

    public Notificador() {
        this.estrategia = new NotificacaoEmail(); // Padrão
    }

    public void setEstrategia(Notificacao estrategia) {
        this.estrategia = estrategia;
    }

    public void enviarNotificacao(String destinatario, String mensagem) {
        estrategia.enviar(destinatario, mensagem);
    }
}