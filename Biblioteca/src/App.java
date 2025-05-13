// Victor Gonçalves - RGM: 38094649 / lucas Kumegawa de Godoi - RGM: 42045681 / Thauana Vitória - RGM: 37905236 
import view.BibliotecaView;

public class App {
    public static void main(String[] args) {
        System.out.println("============================================================================");
        System.out.println("||              <  SISTEMA DE GERENCIAMENTO DE BIBLIOTECA  >              ||");
        System.out.println("|| Desenvolvido por: Lucas Kumegawa, Victor Gonçalves e Thauana Vitória   ||");
        System.out.print("============================================================================");

        BibliotecaView view = new BibliotecaView();
        view.iniciar();
    }
}