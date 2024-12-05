/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.u4_parser;

import automata.AutomataDoWhile;
import automata.AutomataIf;
import automata.ForAutomata;
import automata.ReadAutomata;
import automata.WriteAutomata;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author alext
 */
public class App_Parser extends javax.swing.JFrame {
  


    /**
     * Creates new form App_Parser
     */
    public App_Parser() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser2 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout());

        jFileChooser2.addActionListener(evt -> jFileChooser2ActionPerformed(evt));
        getContentPane().add(jFileChooser2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

   //Metodo para eliminar espacios
    public static String eliminarEspacios(String entrada){
        return entrada.replaceAll("\\s+", " ");
    }
    
    //Metodo para procesar lineas
    public static void procesarLinea(String linea){
        if(linea.startsWith("read")){
            ReadAutomata.procesarRead(linea);
        }else if(linea.startsWith("write")){
            WriteAutomata.procesarWrite(linea);
        }else if(linea.startsWith("for")){
            ForAutomata.procesarFor(linea);
        }else if (linea.startsWith("do")) {
            AutomataDoWhile.procesarDoWhile(linea);
        } else if (linea.startsWith("if")) {
            AutomataIf.procesarIf(linea);
        } else {
            System.out.println("\nLínea no reconocida: " + linea);
        }
    }
   
    private void jFileChooser2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser2ActionPerformed
        try {
            String ruta = jFileChooser2.getSelectedFile().getAbsolutePath();                                        
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea = br.readLine()) != null) {
                String lineaSinEspacio = eliminarEspacios(linea.trim());
                if (lineaSinEspacio.isEmpty()) {
                    continue;
                }
                if (lineaSinEspacio.startsWith("do")) {
                    // Agrupar el bloque do-while completo
                    StringBuilder doWhileBlock = new StringBuilder();
                    doWhileBlock.append(lineaSinEspacio);
                    boolean finDoWhile = false;
                    while (!finDoWhile && (linea = br.readLine()) != null) {
                        lineaSinEspacio = eliminarEspacios(linea.trim());
                        if (lineaSinEspacio.isEmpty()) {
                            continue;
                        }
                        doWhileBlock.append(" ").append(lineaSinEspacio);
                        if (lineaSinEspacio.startsWith("}while") && lineaSinEspacio.endsWith(";")) {
                            finDoWhile = true;
                        }
                    }
                    procesarLinea(doWhileBlock.toString());
                } else if (lineaSinEspacio.startsWith("for")) {
                    // Agrupar el bucle for completo
                    StringBuilder forBlock = new StringBuilder();
                    forBlock.append(lineaSinEspacio);
                    boolean finFor = false;
                    int openBraces = 0;
                    if (lineaSinEspacio.contains("{")) {
                        openBraces++;
                    }
                    if (lineaSinEspacio.contains("}")) {
                        openBraces--;
                    }
                    while (!finFor && (linea = br.readLine()) != null) {
                        lineaSinEspacio = eliminarEspacios(linea.trim());
                        if (lineaSinEspacio.isEmpty()) {
                            continue;
                        }
                        forBlock.append(" ").append(lineaSinEspacio);
                        if (lineaSinEspacio.contains("{")) {
                            openBraces++;
                        }
                        if (lineaSinEspacio.contains("}")) {
                            openBraces--;
                            if (openBraces == 0) {
                                finFor = true;
                            }
                        }
                    }
                    procesarLinea(forBlock.toString());
                } else {
                    procesarLinea(lineaSinEspacio);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No se ha seleccionado ningún fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } 
        
    }//GEN-LAST:event_jFileChooser2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App_Parser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App_Parser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App_Parser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App_Parser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App_Parser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser2;
    // End of variables declaration//GEN-END:variables
}
