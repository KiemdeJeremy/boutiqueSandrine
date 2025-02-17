package com.example.demo.services;

import com.example.demo.models.Mpersonnel;
import com.example.demo.models.Mrapport;
import com.example.demo.repository.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.io.font.constants.StandardFonts;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.core.io.Resource;

@Service
public class RapportService {

    @Autowired
    private RapportRepository rapportRepository;

    // Récupérer tous les rapports
    public List<Mrapport> getAllRapports() {
        return rapportRepository.findAll();
    }

    // Récupérer un rapport par son ID
    public Mrapport getRapportById(Long id) {
        Optional<Mrapport> rapport = rapportRepository.findById(id);
        return rapport.orElse(null);
    }

    // Créer ou mettre à jour un rapport
    public Mrapport saveRapport(Mrapport rapport) {
        return rapportRepository.save(rapport);
    }

    // Supprimer un rapport par son ID
    public void deleteRapport(Long id) {
        rapportRepository.deleteById(id);
    }

    // Rechercher des rapports par personnel
    public List<Mrapport> getRapportsByPersonnel(Long personnelId) {
        return rapportRepository.findByPersonnel_IdUtilisateur(personnelId);
    }

    public void generateRapport(Mrapport rapport, Mpersonnel perso) {
        System.out.println("Tentative de génération du rapport ID: " + rapport.getIdRapport());

        String userHome = System.getProperty("user.home");
        String rapportDirPath = userHome + File.separator + "Desktop" + File.separator + "rapports_Campus_Faso";
        File rapportDir = new File(rapportDirPath);

        if (!rapportDir.exists()) {
            rapportDir.mkdirs();
        }

        String filePath = rapportDirPath + File.separator + "rapport_" + rapport.getIdRapport() + ".pdf";

        PdfWriter writer = null;
        PdfDocument pdf = null;
        Document document = null;

        try {
            writer = new PdfWriter(new FileOutputStream(filePath));
            pdf = new PdfDocument(writer);
            document = new Document(pdf, PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            // Création du tableau pour l'en-tête avec logo à gauche et texte à droite
            Table headerTable = new Table(2);
            headerTable.setWidth(UnitValue.createPercentValue(100));

            // Cellule pour le logo (colonne 1)
            Cell logoCell = new Cell();
            logoCell.setBorder(Border.NO_BORDER);

            // Tentative de chargement du logo
            boolean logoLoaded = false;
            try {
                Resource resource = new ClassPathResource("static/images/CampusFasoLogo.jpg");
                if (resource.exists()) {
                    byte[] imageData = resource.getInputStream().readAllBytes();
                    ImageData data = ImageDataFactory.create(imageData);
                    Image logo = new Image(data);
                    logo.setWidth(175);
                    logoCell.add(logo);
                    logoLoaded = true;
                    System.out.println("Logo chargé avec ClassPathResource");
                } else {
                    System.out.println("Logo non chargé");
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement du logo: " + e.getMessage());
            }

            if (!logoLoaded) {
                logoCell.add(new Paragraph("Logo non disponible"));
            }
            headerTable.addCell(logoCell);

            // Cellule pour la devise (colonne 2)
            Cell deviseCell = new Cell();
            deviseCell.setBorder(Border.NO_BORDER);
            Paragraph devise = new Paragraph("La Patrie ou la Mort nous Vaincrons")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.RIGHT);
            // Ajouter "Ouagadougou, le [date]" juste en dessous
            LocalDate today = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = today.format(dateFormatter);
            Paragraph ouagaDate = new Paragraph("Ouagadougou, le " + formattedDate)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(ouagaDate);
            deviseCell.add(devise);
            headerTable.addCell(deviseCell);

            // Ajouter la table d'en-tête au document
            document.add(headerTable);

            document.add(new Paragraph(" ")); // Espace

            // Titre du rapport
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            Paragraph title = new Paragraph("RAPPORT")
                    .setFont(boldFont)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Reste du contenu inchangé...
            PdfFont headerFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont contentFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            Color blueColor = new DeviceRgb(0, 0, 255);

            document.add(new Paragraph("Détails du rapport:")
                    .setFont(headerFont)
                    .setFontSize(16));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("ID Rapport: " + rapport.getIdRapport())
                    .setFont(contentFont)
                    .setFontSize(14));
            document.add(new Paragraph("État: " + rapport.getEtat())
                    .setFont(headerFont)
                    .setFontSize(14)
                    .setFontColor(blueColor));

            if (perso != null) {
                document.add(new Paragraph("Personnel concerné: " + perso.getNom() + " "
                        + perso.getPrenom())
                        .setFont(contentFont)
                        .setFontSize(14));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("-----------------------------------------------------------------------------------------------------------------")
                    .setFont(contentFont)
                    .setFontSize(14));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Contenu du rapport:")
                    .setFont(headerFont)
                    .setFontSize(16));
            document.add(new Paragraph(" "));

            String contenu = rapport.getContenuRapport() != null ? rapport.getContenuRapport()
                    : "Aucun contenu disponible";
            document.add(new Paragraph(contenu)
                    .setFont(contentFont)
                    .setFontSize(14));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Ligne de séparation
            document.add(new Paragraph("-----------------------------------------------------------------------------------------------------------------")
                    .setFont(contentFont)
                    .setFontSize(14));

            // Date et heure avec format personnalisé
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm:ss");
            String formattedDateTime = now.format(dateTimeFormatter);
            document.add(new Paragraph("Généré le: " + formattedDateTime)
                    .setFont(contentFont)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.RIGHT));

            // Footer
            document.add(new Paragraph("Document confidentiel - Usage interne uniquement")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            System.out.println("Contenu du rapport ajouté au PDF");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la génération du rapport: " + e.getMessage());
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (pdf != null) {
                    pdf.close();
                }
                if (writer != null) {
                    writer.close();
                }
                System.out.println("Rapport PDF généré et fermé correctement: " + filePath);
            } catch (Exception e) {
                System.out.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
            }
        }
    }
}