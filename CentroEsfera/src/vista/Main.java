package vista;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame f = new JFrame("Calcular el centro y el radio de una esfera");
        PanelEsfera panel = new PanelEsfera();
        
        
        f.setSize(467, 430);
        f.add(panel);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
        
        
    }
    
    
    
}
