/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorios;

import DAL.ConectaBd;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel
 */
public class RelatorioCompra {

    public static void gerarRelatorio(String sql) throws ClassNotFoundException { // criação do objeto documento
        Document document = new Document(PageSize.A4, 30, 20, 20, 30);
        Connection conecta; //É o objeto que conecta com o banco de dados
        PreparedStatement pst;
        ResultSet rs;
        conecta = ConectaBd.conectabd();

        OutputStream output;
        try {
            output = new FileOutputStream("relatorios/Relatório de Compras.pdf");
            try {
                PdfWriter.getInstance(document, output);
                document.open(); // adicionando um parágrafo ao documento 
                document.add(new Paragraph("                                    Relatório de Compras"));
                document.add(new Paragraph("\n"));
                Font bfBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(0, 0, 0));
                Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, new BaseColor(0, 0, 0));
                Font bf8 = new Font(Font.FontFamily.TIMES_ROMAN, 6);
                PdfPTable tabela = new PdfPTable(6);
                PdfPCell cell;
                cell = new PdfPCell(new Phrase("Fornecedor", bfBold10));
                tabela.addCell(cell);
                cell = new PdfPCell(new Phrase("Produto", bfBold10));
                tabela.addCell(cell);
                cell = new PdfPCell(new Phrase("Qtde do Produto", bfBold10));
                tabela.addCell(cell);
                cell = new PdfPCell(new Phrase("Valor por Unidade", bfBold10));
                tabela.addCell(cell);
                cell = new PdfPCell(new Phrase("Valor Total", bfBold10));
                tabela.addCell(cell);
                cell = new PdfPCell(new Phrase("Data", bfBold10));
                tabela.addCell(cell);

                try {
                    pst = conecta.prepareStatement(sql);
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        String fornec = rs.getString("fornecedor");
                        cell = new PdfPCell(new Phrase(fornec, bf8));
                        tabela.addCell(cell);
                        fornec = rs.getString("produto");
                        cell = new PdfPCell(new Phrase(fornec, bf8));
                        tabela.addCell(cell);
                        fornec = rs.getString("quantidade");
                        cell = new PdfPCell(new Phrase(fornec, bf8));
                        tabela.addCell(cell);
                        fornec = rs.getString("valorunitariocompra");
                        cell = new PdfPCell(new Phrase(fornec, bf8));
                        tabela.addCell(cell);
                        fornec = rs.getString("valortotalcompra");
                        cell = new PdfPCell(new Phrase(fornec, bf8));
                        tabela.addCell(cell);
                        fornec = rs.getString("data_compra");
                        cell = new PdfPCell(new Phrase(fornec, bf8));
                        tabela.addCell(cell);
                    }
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, error);
                }

                document.add(tabela);
                document.close();
            } catch (DocumentException de) {
                System.err.println(de.getMessage());
                document.close();

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelatorioCompra.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());

        }

    }

}
