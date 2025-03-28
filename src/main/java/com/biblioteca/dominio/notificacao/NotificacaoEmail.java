package main.java.com.biblioteca.dominio.notificacao;

public class NotificacaoEmail implements Notificacao {
    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("\n=== NOTIFICAÇÃO VIA EMAIL ===");
        System.out.println("Para: " + destinatario);
        System.out.println("Mensagem: " + mensagem);
        System.out.println("----------------------------");
    }
}