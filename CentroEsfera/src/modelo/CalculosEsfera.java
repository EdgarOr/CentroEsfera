package modelo;

import javax.swing.DefaultListModel;
import org.apache.commons.math3.fraction.Fraction;
import vista.PanelEsfera;

public class CalculosEsfera {
    
    private static final String SEPARADOR = "/";
    
    private final PanelEsfera panel;
    private String[] coeficientesLineales;
    private float coeficientesCuadrados;
    private final DefaultListModel modelo;
    private Fraction x, y, z, radio = Fraction.ZERO, valorConstante;
    private String ecuacionFinal, ecuacionFinalX, ecuacionFinalY, ecuacionFinalZ;
    
    public CalculosEsfera(PanelEsfera panelEsfera){
        this.panel = panelEsfera;
        modelo = new DefaultListModel();
        asignaciones();
    }
    
    public final void asignaciones(){
        
        coeficientesCuadrados = Float.parseFloat(panel.getValoresCuadradosJTF().getText());
        coeficientesLineales = panel.getValoresLinealesJTF().getText().split(SEPARADOR);
        int constante = Integer.parseInt(panel.getValorConstanteJTF().getText());
        if (coeficientesCuadrados != 1) {
           constante /= coeficientesCuadrados;
        }
        valorConstante = new Fraction(constante);
        calcularX();
    }
    
    public void calcularX(){
        Fraction x2 = new Fraction(coeficientesCuadrados);
        Fraction x1 = new Fraction(Float.parseFloat(coeficientesLineales[0]));
        modelo.addElement("* * * Calculando x * * *");
        x = completarCuadrados(x2, x1, "x");
       calcularY();
    }
    
    public void calcularY(){
        Fraction y2 = new Fraction(coeficientesCuadrados);
        Fraction y1 = new Fraction(Float.parseFloat(coeficientesLineales[1]));
        modelo.addElement("\n");
        modelo.addElement("* * * Calculando y * * *");
        y = completarCuadrados(y2, y1, "y");
        
        calcularZ();

    }
    
    public void calcularZ(){
        Fraction z2 = new Fraction(coeficientesCuadrados);
        Fraction z1 = new Fraction(Float.parseFloat(coeficientesLineales[2]));
        modelo.addElement("\n");
        modelo.addElement("* * * Calculando z * * *");
        z = completarCuadrados(z2, z1, "z");
        
        resultado();

    }
    
    public Fraction completarCuadrados(Fraction a, Fraction b, String letra){
        String stringAuxiliar ;
        
        if (a.compareTo(Fraction.ONE) != 0) {
            b = b.divide(a); 
            a = a.divide(a);
            
        }
        Fraction aux1 = new Fraction(b.abs().floatValue()), aux2, aux3;
        
        
        modelo.addElement(a + letra + "\u00b2 + (" + b + letra + ") = 0");
        aux1 = aux1.divide(2);
        modelo.addElement(a + letra + "\u00b2 + (" + b + letra + ") + (" + aux1 + ")\u00b2 - (" + aux1 + ")\u00b2 = 0");
        aux3 = aux1.multiply(aux1);
        modelo.addElement(a + letra + "\u00b2 + (" + b + letra + ") + " + aux3 + " - " + aux3 + " = 0");
        aux2 = aux3.multiply(-1);
       if (b.floatValue() < 0) 
            aux1 = aux1.multiply(-1);
        a = a.divide(a);
        stringAuxiliar  = "("+ a + letra + " + ("+ aux1 + ") )\u00b2 + (" + aux2 + ")";
        if (letra.equals("x")) {
            ecuacionFinal = stringAuxiliar;
        }else{
            ecuacionFinal += " + " + stringAuxiliar;
        }
        
        switch(letra){
            case "x":   ecuacionFinalX = "("+ a + letra + " + ("+ aux1 + ") )\u00b2";
                        break;
            case "y":   ecuacionFinalY = " + ("+ a + letra + " + ("+ aux1 + ") )\u00b2";
                        break;
            case "z":  ecuacionFinalZ = " + ("+ a + letra + " + ("+ aux1 + ") )\u00b2";
        }
        
        modelo.addElement(stringAuxiliar +  " = 0");
        radio = radio.add(aux2);
        
        return aux1;
    } 
    
    public void resultado(){
        modelo.addElement("\n");
        modelo.addElement("\n");
        modelo.addElement(" + + + + + Ecuacion Final + + + + +");
        radio = radio.add(valorConstante);
        modelo.addElement(ecuacionFinal + " + " + valorConstante + " = 0");
        modelo.addElement(ecuacionFinalX + ecuacionFinalY + ecuacionFinalZ + " " + radio + " = 0");
        modelo.addElement(ecuacionFinalX + ecuacionFinalY + ecuacionFinalZ + " = " + radio.multiply(-1));
        modelo.addElement("\n");
        modelo.addElement("\n");
        modelo.addElement(" + + + + + Resultado + + + + +");
        modelo.addElement(" Centro de la Esfera: (" + x.multiply(-1) + ", " + y.multiply(-1) + ", " + z.multiply(-1) + ")");
        modelo.addElement(" Radio: \u221a(" + radio.multiply(-1) + ")");
        panel.setDespliegueJL(modelo);
        
    }

    public float getX() {
        return x.floatValue();
    }

    public float getY() {
        return y.floatValue();
    }

    public float getZ() {
        return z.floatValue();
    }

    public float getRadio() {
        return radio.floatValue();
    }
    
    
    
    
    
}
